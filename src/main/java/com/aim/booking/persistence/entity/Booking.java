package com.aim.booking.persistence.entity;

import java.time.OffsetDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "booking")
public class Booking extends BaseEntity {

  @Version
  @Setter(value = AccessLevel.NONE)
  @Column(name = "version")
  private Integer version;

  @Column(name = "check_in", nullable = false)
  private OffsetDateTime checkIn;

  @Column(name = "check_out", nullable = false)
  private OffsetDateTime checkOut;

  @Column(name = "room_id")
  private String roomId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(referencedColumnName = "id", name = "room_id", updatable = false, insertable = false)
  private Room room;

}
