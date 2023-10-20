package it.twinsbrain.dojos.domain;

import it.twinsbrain.dojos.ports.BirthdayPostman;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.time.LocalDate;

public class BirthdayGreeter {
    private final BirthdayPostman postman;
    private final PhoneBook phoneBook;

    public BirthdayGreeter(BirthdayPostman birthdayPostMan, PhoneBook phoneBook) {
        this.postman = birthdayPostMan;
        this.phoneBook = phoneBook;
    }

    public void greetFriendsBorn(LocalDate today) {
        phoneBook.all()
                .stream()
                .filter(friend -> friend.isBorn(today))
                .forEach(postman::sendMessageTo);
    }
}
