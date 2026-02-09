package sfera.reportproyect.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Barcha uchun profile kurish API")
    public ResponseEntity<ApiResponse<ResUser>> getMe(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getMe(user));
    }
}
