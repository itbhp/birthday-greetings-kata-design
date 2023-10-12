package it.twinsbrain.dojos.domain;

import it.twinsbrain.dojos.ports.MessageSender;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.time.LocalDate;

public class BirthdayGreeter {
    private final MessageSender messageSender;
    private final PhoneBook phoneBook;

    public BirthdayGreeter(MessageSender messageSender, PhoneBook phoneBook) {
        this.messageSender = messageSender;
        this.phoneBook = phoneBook;
    }

    public void greetFriendsBorn(LocalDate today) {
        phoneBook.all()
                .stream()
                .filter(friend -> friend.isBorn(today))
                .forEach(messageSender::sendMessageTo);
    }
}
