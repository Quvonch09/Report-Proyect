package sfera.reportproyect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByIdAndEnabledTrue(Long id);

    @Query(value = """
        select u.* from users u where
        (:fullName IS NULL OR LOWER(u.first_name) LIKE LOWER(CONCAT('%', :fullName, '%'))) OR
                (:fullName IS NULL OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND
                        (:role IS NULL OR u.role = role) order by u.created_at desc
        """, nativeQuery = true)
    Page<User> searchUser(@Param("fullName") String fullName,
                          @Param("phone") String phone,
                          @Param("role") String role,
                          Pageable pageable);

    boolean existsByPhoneAndEnabledTrue(String phone);

    Optional<User> findByCode(Integer code);

    int countByRoleAndEnabledTrue(Role role);

}
