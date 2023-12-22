package com.todoapplication.exception;

import com.todoapplication.utils.MasterData;
import static com.todoapplication.utils.MasterData.getTodoDTO;
import static com.todoapplication.utils.TestUtils.currentTest;
import static com.todoapplication.utils.TestUtils.exceptionTestFile;
import static com.todoapplication.utils.TestUtils.testReport;
import static com.todoapplication.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.todoapplication.controller.ToDoController;
import com.todoapplication.dto.ToDoDTO;
import com.todoapplication.service.ToDoService;

@WebMvcTest(ToDoController.class)
@AutoConfigureMockMvc
public class ToDoExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToDoService todoService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateTodoInvalidDataException() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();
		todoDTO.setTitle(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/todos")
				.content(MasterData.asJsonString(todoDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateTodoInvalidDataException() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();
		todoDTO.setTitle(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/todos/" + todoDTO.getId())
				.content(MasterData.asJsonString(todoDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetTodoByIdResourceNotFoundException() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Todo not found");

		when(this.todoService.getToDoById(todoDTO.getId())).thenThrow(new ResourceNotFoundException("Todo not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos/" + todoDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}
