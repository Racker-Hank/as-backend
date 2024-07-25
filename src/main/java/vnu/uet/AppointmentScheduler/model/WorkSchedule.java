package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private WorkScheduleType workScheduleType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

//	@Column(name = "is_active")
//	private boolean isActive;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "workSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    public enum WorkScheduleType {
        DEFAULT,
        TEMPORARY
    }
}
