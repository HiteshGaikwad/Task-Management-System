package com.TMSystem.Controller;

import com.TMSystem.DTO.RequestTask;
import com.TMSystem.DTO.ResponseTask;
import com.TMSystem.Entity.Task;
import com.TMSystem.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTask> getTask(@PathVariable Long id){
        ResponseTask task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody RequestTask task) {
        Task newTask= taskService.createTask(task);
         if(newTask==null){
             return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
         } else {
             return new ResponseEntity<>("New task has been created successfully.", HttpStatus.CREATED);
         }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody RequestTask task)  {
        Task updatedTask = taskService.updateTask(id, task);
        if (updatedTask != null) {
            return ResponseEntity.ok("Task has been updated successfully.");
        } else {
            return new ResponseEntity<>("Task not found.",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String message= taskService.deleteTask(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTask>> getAllTasks() {
        List<ResponseTask> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<String> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        String message = taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{taskId}/unassign")
    public ResponseEntity<Task> unassignTask(@PathVariable Long taskId){
        Task task = taskService.unassignTaskFromUser(taskId);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}