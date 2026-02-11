package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResReportActivity;
import sfera.reportproyect.entity.ReportActivity;

@Component
public class ReportActivityMapper {
    public ResReportActivity reportActivity(ReportActivity reportActivity){
        return ResReportActivity.builder()
                .id(reportActivity.getId())
                .description(reportActivity.getDescription())
                .reportId(reportActivity.getReport().getId())
                .build();
    }
}
