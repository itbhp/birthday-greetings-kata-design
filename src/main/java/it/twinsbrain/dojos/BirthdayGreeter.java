package it.twinsbrain.dojos;

import java.time.LocalDate;

public class BirthdayGreeter {
    private final MessageSender messageSender;
    private final PhoneBook phoneBook;

    public BirthdayGreeter(MessageSender messageSender, PhoneBook phoneBook) {
        this.messageSender = messageSender;
        this.phoneBook = phoneBook;
    }

    public void greetFriendsBorn(LocalDate today) {
        var friend = phoneBook.all().get(0);
        messageSender.sendMessageTo(friend);
    }
}
