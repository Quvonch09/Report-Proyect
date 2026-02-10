package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
