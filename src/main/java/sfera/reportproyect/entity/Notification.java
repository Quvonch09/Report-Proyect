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
public class Notification extends BaseEntity {
    private String title;

    private String message;

    private String imgUrl;

    @ManyToOne
    private User user;

    private boolean read;
}
