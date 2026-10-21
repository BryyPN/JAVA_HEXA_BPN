package org.formation.coffee.application.service;

import org.formation.coffee.application.port.in.CreateCoffeeCommand;
import org.formation.coffee.application.port.in.CreateCoffeeUseCase;
import org.formation.coffee.application.port.in.GetCoffeeQuery;
import org.formation.coffee.application.port.in.ListCoffeesQuery;
import org.formation.coffee.application.port.in.UpdateCoffeeCommand;
import org.formation.coffee.application.port.in.UpdateCoffeeUseCase;
import org.formation.coffee.application.port.out.LoadCoffeePort;
import org.formation.coffee.application.port.out.SaveCoffeePort;
import org.formation.coffee.application.port.out.TransactionRunner;
import org.formation.coffee.domain.exception.CoffeeNotFoundException;
import org.formation.coffee.domain.model.Coffee;
import org.formation.coffee.domain.model.CoffeeId;
import org.formation.coffee.domain.model.Price;

import java.util.List;

/**
 * Implémente les cas d'usage des cafés. Java pur : câblé par {@code UseCaseConfig}, sans annotation Spring.
 */
public class CoffeeApplicationService
    implements ListCoffeesQuery, GetCoffeeQuery, CreateCoffeeUseCase, UpdateCoffeeUseCase {

    private final LoadCoffeePort loadCoffeePort;
    private final SaveCoffeePort saveCoffeePort;
    private final TransactionRunner transactionRunner;

    public CoffeeApplicationService(LoadCoffeePort loadCoffeePort,
                                  SaveCoffeePort saveCoffeePort,
                                  TransactionRunner transactionRunner) {
        this.loadCoffeePort = loadCoffeePort;
        this.saveCoffeePort = saveCoffeePort;
        this.transactionRunner = transactionRunner;
    }

    @Override
    public List<Coffee> listCoffees() {
        return transactionRunner.executeReadOnly(loadCoffeePort::findAll);
    }

    @Override
    public Coffee getCoffee(CoffeeId id) {
        return transactionRunner.executeReadOnly(() -> loadCoffee(id));
    }

    @Override
    public Coffee createCoffee(CreateCoffeeCommand command) {
        return transactionRunner.execute(
            () -> saveCoffeePort.save(Coffee.create(command.name(), command.price())));
    }

    @Override
    public Coffee updateCoffee(UpdateCoffeeCommand command) {
        return transactionRunner.execute(() -> {
            Coffee coffee = loadCoffee(command.id());
            String name = command.name();
            if (name != null) {
                coffee.rename(name);
            }
            Price price = command.price();
            if (price != null) {
                coffee.changePrice(price);
            }
            return saveCoffeePort.save(coffee);
        });
    }

    private Coffee loadCoffee(CoffeeId id) {
        return loadCoffeePort.findById(id).orElseThrow(() -> new CoffeeNotFoundException(id));
    }
}
