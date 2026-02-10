package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfera.reportproyect.entity.ReportActivity;

import java.util.List;

@Repository
public interface ReportActivityRepository extends JpaRepository<ReportActivity, Long> {
    List<ReportActivity> findAllByReport_Id(Long id);
}
