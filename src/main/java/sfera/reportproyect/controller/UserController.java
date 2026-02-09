package sfera.reportproyect.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqUser;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Role;
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


    @PutMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @Operation(description = "Agar bodyda id kiritilsa usha id dagi userni update qiladi. Agar kiritilmasa uzini update qiladi")
    public ResponseEntity<ApiResponse<String>> update(@AuthenticationPrincipal User user, @RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.updateUser(user, reqUser));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResPageable>> searchUsers(@RequestParam(required = false) String fullName,
                                                                @RequestParam(required = false) String phone,
                                                                @RequestParam(required = false) Role role,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(userService.searchUsers(fullName, phone, role, page, size));
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestParam Role role,@RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.saveUsers(role, reqUser));
    }
}
