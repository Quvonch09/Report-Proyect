package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;

import java.util.Optional;

@Repository
public interface UniversalEntityRepository extends JpaRepository<UniversalEntity, Long> {
    Optional<UniversalEntity> findByIdAndTypeEnum(Long id, TypeEnum typeEnum);
}
