package sfera.reportproyect.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResReportActivity {
    private Long id;
    private String description;
    private Long reportId;
}
