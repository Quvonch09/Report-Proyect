package sfera.reportproyect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;

import java.util.Optional;

import java.util.List;

@Repository
public interface UniversalEntityRepository extends JpaRepository<UniversalEntity, Long> {
    boolean existsByTypeEnumAndNameIgnoreCase(TypeEnum typeEnum, String name);

    @Query("""
    SELECT u FROM UniversalEntity u
    WHERE u.typeEnum = :typeEnum
      AND u.active = true
      AND (
          COALESCE(:name, '') = ''
          OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))
      )
""")
    Page<UniversalEntity> search(@Param("typeEnum") TypeEnum typeEnum,
                                 @Param("name") String name,
                                 Pageable pageable);

    @Query(value = """
    select * from universal_entity where active=true and type_enum = ?1
    """, nativeQuery = true)
    List<UniversalEntity> findAllAndActiveTrue(TypeEnum typeEnum);

    Optional<UniversalEntity> findByIdAndActiveTrue(Long id);

    Optional<UniversalEntity> findByIdAndTypeEnumAndActiveTrue(Long id, TypeEnum typeEnum);


    boolean existsByNameAndIdNot(String name, Long id);
    Optional<UniversalEntity> findByIdAndTypeEnum(Long id, TypeEnum typeEnum);
}
