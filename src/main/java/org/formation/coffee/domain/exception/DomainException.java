package org.formation.coffee.domain.exception;

/**
 * Type de base de toutes les exceptions levées par le domaine.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
