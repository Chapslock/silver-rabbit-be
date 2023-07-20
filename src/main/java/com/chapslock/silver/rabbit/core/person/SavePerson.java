package com.chapslock.silver.rabbit.core.person;

@FunctionalInterface
public interface SavePerson {
    Person execute(Person request);

}
