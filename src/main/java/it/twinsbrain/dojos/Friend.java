package it.twinsbrain.dojos;

import java.time.LocalDate;

public record Friend(String name, String surname, LocalDate birthDate, String email) {
    public boolean isBorn(LocalDate today) {
        return birthDateSameDay(today) && birthDateSameMonth(today);
    }

    private boolean birthDateSameMonth(LocalDate today) {
        return birthDate.getMonth().equals(today.getMonth());
    }

    private boolean birthDateSameDay(LocalDate today) {
        return birthDate.getDayOfMonth() == today.getDayOfMonth();
    }
}
