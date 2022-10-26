package com.aim.booking.persistence.entity;

import com.aim.booking.security.UserDetailsImpl;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity implements Serializable {

  @Id
  @Column(name = "id", updatable = false, unique = true, nullable = false)
  @GeneratedValue(generator = "ulid-generator")
  @GenericGenerator(name = "ulid-generator",
      strategy = "com.aim.booking.persistence.generator.ULIDIdGenerator")
  private String id;

  @Column(name = "creator", updatable = false, nullable = false)
  private String creator;

  @Column(name = "created", updatable = false, nullable = false)
  private OffsetDateTime created;

  @PrePersist
  public void created() {
    createdAt();
    createdBy();
  }

  private void createdAt() {
    if (this.created == null) {
      this.created = OffsetDateTime.now();
    }
  }

  private void createdBy() {
    Object principal = getPrincipal();
    this.creator = principal instanceof UserDetailsImpl ? ((UserDetailsImpl) principal).getEmail()
        : principal.toString();
  }

  Object getPrincipal() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Object principal = "anonymous";
    if (securityContext != null && securityContext.getAuthentication() != null) {
      principal = securityContext.getAuthentication().getPrincipal();
    }
    return principal;
  }

}