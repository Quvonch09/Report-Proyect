package sfera.reportproyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import sfera.reportproyect.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ReportActivity extends BaseEntity {

    private String description;

    @ManyToOne
    private Report report;
}
