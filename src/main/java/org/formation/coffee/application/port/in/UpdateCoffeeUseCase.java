package org.formation.coffee.application.port.in;

import org.formation.coffee.domain.exception.CoffeeNotFoundException;
import org.formation.coffee.domain.model.Coffee;

public interface UpdateCoffeeUseCase {

    /**
     * @throws CoffeeNotFoundException si aucun café n'a l'identifiant de la commande
     */
    Coffee updateCoffee(UpdateCoffeeCommand command);
}
