package com.Verzat.VerzatTechno.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByFolder_Id(Long folderId);

    List<Task> findByFolder(Folder folder);

    List<Task> findByCompletedFalse();

    List<Task> findByDueDateIn(List<LocalDate> dates);

    void deleteByFolder_Id(Long folderId);
}
