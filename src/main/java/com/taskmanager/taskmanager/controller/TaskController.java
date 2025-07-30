package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //Create a new task
    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Task created successfully",
                        "data", savedTask
                )
        );
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<Object> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(
                Map.of(
                        "message", "Tasks fetched successfully",
                        "data", tasks
                )
        );
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Long id) {
    Optional<Task> task = taskService.getTaskById(id);

    if (task.isPresent()) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task fetched successfully");
        response.put("data", task.get());
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "Task not found"));
    }
}


    // Get tasks by category
    @GetMapping("/category")
    public ResponseEntity<Object> getTasksByCategory(@RequestParam String category) {
        List<Task> tasks = taskService.getTasksByCategory(category);
        return ResponseEntity.ok(
                Map.of("message", "Tasks fetched for category: " + category, "data", tasks)
        );
    }

    // Get tasks by deadline
    @GetMapping("/deadline")
    public ResponseEntity<Object> getTasksByDeadline(@RequestParam String datetime) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(datetime);
        List<Task> tasks = taskService.getTasksByDeadline(parsedDateTime);
        return ResponseEntity.ok(
                Map.of("message", "Tasks fetched for deadline: " + datetime, "data", tasks)
        );
    }

    // Get tasks by status
    @GetMapping("/status")
    public ResponseEntity<Object> getTasksByStatus(@RequestParam String status) {
       List<Task> tasks = taskService.getTasksByStatus(status);
       return ResponseEntity.ok(
               Map.of("message", "Tasks with status: " + status, "data", tasks)
       );
    }


    // Search tasks by title
    @GetMapping("/search")
    public ResponseEntity<Object> searchTasksByTitle(@RequestParam String keyword) {
        List<Task> tasks = taskService.searchTasksByTitle(keyword);
        return ResponseEntity.ok(
                Map.of("message", "Tasks matching keyword: " + keyword, "data", tasks)
        );
    }

    // Delete task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            taskService.deleteTask(id);
            return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found"));
        }
    }

    // PUT: Full update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> existingTask = taskService.getTaskById(id);

        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDeadline(updatedTask.getDeadline());
            task.setCategory(updatedTask.getCategory());
            task.setStatus(updatedTask.getStatus());

            Task savedTask = taskService.saveTask(task);

            return ResponseEntity.ok(
                    Map.of("message", "Task updated successfully (full)", "data", savedTask)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found"));
        }
    }


    // PATCH: Partial update
    @PatchMapping("/{id}")
       public ResponseEntity<Object> patchTask(@PathVariable Long id, @RequestBody Task updatedTask) {
       Optional<Task> existingTask = taskService.getTaskById(id);

           if (existingTask.isPresent()) {
              Task task = existingTask.get();

           if (updatedTask.getTitle() != null) task.setTitle(updatedTask.getTitle());
           if (updatedTask.getDeadline() != null) task.setDeadline(updatedTask.getDeadline());
           if (updatedTask.getCategory() != null) task.setCategory(updatedTask.getCategory());
           if (updatedTask.getStatus() != null) task.setStatus(updatedTask.getStatus());

        // persist checkbox value
           task.setCompleted(updatedTask.isCompleted());

           Task savedTask = taskService.saveTask(task);

            return ResponseEntity.ok(
                Map.of("message", "Task updated successfully (partial)", "data", savedTask)
         );
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found"));
         }
    }
}


