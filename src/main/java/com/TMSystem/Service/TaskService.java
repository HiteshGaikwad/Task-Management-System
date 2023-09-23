package com.TMSystem.Service;


import com.TMSystem.DTO.Convertor;
import com.TMSystem.DTO.RequestTask;
import com.TMSystem.DTO.ResponseTask;
import com.TMSystem.Entity.Task;
import com.TMSystem.Entity.User;
import com.TMSystem.Repository.TaskRepository;
import com.TMSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Cacheable("tasks")
    public ResponseTask getTaskById(Long id) {
        // This method's result will be cached if called with the same 'id'
        boolean check= taskRepository.existsById(id);
        if(!check){
            return null;
        }
        Task task = taskRepository.getReferenceById(id);
        ResponseTask responseTask= Convertor.convertToResponseTask(task);
        return responseTask;
    }

    public Task createTask(RequestTask task) {
        try {
            Task newTask = Convertor.convertToTask(task);
            taskRepository.save(newTask);
            return newTask;
        } catch (Exception e){
            return null;
        }

    }

    public Task updateTask(Long id, RequestTask updatedTask) {
        // Retrieve the existing task
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            // Update the existing task with the new data
            if(updatedTask.getTitle()!=null) {
                existingTask.setTitle(updatedTask.getTitle());
            }
            if(updatedTask.getDescription()!=null) {
                existingTask.setDescription(updatedTask.getDescription());
            }
            if(updatedTask.getDueDate()!=null) {
                existingTask.setDueDate(updatedTask.getDueDate());
            }
            return taskRepository.save(existingTask);
        } else {
            //When the task with the given ID doesn't exist
            return null;
        }
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public String deleteTask(Long id) {
        boolean check= taskRepository.existsById(id);
        if(check) {
            Task task= taskRepository.getReferenceById(id);
            taskRepository.deleteById(id);
            return "Task deleted successfully.";
        } else{
            return "Task not found.";
        }
    }

    public List<ResponseTask> getAllTasks() {
        List<ResponseTask> responseTasks= new ArrayList<>();
        List<Task> tasks=taskRepository.findAll();
        for(Task task: tasks){
            responseTasks.add(Convertor.convertToResponseTask(task));
        }
        return responseTasks;
    }

    public String assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (task != null && user != null) {
            task.setAssignedUser(user);
            taskRepository.save(task);
            return "Task assigned successfully.";
        } else {
            return "Invalid. Please enter valid userId and taskId";
        }
    }

    public Task unassignTaskFromUser(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task != null) {
            task.setAssignedUser(null);
            return taskRepository.save(task);
        } else {
            return null;
        }
    }
}

