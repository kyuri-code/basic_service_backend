package com.study.basic.service.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.study.basic.service.demo.mapper.TaskMapper;
import com.study.basic.service.demo.model.Task;
import com.study.basic.service.demo.model.Comment;

@Service
public class TaskService {
    
    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllTasks() {
        return taskMapper.findAll();
    }

    public Task getTaskById(Long id) {
        return taskMapper.findById(id);
    }

    public void createTask(Task task) {
        taskMapper.insert(task);
    }

    public void updateTask(Task task) {
        taskMapper.update(task);
    }

    public void deleteTask(Long id) {
        taskMapper.deleteCommentsByTaskId(id);
        taskMapper.delete(id);
    }

    public void addComment(Comment comment) {
        taskMapper.insertComment(comment);
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        return taskMapper.findCommentsByTaskId(taskId);
    }
}
