package com.aim.booking.persistence.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "room")
public class Room extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "seats_amount")
  private int seatsAmount;

  @Column(name = "floor_number")
  private byte floorNumber;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "roomId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Booking> bookingList;
}
