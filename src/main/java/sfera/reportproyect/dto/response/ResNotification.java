package sfera.reportproyect.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResNotification {
    private Long id;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime date;
}
