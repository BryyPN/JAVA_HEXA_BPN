package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;

/**
 * Identité d'un {@link Coffee}.
 */
public record CoffeeId(long value) {

    public CoffeeId {
        if (value <= 0) {
            throw new DomainValidationException("Coffee id must be positive, got " + value);
        }
    }
}
