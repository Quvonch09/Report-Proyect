package sfera.reportproyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import sfera.reportproyect.entity.base.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Location extends BaseEntity {
    private String name;
    @ManyToOne
    private UniversalEntity filial;
    private boolean active;
}
