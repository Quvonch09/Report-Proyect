package sfera.reportproyect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.dto.LocationDTO;
import sfera.reportproyect.entity.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean  existsByNameAndIdNot(String name, Long id);

    @Query("""
        SELECT l FROM Location l
        WHERE l.active = true
          AND (:name IS NULL OR TRIM(:name) = ''
               OR LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    Page<Location> search(@Param("name") String name,
                          Pageable pageable);

    List<LocationDTO> findAllByActiveTrue();

    Optional<Location> findByIdAndActiveTrue(Long id);
}
