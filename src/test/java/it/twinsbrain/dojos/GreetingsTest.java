package it.twinsbrain.dojos;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GreetingsTest {

  private BirthdayGreeter underTest;

  @Mock
  private MessageSender messageSender;

  @Mock
  private PhoneBook phoneBook;

  @BeforeEach
  void setUp() {
    underTest = new BirthdayGreeter(messageSender, phoneBook);
  }

  @Test
  void ignore_friends_whose_birthday_not_today() {
    // given
    var today = parse("2023-10-12");
    var joe = new Friend("Joe", "Black", parse("1984-10-12"), "joe.black@email.com");
    var bob = new Friend("Bob", "Sting", parse("1989-02-15"), "bob.sting@email.com");
    var alice = new Friend("Alice", "Kent", parse("1987-10-12"), "alice.kent@email.com");

    given(phoneBook.all()).willReturn(List.of(joe, bob, alice));

    // when
    underTest.greetFriendsBorn(today);

    // then
    var messagesCaptor = ArgumentCaptor.forClass(Friend.class);
    verify(messageSender, times(2)).sendMessageTo(messagesCaptor.capture());
    assertThat(messagesCaptor.getAllValues()).containsExactlyInAnyOrder(joe, alice);
  }

  @Test
  void no_friends_no_messages_sent() {
    // given
    var today = parse("2023-10-12");
    given(phoneBook.all()).willReturn(List.of());

    // when
    underTest.greetFriendsBorn(today);

    // then
    verifyNoInteractions(messageSender);
  }
}
