package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.IdList;
import sfera.reportproyect.dto.request.ReqNotification;
import sfera.reportproyect.dto.request.ReqNotificationDTO;
import sfera.reportproyect.dto.response.ResNotification;
import sfera.reportproyect.entity.Notification;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Role;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.mapper.NotificationMapper;
import sfera.reportproyect.repository.NotificationRepository;
import sfera.reportproyect.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public ApiResponse<String> saveNotification(ReqNotification reqNotification){
        User user = userRepository.findByIdAndEnabledTrue(reqNotification.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Notification notification = Notification.builder()
                .title(reqNotification.getTitle())
                .message(reqNotification.getMessage())
                .user(user)
                .read(false)
                .build();
        notificationRepository.save(notification);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateNotification(ReqNotificationDTO reqNotificationDTO){
        Notification notification = notificationRepository.findById(reqNotificationDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Notification not found")
        );

        User user = userRepository.findByIdAndEnabledTrue(reqNotificationDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        notification.setTitle(reqNotificationDTO.getTitle());
        notification.setMessage(reqNotificationDTO.getMessage());
        notification.setUser(user);
        notificationRepository.save(notification);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> deleteNotification(Long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Notification not found")
        );
        notificationRepository.delete(notification);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> isRead(IdList idList){
        List<Notification> allById = notificationRepository.findAllById(idList.getIdList());
        allById.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(allById);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<List<ResNotification>> getAllNotifications(User user){
        List<Notification> notificationList;
        if (user.getRole().equals(Role.ROLE_EMPLOYEE)){
            notificationList = notificationRepository.findAllByUser(user);
        } else {
            notificationList = notificationRepository.findAll();
        }
        List<ResNotification> list = notificationList.stream().map(notificationMapper::resNotification).toList();

        return ApiResponse.success(list, "Success");
    }

    public ApiResponse<Long> countNotification(User user){
        long count;
        if (user.getRole().equals(Role.ROLE_EMPLOYEE)){
            count = notificationRepository.countAllByUser(user);
        } else {
            count = notificationRepository.count();
        }

        return ApiResponse.success(count, "Success");
    }


    public ApiResponse<ResNotification> getNotificationById(Long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Notification not found")
        );

        return ApiResponse.success(notificationMapper.resNotification(notification), "Success");
    }
}
