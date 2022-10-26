package com.aim.booking.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.aim.booking.annotation.BookingControllerTest;
import com.aim.booking.domain.BookingDto;
import com.aim.booking.persistence.entity.Room;
import com.aim.booking.security.login.LoginRequest;
import com.aim.booking.security.login.LoginResponse;
import com.aim.booking.utils.BookingTestUtils;
import com.aim.booking.utils.RoomTestUtil;
import com.aim.booking.utils.TestUtils;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@BookingControllerTest
public class BookingController {

  @Autowired
  TestUtils testUtils;
  @Autowired
  RoomTestUtil roomTestUtil;
  @Autowired
  BookingTestUtils bookingTestUtils;
  @Autowired
  private TestRestTemplate restTemplate;

  private final static String TEST_ADMIN_EMAIL = "admin@test.com";
  private final static String TEST_ADMIN_PASS = "admin";

  @Test
  public void when_CreateBooking_then_Http_Status_Created() {
    Room room = roomTestUtil.createRoomDbInstance("testRoom");
    BookingDto bookingDto = bookingTestUtils.buildBookingDto(room.getId(), OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1));
    String userToken = getTokenForUser(TEST_ADMIN_PASS, TEST_ADMIN_EMAIL);
    ResponseEntity<String> resp = restTemplate
        .exchange("/api/bookings/", HttpMethod.POST,
            testUtils.getHttpEntity(userToken, bookingDto), String.class);

    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  public void when_getAllBooking_then_BookingDtoList() {
    int bookingAmount = 5;
    Room room = roomTestUtil.createRoomDbInstance("testRoom");
    for (int i = 0; i < bookingAmount; i++) {
      bookingTestUtils.createBookingDbInstance(room.getId(), OffsetDateTime.now(),
          OffsetDateTime.now().plusHours(1));
    }
    String userToken = getTokenForUser(TEST_ADMIN_PASS, TEST_ADMIN_EMAIL);
    ResponseEntity<List<BookingDto>> resp = restTemplate
        .exchange("/api/bookings/", HttpMethod.GET,
            testUtils.getHttpEntity(userToken, null),
            new ParameterizedTypeReference<List<BookingDto>>() {
            });

    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<BookingDto> respBody = resp.getBody();
    assertThat(respBody.size()).isEqualTo(bookingAmount);
  }

  private String getTokenForUser(String pass, String email) {
    LoginRequest request = new LoginRequest(email, pass);
    LoginResponse response = restTemplate
        .exchange("/api/authentication/login", HttpMethod.POST, testUtils.loginHttpEntity(request),
            LoginResponse.class)
        .getBody();
    return Objects.requireNonNull(response).getToken();
  }
}
