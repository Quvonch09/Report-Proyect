package sfera.reportproyect.controller;

import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.IdList;
import sfera.reportproyect.dto.request.ReqNotification;
import sfera.reportproyect.dto.request.ReqNotificationDTO;
import sfera.reportproyect.dto.response.ResNotification;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createNotification(@RequestBody ReqNotification req) {
        return ResponseEntity.ok(notificationService.saveNotification(req));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResNotification>> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }


    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ResNotification>>> getMyNotifications(@AuthenticationPrincipal User customUserDetails) {
        return ResponseEntity.ok(notificationService.getAllNotifications(customUserDetails));
    }


    @PutMapping("/read")
    public ResponseEntity<ApiResponse<String>> readNotification(@RequestBody IdList idList) {
        return ResponseEntity.ok(notificationService.isRead(idList));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateNotification(@RequestBody ReqNotificationDTO req) {
        return ResponseEntity.ok(notificationService.updateNotification(req));
    }


    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countNotification(@AuthenticationPrincipal User customUserDetails) {
        return ResponseEntity.ok(notificationService.countNotification(customUserDetails));
    }

}
