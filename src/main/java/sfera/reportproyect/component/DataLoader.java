package sfera.reportproyect.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Role;
import sfera.reportproyect.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args){
        if (ddl.equals("create")){
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .phone("998900000000")
                    .password(encoder.encode("admin123"))
                    .role(Role.ROLE_SUPER_ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);

            User anonymous = User.builder()
                    .firstName("Anonim")
                    .lastName("Anonim")
                    .phone("null")
                    .role(Role.ROLE_EMPLOYEE)
                    .enabled(true)
                    .password(encoder.encode("anonim123"))
                    .build();

            userRepository.save(anonymous);
        }
    }
}
