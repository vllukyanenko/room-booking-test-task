package com.aim.booking.persistence.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "room")
public class Room extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "seats_amount")
  private int seatsAmount;

  @Column(name = "floor")
  private String floor;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "roomId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  @Filter(name = "dateDueFilter", condition = ":checkIn >= OffsetDateTime.now()")
  private List<Booking> bookingList;
}
