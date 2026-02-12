package sfera.reportproyect.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDashboardDTO {
    int countAll;
    int countAdmin;
    int countUser;
}
