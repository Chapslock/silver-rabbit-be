package com.chapslock.silver.rabbit.core.person;

import lombok.Value;

@FunctionalInterface
public interface SavePerson {
    Person execute(Request request);

    @Value(staticConstructor = "of")
    class Request {
        Person person;
    }

}
