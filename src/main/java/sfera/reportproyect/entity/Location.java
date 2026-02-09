package sfera.reportproyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sfera.reportproyect.entity.base.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location extends BaseEntity {
    private String name;
    @ManyToOne
    private UniversalEntity filial;
    private boolean active;
}
