package com.aim.booking.annotation;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import com.aim.booking.BookingServiceApplication;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = BookingServiceApplication.class)
@TestPropertySource("classpath:additional-test.properties")
@Transactional(propagation = Propagation.NEVER)
@ActiveProfiles
@Sql(scripts = "classpath:sql/cleanup-tests-data.sql", executionPhase = AFTER_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BookingIntegrationTest {

  @AliasFor(annotation = ActiveProfiles.class, attribute = "profiles") String[] activeProfiles() default {
      "${integration.tests.profile.active}"};
}