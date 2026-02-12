package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.AuthRegister;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Role;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.repository.UniversalEntityRepository;
import sfera.reportproyect.repository.UserRepository;
import sfera.reportproyect.security.JwtService;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UniversalEntityRepository universalEntityRepository;

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


    public ApiResponse<Integer> register(AuthRegister authRegister){
        boolean b = userRepository.existsByPhoneAndEnabledTrue(authRegister.getPhone());
        if (b) {
            return ApiResponse.error("User already exists");
        }

        UniversalEntity filial = universalEntityRepository.findByIdAndTypeEnum(authRegister.getFilialId(), TypeEnum.FILIAL).orElseThrow(
                () -> new DataNotFoundException("Filial not found")
        );

        Random random = new Random();
        int code = 10000 + random.nextInt(90000);

        User user = User.builder()
                .phone(authRegister.getPhone())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .enabled(false)
                .firstName(authRegister.getFirstName())
                .lastName(authRegister.getLastName())
                .role(Role.ROLE_EMPLOYEE)
                .imgUrl(null)
                .filial(filial)
                .code(code)
                .build();
        userRepository.save(user);
        return ApiResponse.success(code, "Please activated your account");
    }


    public ApiResponse<String> activate(Integer code){
        User user = userRepository.findByCode(code).orElseThrow(
                () -> new DataNotFoundException("User not found OR invalid code")
        );

        if (user.getCode() == code){
            user.setEnabled(true);
            user.setCode(0);
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
        userRepository.save(user);
        return ApiResponse.success(token, user.getRole().name());
    }


}