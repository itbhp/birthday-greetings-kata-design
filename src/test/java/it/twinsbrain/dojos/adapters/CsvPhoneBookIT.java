package it.twinsbrain.dojos.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.twinsbrain.dojos.domain.Friend;
import it.twinsbrain.dojos.domain.PhoneBookReadException;
import java.time.LocalDate;
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

  @Test
  void malformed_date_throws_error() {
    // given
    var content = """
    last_name, first_name, date_of_birth, email
    Justin, Bieber, 2000-01-01, bieber@email.com
    """.trim();

    // when, then
    var underTest = new CsvPhoneBook(content);
    assertThatThrownBy(underTest::all)
            .isInstanceOf(PhoneBookReadException.class);
  }

  @Test
  void non_empty_phone_book() {
    // given
    var content = """
    last_name, first_name, date_of_birth, email
    Bieber, Justin, 2000/01/01, bieber@email.com
    Bieber, Lea, 2004/01/01, lea.bieber@email.com
    Bieber, Fred, 2010/01/01, fred.bieber@email.com
    """.trim();

    // when, then
    var underTest = new CsvPhoneBook(content);
    var bieber = new Friend("Justin", "Bieber", LocalDate.parse("2000-01-01"), "bieber@email.com");
    var lea = new Friend("Lea", "Bieber", LocalDate.parse("2004-01-01"), "lea.bieber@email.com");
    var fred = new Friend("Fred", "Bieber", LocalDate.parse("2010-01-01"), "fred.bieber@email.com");
    assertThat(underTest.all()).containsExactlyInAnyOrder(bieber, lea, fred);
  }
}
