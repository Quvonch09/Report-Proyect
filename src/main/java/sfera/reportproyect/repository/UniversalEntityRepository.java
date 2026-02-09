package sfera.reportproyect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;

@Repository
public interface UniversalEntityRepository extends JpaRepository<UniversalEntity, Long> {
    boolean existsByTypeEnumAndNameIgnoreCase(TypeEnum typeEnum, String name);

    @Query("""
        SELECT u FROM UniversalEntity u
        WHERE u.typeEnum = :typeEnum
          AND (:name IS NULL OR TRIM(:name) = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    Page<UniversalEntity> search(@Param("typeEnum") TypeEnum typeEnum,
                                 @Param("name") String name,
                                 Pageable pageable);

}
