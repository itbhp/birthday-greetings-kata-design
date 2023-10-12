package it.twinsbrain.dojos.adapters;

import it.twinsbrain.dojos.domain.Friend;
import it.twinsbrain.dojos.domain.PhoneBookReadException;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPhoneBook implements PhoneBook {
  private final String content;
  private List<Friend> friends;

  public CsvPhoneBook(String content) {
    this.content = content;
  }

  @Override
  public List<Friend> all() {
    if (friends == null) {
      friends = parseContent();
    }
    return friends;
  }

  private List<Friend> parseContent() {
    if (content.isBlank()) {
      return List.of();
    }
    return Arrays.stream(content.split("\n"))
            .skip(1)
            .map(this::toFriend)
            .collect(Collectors.toList());
  }

    private Friend toFriend(String csvLine) {
        String[] columns = csvLine.split(", ");
        if(columns.length < 4){
            throw new PhoneBookReadException();
        }
        return null;
    }
}
