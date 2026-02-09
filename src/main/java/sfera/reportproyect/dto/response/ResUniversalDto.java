package sfera.reportproyect.dto.response;

import lombok.*;
import sfera.reportproyect.entity.enums.TypeEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResUniversalDto {
    private Long id;
    private String name;
    private TypeEnum type;
}
