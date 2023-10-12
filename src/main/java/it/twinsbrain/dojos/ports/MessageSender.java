package it.twinsbrain.dojos.ports;

import it.twinsbrain.dojos.domain.Friend;

public interface MessageSender {
    void sendMessageTo(Friend friend);
}
