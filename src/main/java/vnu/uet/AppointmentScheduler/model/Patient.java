package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class Patient extends User {
}
