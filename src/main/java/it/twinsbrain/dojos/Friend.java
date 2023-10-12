package it.twinsbrain.dojos;

import java.time.LocalDate;

public record Friend(String name, String surname, LocalDate birthDate, String email) {}
