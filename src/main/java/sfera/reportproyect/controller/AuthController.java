package sfera.reportproyect.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.AuthRegister;
import sfera.reportproyect.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @Pattern(
                    regexp = "^998(9[012345789]|6[0123456789]|7[0123456789]|8[0123456789]|3[0123456789]|5[0123456789])[0-9]{7}$",
                    message = "Telefon raqam xato kiritilgan"
            )
            @Valid @RequestParam String phone,
            @RequestParam String password
    ){
        return ResponseEntity.ok(authService.login(phone, password));
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Integer>> registerUser(@RequestBody AuthRegister authRegister){
        return ResponseEntity.ok(authService.register(authRegister));
    }


    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<String>> activateUser(@RequestParam Integer code){
        return ResponseEntity.ok(authService.activate(code));
    }

}
