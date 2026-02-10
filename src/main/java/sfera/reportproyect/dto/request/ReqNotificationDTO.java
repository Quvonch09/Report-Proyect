package sfera.reportproyect.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqNotificationDTO {
    private Long id;
    private String title;
    private String message;
    private Long userId;
}
