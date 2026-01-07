package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Dto.TaskCreateRequest;
import com.Verzat.VerzatTechno.Dto.TaskResponse;
import com.Verzat.VerzatTechno.Service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ✅ Add task to folder
    @PostMapping("/{folderId}")
    public TaskResponse addTask(
            @PathVariable Long folderId,
            @RequestBody TaskCreateRequest request) {

        if (request == null || request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Task title is required");
        }

        return taskService.addTask(folderId, request.getTitle());
    }

    // ✅ Get all tasks of a folder
    @GetMapping("/folder/{folderId}")
    public List<TaskResponse> getTasks(@PathVariable Long folderId) {
        return taskService.getTasks(folderId);
    }

    // ✅ Mark task as completed
    @PutMapping("/complete/{taskId}")
    public TaskResponse completeTask(@PathVariable Long taskId) {
        return taskService.markTaskCompleted(taskId);
    }

    // ✅ Delete task
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
