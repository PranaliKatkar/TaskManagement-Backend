package com.Verzat.VerzatTechno.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Verzat.VerzatTechno.Dto.TaskCreateRequest;
import com.Verzat.VerzatTechno.Dto.TaskResponse;
import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.FolderRepo;
import com.Verzat.VerzatTechno.Repository.TaskRepo;

@Service
public class TaskService {

    private static final String BASE_DIR = "user_folders";

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private AlertService alertService;

    public TaskResponse addTask(Long folderId, TaskCreateRequest request) {
        Folder folder = folderRepo.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setCompleted(false);
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority() != null ? request.getPriority() : "Medium");
        task.setDueDate(request.getDueDate());
        task.setFolder(folder);

        Task savedTask = taskRepo.save(task);

        alertService.generateAlertForTask(
                savedTask,
                LocalDate.now(ZoneId.of("Asia/Kolkata"))
        );

        createFolderIfNotExists(folderId);
        saveTaskToFile(folderId, savedTask);

        return new TaskResponse(savedTask);
    }

    public List<TaskResponse> getTasks(Long folderId) {
        if (!folderRepo.existsById(folderId))
            throw new RuntimeException("Folder not found");

        return taskRepo.findByFolder_Id(folderId)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public TaskResponse markTaskCompleted(Long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(true);
        Task updatedTask = taskRepo.save(task);

        alertService.generateAlertForTask(
                updatedTask,
                LocalDate.now(ZoneId.of("Asia/Kolkata"))
        );

        updateTaskFile(updatedTask);
        return new TaskResponse(updatedTask);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        deleteTaskFile(task);
        taskRepo.delete(task);
    }

    private void createFolderIfNotExists(Long folderId) {
        try {
            Files.createDirectories(Paths.get(BASE_DIR, "folder_" + folderId));
        } catch (IOException e) {
            throw new RuntimeException("Could not create folder directory");
        }
    }

    private void saveTaskToFile(Long folderId, Task task) {
        try {
            Path file = Paths.get(
                    BASE_DIR,
                    "folder_" + folderId,
                    "task_" + task.getId() + ".txt"
            );

            String content =
                    "Title: " + task.getTitle() +
                    "\nDescription: " + task.getDescription() +
                    "\nPriority: " + task.getPriority() +
                    "\nDueDate: " + task.getDueDate() +
                    "\nCompleted: " + task.isCompleted();

            Files.write(
                    file,
                    content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save task file");
        }
    }

    private void updateTaskFile(Task task) {
        saveTaskToFile(task.getFolder().getId(), task);
    }

    private void deleteTaskFile(Task task) {
        try {
            Path file = Paths.get(
                    BASE_DIR,
                    "folder_" + task.getFolder().getId(),
                    "task_" + task.getId() + ".txt"
            );
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete task file");
        }
    }
}
