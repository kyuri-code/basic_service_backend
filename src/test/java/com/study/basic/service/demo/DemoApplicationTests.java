package com.study.basic.service.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetNonExistentTask() throws Exception {
		mockMvc.perform(get("/api/tasks/{id}", 999L))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Task not found with id: 999"))
				.andExpect(jsonPath("$.statusCode").value(404));
	}

}
