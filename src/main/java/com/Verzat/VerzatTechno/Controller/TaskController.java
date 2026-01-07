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

    @PostMapping("/{folderId}")
    public TaskResponse addTask(
            @PathVariable Long folderId,
            @RequestBody TaskCreateRequest request) {
        return taskService.addTask(folderId, request);
    }

    @GetMapping("/folder/{folderId}")
    public List<TaskResponse> getTasks(@PathVariable Long folderId) {
        return taskService.getTasks(folderId);
    }

    @PutMapping("/complete/{taskId}")
    public TaskResponse completeTask(@PathVariable Long taskId) {
        return taskService.markTaskCompleted(taskId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
