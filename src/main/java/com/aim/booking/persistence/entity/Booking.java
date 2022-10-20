package com.aim.booking.persistence.entity;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "booking")
public class Booking extends BaseEntity{

  @Column(name = "start_booking_time", nullable = false)
  private OffsetDateTime startBookingTime;

  @Column(name = "end_booking_time", nullable = false)
  private OffsetDateTime endBookingTime;

  @Column(name = "room_id", nullable = false, insertable = false, updatable = false)
  private String roomId;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

}
