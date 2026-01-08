package com.Verzat.VerzatTechno.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Verzat.VerzatTechno.Entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long>{
	  List<Alert> findByUserEmailOrderByCreatedAtDesc(String userEmail);

	  boolean existsByTaskIdAndAlertType(Long id, String type);

	  void deleteByCreatedAtBefore(LocalDateTime cutoff);


}
