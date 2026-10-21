package org.formation.coffee.application.port.in;

import org.formation.coffee.domain.exception.CoffeeNotFoundException;
import org.formation.coffee.domain.model.Coffee;
import org.formation.coffee.domain.model.CoffeeId;

public interface GetCoffeeQuery {

    /**
     * @throws CoffeeNotFoundException si aucun café n'a cet identifiant
     */
    Coffee getCoffee(CoffeeId id);
}
