package org.formation.coffee.application.port.out;

import java.util.function.Supplier;

/**
 * Exécute une unité de travail dans une transaction, sans annotation de framework dans la couche application.
 */
public interface TransactionRunner {

    <T> T execute(Supplier<T> work);

    <T> T executeReadOnly(Supplier<T> work);
}
