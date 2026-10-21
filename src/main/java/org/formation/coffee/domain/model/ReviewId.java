package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;

/**
 * Identité d'un {@link Review}.
 */
public record ReviewId(long value) {

    public ReviewId {
        if (value <= 0) {
            throw new DomainValidationException("Review id must be positive, got " + value);
        }
    }
}
