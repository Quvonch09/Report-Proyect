package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.Report;
import sfera.reportproyect.entity.enums.Category;
import sfera.reportproyect.entity.enums.ReportEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByAssignedUser_Id(Long id);

    Optional<Report> findByIdAndActiveTrue(Long id);

    @Query(value = """
        select r.* from report r join location l on r.location_id = l.id where
        (:filialId IS NULL OR l.filial_id = :filialId) AND
        (:category IS NULL OR r.category = :category) AND
        (:reportTypeId IS NULL OR r.report_type_id = :reportTypeId) AND
        (:departmentId IS NULL OR r.department_id = :departmentId) AND
        (:dangerTypeId IS NULL OR r.danger_type_id = :dangerTypeId) AND
        (:priority IS NULL OR r.priority = :priority) order by r.created_at desc
        """, nativeQuery = true)
    List<Report> searchReport(@Param("filialId") Long filialId,
                              @Param("category") String category,
                              @Param("reportTypeId") Long reportTypeId,
                              @Param("departmentId") Long departmentId,
                              @Param("dangerTypeId") Long dangerTypeId,
                              @Param("priority") String priority);

    List<Report> findAllByActiveTrue();

    long countByCategoryAndActiveTrue(Category category);
    long countByCategoryAndActiveTrueAndAssignedUser_Id(Category category, Long assignedUserId);
    long countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum reportEnum, Long assignedUserId);

    // YILLIK: 12 oy bo‘yicha (masalan: 2026 yilning Jan..Dec)
    @Query(value = """
        SELECT to_char(date_trunc('month', r.start_time), 'Mon') AS label,
               COUNT(*)::bigint AS count
        FROM report r
        WHERE r.active = true AND
        (:userId IS NULL OR r.assigned_user_id = :userId)
          AND r.start_time >= :fromDate
          AND r.start_time <  :toDate
        GROUP BY date_trunc('month', r.start_time)
        ORDER BY date_trunc('month', r.start_time)
        """, nativeQuery = true)
    List<Object[]> countByMonthRange(@Param("fromDate") LocalDateTime fromDate,
                                     @Param("toDate") LocalDateTime toDate,
                                     @Param("userId")  Long userId);

    // OYLIK: 1 oy ichida kunlar bo‘yicha (1..31)
    @Query(value = """
        SELECT to_char(date_trunc('day', r.start_time), 'DD') AS label,
               COUNT(*)::bigint AS count
        FROM report r
        WHERE r.active = true AND
        (:userId IS NULL OR r.assigned_user_id = :userId)
          AND r.start_time >= :fromDate
          AND r.start_time <  :toDate
        GROUP BY date_trunc('day', r.start_time)
        ORDER BY date_trunc('day', r.start_time)
        """, nativeQuery = true)
    List<Object[]> countByDayRange(@Param("fromDate") LocalDateTime fromDate,
                                   @Param("toDate") LocalDateTime toDate,
                                   @Param("userId")   Long userId);

    // HAFTALIK: 1 hafta ichida kun nomi bo‘yicha (Mon..Sun)
    @Query(value = """
        SELECT to_char(date_trunc('day', r.start_time), 'Dy') AS label,
               COUNT(*)::bigint AS count
        FROM report r
        WHERE r.active = true AND
        (:userId IS NULL OR r.assigned_user_id = :userId)
          AND r.start_time >= :fromDate
          AND r.start_time <  :toDate
        GROUP BY date_trunc('day', r.start_time)
        ORDER BY date_trunc('day', r.start_time)
        """, nativeQuery = true)
    List<Object[]> countByWeekDaysRange(@Param("fromDate") LocalDateTime fromDate,
                                        @Param("toDate") LocalDateTime toDate,
                                        @Param("userId")    Long userId);

    @Query(value = """
        SELECT r.category       AS category,
               r.report_status  AS status,
               COUNT(*)::bigint AS count
        FROM report r
        WHERE r.active = true AND
        (:userId IS NULL OR r.assigned_user_id = :userId)
          AND r.start_time >= :fromDate
          AND r.start_time <  :toDate
        GROUP BY r.category, r.report_status
        ORDER BY r.category, r.report_status
        """, nativeQuery = true)
    List<Object[]> countByCategoryAndStatus(@Param("fromDate") LocalDateTime fromDate,
                                            @Param("toDate") LocalDateTime toDate,
                                            @Param("userId") Long userId);
}
