package it.twinsbrain.dojos.adapters;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

class CsvPhoneBookIT {

  @Test
  void empty_file_empty_phone_book() {
      // given
      var content = "";
      var inputStream = new ByteArrayInputStream(content.getBytes(UTF_8));

      // when
      var underTest = new CsvPhoneBook(inputStream);

      // then
      assertThat(underTest.all()).isEmpty();
  }
}
