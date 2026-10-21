package org.formation.coffee.domain.exception;

/**
 * Levée quand une valeur ou une opération violerait un invariant métier.
 */
public class DomainValidationException extends DomainException {

    public DomainValidationException(String message) {
        super(message);
    }
}
