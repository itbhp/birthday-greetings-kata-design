import static org.assertj.core.api.Assertions.assertThat;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import it.twinsbrain.dojos.adapters.CsvPhoneBook;
import it.twinsbrain.dojos.adapters.EMailBirthdayPostman;
import it.twinsbrain.dojos.domain.BirthdayGreeter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AcceptanceTest {
  @RegisterExtension
  static GreenMailExtension greenMail =
      new GreenMailExtension(ServerSetupTest.SMTP)
          .withConfiguration(GreenMailConfiguration.aConfig().withUser("smtpUser", "passwd"))
          .withPerMethodLifecycle(false);

  private BirthdayGreeter greeter;

  @BeforeEach
  void setUp() throws URISyntaxException, IOException {
    var port = greenMail.getSmtp().getServerSetup().getPort();
    var host = greenMail.getSmtp().getServerSetup().getBindAddress();
    var mailMessageSender =
        new EMailBirthdayPostman(
            new EMailBirthdayPostman.SmtpConfig(host, port, "smtpUser", "passwd"), "sender@email.com");
    // given
    var csvPhoneBook = new CsvPhoneBook(readFixture());
    greeter = new BirthdayGreeter(mailMessageSender, csvPhoneBook);
  }

  @Test
  void friend_born_today_will_receive_an_email() throws MessagingException {
    // when
    var thisDay = LocalDate.parse("2023-10-08");
    greeter.greetFriendsBorn(thisDay);
    // then
    var receivedMessages = greenMail.getReceivedMessages();
    assertThat(receivedMessages.length).isEqualTo(1);

    var receivedMessage = receivedMessages[0];
    assertThat(receivedMessage.getHeader("From")[0]).isEqualTo("sender@email.com");
    assertThat(receivedMessage.getAllRecipients())
        .contains(new InternetAddress("john.doe@foobar.com"));
    assertThat(receivedMessage.getSubject()).isEqualTo("Happy birthday!");
    assertThat(GreenMailUtil.getBody(receivedMessage)).isEqualTo("Happy birthday, dear John!");
  }

  private String readFixture() throws URISyntaxException, IOException {
    var url = Objects.requireNonNull(this.getClass().getResource("/friends.csv"));
    return Files.readString(Paths.get(url.toURI()));
  }
}
