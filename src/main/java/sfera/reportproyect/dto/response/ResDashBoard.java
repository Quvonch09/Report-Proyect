package sfera.reportproyect.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResDashBoard {
    private Long nearMissCount;
    private Long observationCount;
    private Long accidentCount;
    private Long incidentCount;
    private Long acceptedCount;
    private Long cancelledCount;
    private Long newReportCount;
    private Long inProgressCount;
    private Long completedCount;
}
