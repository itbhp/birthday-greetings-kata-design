package it.twinsbrain.dojos.domain;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;

import it.twinsbrain.dojos.ports.BirthdayPostman;
import it.twinsbrain.dojos.ports.PhoneBook;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GreetingsTest {

  @Test
  void ignore_friends_whose_birthday_not_today() {
    // given
    var today = parse("2023-10-12");
    var joe = new Friend("Joe", "Black", parse("1984-10-12"), "joe.black@email.com");
    var bob = new Friend("Bob", "Sting", parse("1989-02-15"), "bob.sting@email.com");
    var alice = new Friend("Alice", "Kent", parse("1987-10-12"), "alice.kent@email.com");

    // given(phoneBook.all()).willReturn(List.of(joe, bob, alice));
    PhoneBook phoneBook = () -> List.of(joe, bob, alice);
    var friendsGreeted = new ArrayList<Friend>();
    BirthdayPostman postman = friendsGreeted::add;

    // when
    var greeter = new BirthdayGreeter(postman, phoneBook);
    greeter.greetFriendsBorn(today);

    // then
    //    var messagesCaptor = ArgumentCaptor.forClass(Friend.class);
    //    verify(birthdayPostMan, times(2)).sendMessageTo(messagesCaptor.capture());
    //    assertThat(messagesCaptor.getAllValues()).containsExactlyInAnyOrder(joe, alice);
    assertThat(friendsGreeted).containsExactlyInAnyOrder(joe, alice);
  }

  @Test
  void no_friends_no_messages_sent() {
    // given
    var today = parse("2023-10-12");
    //    given(phoneBook.all()).willReturn(List.of());
    PhoneBook phoneBook = List::of;
    var friendsGreeted = new ArrayList<Friend>();
    BirthdayPostman postman = friendsGreeted::add;

    // when
    var greeter = new BirthdayGreeter(postman, phoneBook);
    greeter.greetFriendsBorn(today);

    // then
    //    verifyNoInteractions(birthdayPostMan);
    assertThat(friendsGreeted).isEmpty();
  }
}
