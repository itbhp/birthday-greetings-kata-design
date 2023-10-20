package it.twinsbrain.dojos.adapters;

import it.twinsbrain.dojos.domain.Friend;
import it.twinsbrain.dojos.domain.PhoneBookReadException;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CsvPhoneBook implements PhoneBook {
  private final String content;
  private List<Friend> friends;
  private final Lock lock = new ReentrantLock();

  public CsvPhoneBook(String content) {
    this.content = content;
  }

  @Override
  public List<Friend> all() {
    lock.lock();
    try {
      if (friends == null) {
        friends = parse(content);
      }
    } finally {
      lock.unlock();
    }
    return friends;
  }

  private List<Friend> parse(String content) {
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
    if (columns.length < 4) {
      throw new PhoneBookReadException();
    }
    var name = columns[1];
    var lastName = columns[0];
    var birthDate = parseDate(columns[2]);
    var email = columns[3];
    return new Friend(name, lastName, birthDate, email);
  }

  private LocalDate parseDate(String dateString) {
    try {
      return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    } catch (DateTimeParseException e) {
      throw new PhoneBookReadException();
    }
  }
}
