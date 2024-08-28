package com.study.basic.service.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.basic.service.demo.model.Comment;
import com.study.basic.service.demo.model.Task;

@Mapper
public interface TaskMapper {

    List<Task> findAll();

    Task findById(Long id);

    void insert(Task task);

    void update(Task task);

    void delete(Long id);

    void deleteCommentsByTaskId(Long id);

    void insertComment(Comment comment);

    List<Comment> findCommentsByTaskId(Long taskId);
    
}
