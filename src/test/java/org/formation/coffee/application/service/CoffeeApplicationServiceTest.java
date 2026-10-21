package org.formation.coffee.application.service;

import org.formation.coffee.application.port.in.CreateCoffeeCommand;
import org.formation.coffee.application.port.in.UpdateCoffeeCommand;
import org.formation.coffee.application.port.out.LoadCoffeePort;
import org.formation.coffee.application.port.out.SaveCoffeePort;
import org.formation.coffee.application.port.out.TransactionRunner;
import org.formation.coffee.domain.exception.CoffeeNotFoundException;
import org.formation.coffee.domain.model.Coffee;
import org.formation.coffee.domain.model.CoffeeId;
import org.formation.coffee.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoffeeApplicationServiceTest {

    private InMemoryCoffeeRepository repository;
    private CoffeeApplicationService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCoffeeRepository();
        TransactionRunner directRunner = new TransactionRunner() {
            @Override
            public <T> T execute(Supplier<T> work) {
                return work.get();
            }

            @Override
            public <T> T executeReadOnly(Supplier<T> work) {
                return work.get();
            }
        };
        service = new CoffeeApplicationService(repository, repository, directRunner);
    }

    @Test
    @DisplayName("Devrait créer un café via le UseCase et lui attribuer un identifiant")
    void shouldCreateCoffee() {
        Coffee created = service.createCoffee(new CreateCoffeeCommand("Ethiopia", new Price(30)));

        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Ethiopia");
        assertThat(created.getPrice().amount()).isEqualTo(30);
    }

    @Test
    @DisplayName("Devrait lister les cafés et obtenir un café par son identifiant")
    void shouldListAndGetCoffee() {
        Coffee created = service.createCoffee(new CreateCoffeeCommand("Colombia", new Price(40)));

        List<Coffee> all = service.listCoffees();
        assertThat(all).hasSize(1);

        Coffee fetched = service.getCoffee(created.getId());
        assertThat(fetched.getName()).isEqualTo("Colombia");
    }

    @Test
    @DisplayName("Devrait lever CoffeeNotFoundException si l'identifiant n'existe pas")
    void shouldThrowWhenCoffeeNotFound() {
        assertThatThrownBy(() -> service.getCoffee(new CoffeeId(999L)))
            .isInstanceOf(CoffeeNotFoundException.class)
            .hasMessageContaining("Coffee 999 not found");
    }

    @Test
    @DisplayName("Devrait mettre à jour partiellement le nom ou le prix")
    void shouldUpdateCoffee() {
        Coffee created = service.createCoffee(new CreateCoffeeCommand("Kenya", new Price(50)));

        Coffee updated = service.updateCoffee(
            new UpdateCoffeeCommand(created.getId(), null, new Price(60)));

        assertThat(updated.getPrice().amount()).isEqualTo(60);
        assertThat(updated.getName()).isEqualTo("Kenya");
    }

    private static class InMemoryCoffeeRepository implements LoadCoffeePort, SaveCoffeePort {
        private final Map<Long, Coffee> store = new LinkedHashMap<>();
        private final AtomicLong seq = new AtomicLong(1);

        @Override
        public List<Coffee> findAll() {
            return new ArrayList<>(store.values());
        }

        @Override
        public Optional<Coffee> findById(CoffeeId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Coffee save(Coffee coffee) {
            CoffeeId id = coffee.getId();
            if (id == null) {
                id = new CoffeeId(seq.getAndIncrement());
                coffee = Coffee.reconstitute(id, coffee.getName(), coffee.getPrice(), coffee.getReviews());
            }
            store.put(id.value(), coffee);
            return coffee;
        }
    }
}
