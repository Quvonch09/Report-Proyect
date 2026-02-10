package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResNotification;
import sfera.reportproyect.entity.Notification;

import java.time.LocalDateTime;

@Component
public class NotificationMapper {
    public ResNotification resNotification(Notification notification){
        return ResNotification.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .read(notification.isRead())
                .date(LocalDateTime.parse(notification.getCreatedAt().toString()))
                .build();
    }
}
