package test.apachepoi.springdbapachepoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.apachepoi.springdbapachepoi.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

