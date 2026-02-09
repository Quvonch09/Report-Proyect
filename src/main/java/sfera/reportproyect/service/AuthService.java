package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.repository.UserRepository;
import sfera.reportproyect.security.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<String> login(String phone, String password) {
        Optional<User> optionalUser = userRepository.findByPhone(phone);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isEnabled()) {
                return ApiResponse.error("Bu user faol emas. Adminlarga murojaat qiling");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ApiResponse.error("Invalid password");
            }

            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            return ApiResponse.success(token, user.getRole().name());
        }

        return ApiResponse.error("User topilmadi");
    }


}