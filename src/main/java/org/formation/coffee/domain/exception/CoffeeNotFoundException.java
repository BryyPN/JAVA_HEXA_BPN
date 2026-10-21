package org.formation.coffee.domain.exception;

import org.formation.coffee.domain.model.CoffeeId;

/**
 * Levée quand aucun café n'existe pour l'identifiant demandé.
 */
public class CoffeeNotFoundException extends DomainException {

    private final CoffeeId coffeeId;

    public CoffeeNotFoundException(CoffeeId coffeeId) {
        super("Coffee " + coffeeId.value() + " not found");
        this.coffeeId = coffeeId;
    }

    public CoffeeId getCoffeeId() {
        return coffeeId;
    }
}
