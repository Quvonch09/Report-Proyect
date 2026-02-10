package sfera.reportproyect.dto.response;

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
public class ResReportDTO {

    private Long id;

    private String status;

    private String reportType;

    private String dangerType;

    private String category;

    private String reportName;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endTime;

    private String location;

    private Priority priority; //xavf darajasi

    private String department;

    private String assignee;

    private Long assigneeId;

    private String assigneeImg;

    private String reportUser;

    private Long reportUserId;

    private String reportUserImg;

    private List<String> fileUrls;

    private List<ResReportActivity>  activities;

}
