package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoffeeTest {

    @Test
    @DisplayName("Devrait créer un café valide avec un prix entre 0 et 200")
    void shouldCreateValidCoffee() {
        Coffee coffee = Coffee.create("Espresso", new Price(25));

        assertThat(coffee.isNew()).isTrue();
        assertThat(coffee.getId()).isNull();
        assertThat(coffee.getName()).isEqualTo("Espresso");
        assertThat(coffee.getPrice().amount()).isEqualTo(25);
        assertThat(coffee.getReviews()).isEmpty();
    }

    @Test
    @DisplayName("Devrait refuser un café avec un nom vide ou blanc")
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> Coffee.create("   ", new Price(10)))
            .isInstanceOf(DomainValidationException.class)
            .hasMessageContaining("Coffee name must not be blank");
    }

    @Test
    @DisplayName("Devrait refuser un prix négatif ou supérieur à 200")
    void shouldRejectInvalidPrice() {
        assertThatThrownBy(() -> new Price(-5))
            .isInstanceOf(DomainValidationException.class)
            .hasMessageContaining("Price must be between 0 and 200");

        assertThatThrownBy(() -> new Price(201))
            .isInstanceOf(DomainValidationException.class)
            .hasMessageContaining("Price must be between 0 and 200");
    }

    @Test
    @DisplayName("Devrait reconstituer un café avec son identifiant et ses avis")
    void shouldReconstituteCoffeeWithReviews() {
        Review review = new Review(new ReviewId(1L), "Jean", "Excellent", 5);
        Coffee coffee = Coffee.reconstitute(new CoffeeId(42L), "Moka", new Price(15), List.of(review));

        assertThat(coffee.isNew()).isFalse();
        assertThat(coffee.getId()).isEqualTo(new CoffeeId(42L));
        assertThat(coffee.getReviews()).hasSize(1);
    }
}
