package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
