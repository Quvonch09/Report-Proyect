package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.UniversalEntity;

@Repository
public interface UniversalEntityRepository extends JpaRepository<UniversalEntity, Long> {
}
