package sfera.reportproyect.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String imgUrl;
    private String role;
    private String filialName;
    private ResDashBoard report;
}
