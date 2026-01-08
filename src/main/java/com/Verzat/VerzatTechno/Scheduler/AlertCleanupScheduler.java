package com.Verzat.VerzatTechno.Scheduler;

	import java.time.LocalDateTime;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.scheduling.annotation.Scheduled;
	import org.springframework.stereotype.Component;

	import com.Verzat.VerzatTechno.Repository.AlertRepository;

	@Component
	public class AlertCleanupScheduler {

	    @Autowired
	    private AlertRepository alertRepo;

	    @Scheduled(cron = "0 * * * * ?")
	    public void removeExpiredAlerts() {
	        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
	        alertRepo.deleteByCreatedAtBefore(cutoff);
	    }
	}


