package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.Notification;
import sfera.reportproyect.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
    long countAllByUser(User user);
}
