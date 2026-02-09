package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


}
