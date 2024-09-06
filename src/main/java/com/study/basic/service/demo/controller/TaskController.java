package com.study.basic.service.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.basic.service.demo.model.Comment;
import com.study.basic.service.demo.model.Task;
import com.study.basic.service.demo.service.TaskService;
import com.study.basic.service.demo.exception.ResourceNotFoundException;

        logger.info("Received a healthcheck request");
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "HelloWorld!!";
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByTaskId(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) throw new ResourceNotFoundException("Task not found with id: " + id);

        List<Comment> comments = taskService.getCommentsByTaskId(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return task;
    }

    @PostMapping("/{id}/comments/add")
    public ResponseEntity<Comment> addCommentToTask(@PathVariable Long id, @RequestBody Comment comment) {
        Task task = taskService.getTaskById(id);
        if (task == null) throw new ResourceNotFoundException("Task not found with id: " + id);

        comment.setTask(task);
        taskService.addComment(comment);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Task task = taskService.getTaskById(id);
        if (task == null) throw new ResourceNotFoundException("Task not found with id: " + id);

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        taskService.updateTask(task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) throw new ResourceNotFoundException("Task not found with id: " + id);

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
