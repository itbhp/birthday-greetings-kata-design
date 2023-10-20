package it.twinsbrain.dojos.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import it.twinsbrain.dojos.domain.Friend;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class EMailBirthdayPostmanIT {

  private EMailBirthdayPostman mailMessageSender;

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
          .withConfiguration(GreenMailConfiguration.aConfig().withUser("smtpUser", "passwd"))
          .withPerMethodLifecycle(false);

  @BeforeEach
  void setUp() {
    var port = greenMail.getSmtp().getServerSetup().getPort();
    var host = greenMail.getSmtp().getServerSetup().getBindAddress();
    mailMessageSender = new EMailBirthdayPostman(
            new EMailBirthdayPostman.SmtpConfig(host, port, "smtpUser", "passwd"),
            "sender@email.com"
    );
  }

  @Test
  void send_greetings_to_friend_on_his_birthday() throws MessagingException {
    // given
    var friend =
            new Friend("John", "Lennon", LocalDate.parse("1984-09-09"),"john.lennon@email.com");
    // when
    mailMessageSender.sendMessageTo(friend);

    // then
    var receivedMessages = greenMail.getReceivedMessages();
    assertThat(receivedMessages.length).isEqualTo(1);
    assertThat(receivedMessages[0].getHeader("From")[0]).isEqualTo("sender@email.com");
    assertThat(receivedMessages[0].getAllRecipients()).contains(new InternetAddress("john.lennon@email.com"));
    assertThat(receivedMessages[0].getSubject()).isEqualTo("Happy birthday!");
    assertThat(GreenMailUtil.getBody(receivedMessages[0])).isEqualTo("Happy birthday, dear John!");
  }
}
