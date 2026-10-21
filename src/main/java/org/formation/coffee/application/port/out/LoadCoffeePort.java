package org.formation.coffee.application.port.out;

import org.formation.coffee.domain.model.Coffee;
import org.formation.coffee.domain.model.CoffeeId;

import java.util.List;
import java.util.Optional;

/**
 * Charge des agrégats {@link Coffee} complets, avis compris.
 */
public interface LoadCoffeePort {

    List<Coffee> findAll();

    Optional<Coffee> findById(CoffeeId id);
}
