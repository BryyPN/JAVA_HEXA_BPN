package org.formation.coffee.application.port.in;

import org.formation.coffee.domain.model.Price;

import java.util.Objects;

public record CreateCoffeeCommand(String name, Price price) {

    public CreateCoffeeCommand {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(price, "price must not be null");
    }
}
