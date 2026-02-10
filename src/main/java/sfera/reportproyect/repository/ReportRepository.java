package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByAssignedUser_Id(Long id);

    Optional<Report> findByIdAndActiveTrue(Long id);

    @Query(value = """
        select r.* from report r join location l on r.location_id = l.id where
        (:filialId IS NULL OR l.filial_id = :filialId) AND
        (:categoryId IS NULL OR r.category_id = :categoryId) AND
        (:reportTypeId IS NULL OR r.report_type_id = :reportTypeId) AND
        (:departmentId IS NULL OR r.department_id = :departmentId) AND
        (:dangerTypeId IS NULL OR r.danger_type_id = :dangerTypeId) AND
        (:priority IS NULL OR r.priority = :priority) order by r.created_at desc
        """, nativeQuery = true)
    List<Report> searchReport(@Param("filialId") Long filialId,
                              @Param("categoryId") Long categoryId,
                              @Param("reportTypeId") Long reportTypeId,
                              @Param("departmentId") Long departmentId,
                              @Param("dangerTypeId") Long dangerTypeId,
                              @Param("priority") String priority);

    List<Report> findAllByActiveTrue();
}
