package org.formation.coffee.application.port.in;

import org.formation.coffee.domain.model.Coffee;

public interface CreateCoffeeUseCase {

    Coffee createCoffee(CreateCoffeeCommand command);
}
