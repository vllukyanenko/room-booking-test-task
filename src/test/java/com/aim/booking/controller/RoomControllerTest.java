package com.aim.booking.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.aim.booking.annotation.BookingControllerTest;
import com.aim.booking.domain.RoomDto;
import com.aim.booking.security.login.LoginRequest;
import com.aim.booking.security.login.LoginResponse;
import com.aim.booking.utils.RoomTestUtil;
import com.aim.booking.utils.TestUtils;
import com.aim.booking.utils.UserTestUtils;
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
public class RoomControllerTest {

  @Autowired
  UserTestUtils userTestUtils;
  @Autowired
  TestUtils testUtils;
  @Autowired
  RoomTestUtil roomTestUtil;
  @Autowired
  private TestRestTemplate restTemplate;

  private final static String TEST_ADMIN_EMAIL = "admin@test.com";
  private final static String TEST_ADMIN_PASS = "admin";

  @Test
  public void when_CreateRoom_then_Http_Status_Created() {
    RoomDto roomDto = roomTestUtil.buildRoomDto("Test Room");
    String userToken = getTokenForUser(TEST_ADMIN_PASS, TEST_ADMIN_EMAIL);
    ResponseEntity<String> resp = restTemplate
        .exchange("/api/rooms/", HttpMethod.POST,
            testUtils.getHttpEntity(userToken, roomDto), String.class);

    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  public void when_getAllRooms_then_RoomDtoList() {
    int roomAmount = 5;
    for (int i = 0; i < 5; i++) {
      roomTestUtil.createRoomDbInstance("Test Room" + i);
    }
    String userToken = getTokenForUser(TEST_ADMIN_PASS, TEST_ADMIN_EMAIL);
    ResponseEntity<List<RoomDto>> resp = restTemplate
        .exchange("/api/rooms/", HttpMethod.GET,
            testUtils.getHttpEntity(userToken, null),
            new ParameterizedTypeReference<List<RoomDto>>() {
            });

    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<RoomDto> respBody = resp.getBody();
    assertThat(respBody.size()).isEqualTo(roomAmount);
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
