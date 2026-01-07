package com.Verzat.VerzatTechno.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.AlertRepository;

@Service
public class AlertService {

  @Autowired
  private AlertRepository alertRepo;

  public void createAlertIfRequired(Task task) {

    if (task.isCompleted() || task.getDueDate() == null) return;

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);

    if (task.getDueDate().isBefore(today)) {
      saveAlert(task, "HIGH", "Task \"" + task.getTitle() + "\" is overdue");
    }

    if (task.getDueDate().isEqual(tomorrow)) {
      saveAlert(task, "LOW", "Task \"" + task.getTitle() + "\" is due tomorrow");
    }
  }

  private void saveAlert(Task task, String type, String message) {
	    Alert alert = new Alert();

	    alert.setUserEmail(
	        task.getFolder().getUser().getEmail()
	    );

	    alert.setTaskId(task.getId());
	    alert.setAlertType(type);
	    alert.setMessage(message);

	    alertRepo.save(alert);
	}

}
