package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.Location;
import sfera.reportproyect.entity.UniversalEntity;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean  existsByNameAndIdNot(String name, Long id);
}
