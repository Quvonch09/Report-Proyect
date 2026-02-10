package sfera.reportproyect.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import sfera.reportproyect.entity.enums.Priority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqReportDTO {

    private Long id;

    private Long reportTypeId;

    private Long dangerTypeId;

    private Long categoryId;

    private String reportName;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endTime;

    private Long locationId;

    private Priority priority; //xavf darajasi

    private Long departmentId;

    private Long assigneeId;

    private List<String> fileUrls;

    private boolean isAnonymous;  //anonim sifatida yuborish

}
