package it.twinsbrain.dojos.ports;

import it.twinsbrain.dojos.domain.Friend;

@FunctionalInterface
public interface BirthdayPostman {
    void sendMessageTo(Friend friend);
}
