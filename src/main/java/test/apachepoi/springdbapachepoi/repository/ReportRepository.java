package test.apachepoi.springdbapachepoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.apachepoi.springdbapachepoi.model.ReportWithClients;

@Repository
public interface ReportRepository extends JpaRepository<ReportWithClients, Long> {
}