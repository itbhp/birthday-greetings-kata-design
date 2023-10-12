package it.twinsbrain.dojos.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import it.twinsbrain.dojos.domain.PhoneBookReadException;
import org.junit.jupiter.api.Test;

class CsvPhoneBookIT {

  @Test
  void empty_file_empty_phone_book() {
    // given
    var content = "";

    // when
    var underTest = new CsvPhoneBook(content);

    // then
    assertThat(underTest.all()).isEmpty();
  }

  @Test
  void file_with_just_header_empty_phone_book() {
    // given
    var content = "last_name, first_name, date_of_birth, email";

    // when
    var underTest = new CsvPhoneBook(content);

    // then
    assertThat(underTest.all()).isEmpty();
  }

  @Test
  void file_missing_columns_throws_error() {
    // given
    var content = """
    last_name, first_name, date_of_birth, email
    Justin
    """.trim();

    // when, then
    var underTest = new CsvPhoneBook(content);
    assertThatThrownBy(underTest::all)
            .isInstanceOf(PhoneBookReadException.class);
  }
}
