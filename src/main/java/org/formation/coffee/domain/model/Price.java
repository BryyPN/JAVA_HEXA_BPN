package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;

/**
 * Prix d'un café, toujours compris entre {@link #MIN} et {@link #MAX} inclus.
 */
public record Price(int amount) {

    public static final int MIN = 0;
    public static final int MAX = 200;

    public Price {
        if (amount < MIN || amount > MAX) {
            throw new DomainValidationException(
                "Price must be between %d and %d, got %d".formatted(MIN, MAX, amount));
        }
    }
}
