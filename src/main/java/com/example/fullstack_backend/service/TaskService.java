package com.example.fullstack_backend.service;

import com.example.fullstack_backend.model.Comment;
import com.example.fullstack_backend.model.Task;
import com.example.fullstack_backend.repository.CommentRepository;
import com.example.fullstack_backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }



    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task createTask(Task task) {
        task.setCreatedDate(new Date());
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            Task taskToUpdate = existingTask.get();
            taskToUpdate.setTitle(updatedTask.getTitle());
            taskToUpdate.setDescription(updatedTask.getDescription());
            taskToUpdate.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(taskToUpdate);
        }
        return null;
    }

    public boolean deleteTask(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        commentRepository.deleteAll(comments);

        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> searchTasks(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
}
