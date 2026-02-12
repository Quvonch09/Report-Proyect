package sfera.reportproyect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import sfera.reportproyect.entity.base.BaseEntity;
import sfera.reportproyect.entity.enums.TypeEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UniversalEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TypeEnum typeEnum;
    private boolean active;
}
