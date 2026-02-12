package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.UserDashboardDTO;
import sfera.reportproyect.dto.request.ReqUser;
import sfera.reportproyect.dto.response.ResDashBoard;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.dto.response.ResUserDTO;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Category;
import sfera.reportproyect.entity.enums.Role;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.mapper.UserMapper;
import sfera.reportproyect.repository.ReportRepository;
import sfera.reportproyect.repository.UniversalEntityRepository;
import sfera.reportproyect.repository.UserRepository;
import sfera.reportproyect.security.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UniversalEntityRepository universalEntityRepository;
    private final JwtService jwtService;
    private final ReportRepository reportRepository;
    private final ReportService reportService;

    public ApiResponse<ResUser> getMe(User user){
        return ApiResponse.success(userMapper.resUser(user), "Success");
    }

    public ApiResponse<String> updateUser(User user,ReqUser reqUser){
        if (reqUser.getId()!=null){
            user = userRepository.findByIdAndEnabledTrue(reqUser.getId()).orElseThrow(
                    () -> new DataNotFoundException("User not found")
            );
        }

        UniversalEntity department = universalEntityRepository.
                findByIdAndTypeEnum(reqUser.getDepartmentId(), TypeEnum.DEPARTMENT_TYPE).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        UniversalEntity filial = universalEntityRepository.
                findByIdAndTypeEnum(reqUser.getFilialId(), TypeEnum.FILIAL).orElseThrow(
                        () -> new DataNotFoundException("Department not found")
                );

        user.setFirstName(reqUser.getFirstName());
        user.setLastName(reqUser.getLastName());
        user.setImgUrl(reqUser.getImgUrl());
        user.setDepartment(department);
        user.setFilial(filial);

        if (!user.getPhone().equals(reqUser.getPhone())){
            user.setPhone(reqUser.getPhone());
            userRepository.save(user);
            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );
            return ApiResponse.success(token, "Success");
        }
        userRepository.save(user);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> deleteUser(Long id){
        User user = userRepository.findByIdAndEnabledTrue(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        int size = reportRepository.findByAssignedUser_Id(user.getId()).size();
        if (size != 0){
            return ApiResponse.error("The user cannot be deleted.");
        }

        user.setEnabled(false);
        userRepository.save(user);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<ResPageable> searchUsers(String fullName,
                                                String phone,
                                                Role role,
                                                int page, int size){
        Page<User> users = userRepository.
                searchUser(fullName, phone, role != null ? role.name() : null, PageRequest.of(page, size));
        if (users.getTotalElements() == 0){
            return ApiResponse.error("User not found");
        }

        List<ResUser> list = users.getContent().stream().map(userMapper::resUser).toList();
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(users.getTotalElements())
                .totalPage(users.getTotalPages())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }


    public ApiResponse<String> saveUsers(Role role,ReqUser reqUser){
        boolean b = userRepository.existsByPhoneAndEnabledTrue(reqUser.getPhone());
        if (b){
            return ApiResponse.error("Phone number already in use");
        }

        UniversalEntity filial = universalEntityRepository.
                findByIdAndTypeEnum(reqUser.getFilialId(), TypeEnum.FILIAL).orElseThrow(
                        () -> new DataNotFoundException("Department not found")
                );

        UniversalEntity department = null;
        if (reqUser.getDepartmentId() == null || reqUser.getDepartmentId() == 0){
             department = universalEntityRepository.
                    findByIdAndTypeEnum(reqUser.getDepartmentId(), TypeEnum.DEPARTMENT_TYPE).orElseThrow(
                            () -> new DataNotFoundException("Department not found")
                    );
        }

        User user = User.builder()
                .phone(reqUser.getPhone())
                .role(role)
                .firstName(reqUser.getFirstName())
                .lastName(reqUser.getLastName())
                .imgUrl(reqUser.getImgUrl())
                .filial(filial)
                .department(department)
                .build();
        userRepository.save(user);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<UserDashboardDTO> getUserDashboard(){
        int countAdmin = userRepository.countByRoleAndEnabledTrue(Role.ROLE_ADMIN);
        int countUser = userRepository.countByRoleAndEnabledTrue(Role.ROLE_EMPLOYEE);
        UserDashboardDTO userDashboardDTO = UserDashboardDTO.builder()
                .countAdmin(countAdmin)
                .countUser(countUser)
                .countAll(countAdmin + countUser)
                .build();
        return ApiResponse.success(userDashboardDTO, "Success");
    }


    public ApiResponse<ResUserDTO> getOne(Long id){
        User user = userRepository.findByIdAndEnabledTrue(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );


        ResUserDTO resUserDTO;
        if (user.getRole().equals(Role.ROLE_ADMIN)){
            ResDashBoard data = reportService.getCountDashboard(user).getData();
            resUserDTO = userMapper.resUserDTO(user, data);
        } else {
           resUserDTO = userMapper.resUserDTO(user,null);
        }

        return ApiResponse.success(resUserDTO, "Success");
    }
}
