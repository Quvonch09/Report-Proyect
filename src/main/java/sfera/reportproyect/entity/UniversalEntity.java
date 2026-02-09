package sfera.reportproyect.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sfera.reportproyect.entity.base.BaseEntity;
import sfera.reportproyect.entity.enums.TypeEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UniversalEntity extends BaseEntity {
    private String name;
    private boolean active;
}
