package it.twinsbrain.dojos.ports;

import it.twinsbrain.dojos.domain.Friend;
import java.util.List;

@FunctionalInterface
public interface PhoneBook {
    List<Friend> all();
}
