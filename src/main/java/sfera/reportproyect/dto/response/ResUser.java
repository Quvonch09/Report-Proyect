package sfera.reportproyect.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String imgUrl;
    private String role;
}
