package com.chapslock.silver.rabbit.core.person;

import lombok.Value;

@FunctionalInterface
public interface UpdatePerson {
    Person execute(Request person);

    @Value(staticConstructor = "of")
    class Request {
        Person person;
    }
}
