package sfera.reportproyect.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegister {
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private Long filialId;
}
