package sfera.reportproyect.entity;

import jakarta.persistence.*;
import lombok.*;
import sfera.reportproyect.entity.base.BaseEntity;
import sfera.reportproyect.entity.enums.Priority;
import sfera.reportproyect.entity.enums.ReportEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Report extends BaseEntity {

    private String name;

    private String description;

    @ElementCollection
    private List<String> fileUrls;

    private String comment;

    @ManyToOne
    private UniversalEntity category;

    @ManyToOne
    private UniversalEntity reportType;

    @ManyToOne
    private UniversalEntity department;

    @ManyToOne
    private UniversalEntity dangerType;

    @Enumerated(EnumType.STRING)
    private ReportEnum reportStatus;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    private Location location;

    @ManyToOne
    private User reportUser;

    @ManyToOne
    private User assignedUser;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime completedTime;

    private boolean active;
}

