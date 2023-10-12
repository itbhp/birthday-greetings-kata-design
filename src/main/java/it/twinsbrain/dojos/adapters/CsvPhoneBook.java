package it.twinsbrain.dojos.adapters;

import it.twinsbrain.dojos.domain.Friend;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class CsvPhoneBook implements PhoneBook {
    public CsvPhoneBook(InputStream inputStream) {
    }

    @Override
    public List<Friend> all() {
        return Collections.emptyList();
    }
}
