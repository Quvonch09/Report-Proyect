package sfera.reportproyect.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqNotification {
    private String title;
    private String message;
    private Long userId;
}
