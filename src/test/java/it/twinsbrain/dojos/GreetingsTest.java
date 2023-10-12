package it.twinsbrain.dojos;

import static java.time.LocalDate.parse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GreetingsTest {
  /*
   1. 1 friend with birthday today -> 1 mail will be sent
   2. 3 friends two of them having birthday today -> 2 mails sent
   3. no friends having birthday today -> no mail sent
  */

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
  void one_friend_birthday_one_birthday_email() {
    // given
    var today = parse("2023-10-12");
    var friend = new Friend("Joe", "Black", parse("1984-10-12"), "joe.black@email.com");

    given(phoneBook.all()).willReturn(List.of(friend));

    // when
    underTest.greetFriendsBorn(today);

    // then
    verify(messageSender, times(1)).sendMessageTo(friend);
  }
}
