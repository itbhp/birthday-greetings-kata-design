package it.twinsbrain.dojos.adapters;

import it.twinsbrain.dojos.domain.Friend;
import it.twinsbrain.dojos.domain.MessageDeliveryException;
import it.twinsbrain.dojos.ports.BirthdayPostman;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EMailBirthdayPostman implements BirthdayPostman {
  private final SmtpConfig smtpConfig;
  private final String sender;

  public EMailBirthdayPostman(SmtpConfig smtpConfig, String sender) {
    this.smtpConfig = smtpConfig;
    this.sender = sender;
  }

  @Override
  public void sendMessageTo(Friend friend) {
    var email = createBirthdayemail(friend);
    try (var mailer = newMailer()) {
      mailer.sendMail(email);
    } catch (Exception e) {
      throw new MessageDeliveryException();
    }
  }

  private Mailer newMailer() {
    return MailerBuilder.withSMTPServer(
            smtpConfig.host(), smtpConfig.port(), smtpConfig.smtpUser(), smtpConfig.smtpPassword())
        .withTransportStrategy(TransportStrategy.SMTP)
        .buildMailer();
  }

  private Email createBirthdayemail(Friend friend) {
    return EmailBuilder.startingBlank()
        .from(sender)
        .to(friend.email())
        .withSubject("Happy birthday!")
        .withPlainText("Happy birthday, dear %s!".formatted(friend.name()))
        .buildEmail();
  }

  public record SmtpConfig(String host, int port, String smtpUser, String smtpPassword) {}
}
