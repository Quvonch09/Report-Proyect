package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.mapper.UserMapper;
import sfera.reportproyect.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ApiResponse<ResUser> getMe(User user){
        return ApiResponse.success(userMapper.resUser(user), "Success");
    }

    public ApiResponse<String> updateUser(){
        return null;
    }
}
