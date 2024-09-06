package com.study.basic.service.demo.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import com.study.basic.service.demo.exception.GlobalExceptionHandler;
import com.study.basic.service.demo.exception.ResourceNotFoundException;
import com.study.basic.service.demo.model.ErrorResponse;
import com.study.basic.service.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TaskController.class, GlobalExceptionHandler.class})
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testHealthCheckLogging() throws Exception {
        // Arrange
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        Level originalLevel = loggerConfig.getLevel();
        loggerConfig.setLevel(Level.DEBUG);
        ctx.updateLoggers();

        // Act
        mockMvc.perform(get("/api/tasks/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("HelloWorld!!"));

        // Assert
        assertThat(((FileAppender) loggerConfig.getAppenders().get("RollingFile")).getFileName())
                .contains("/var/log/myapi/myapi-log-");
        loggerConfig.setLevel(originalLevel);
        ctx.updateLoggers();
    }
    @Test
    public void testGetTaskByIdNotFound() throws Exception {
        long invalidTaskId = 999L;
        when(taskService.getTaskById(invalidTaskId)).thenReturn(null);

        mockMvc.perform(get("/api/tasks/{id}", invalidTaskId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: " + invalidTaskId))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    public void testHandleException() throws Exception {
        mockMvc.perform(get("/api/tasks/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.statusCode").value(500));
    }

}
