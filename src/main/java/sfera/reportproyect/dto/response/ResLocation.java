package sfera.reportproyect.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResLocation {
    private Long id;
    private String name;
    private Long filialId;
    private String filialName;
}
