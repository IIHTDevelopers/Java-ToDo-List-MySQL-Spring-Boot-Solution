package com.todoapplication.functional;

import static com.todoapplication.utils.MasterData.asJsonString;
import static com.todoapplication.utils.MasterData.getToDoDTOPage;
import static com.todoapplication.utils.MasterData.getTodoDTO;
import static com.todoapplication.utils.MasterData.getTodoDTOList;
import static com.todoapplication.utils.TestUtils.businessTestFile;
import static com.todoapplication.utils.TestUtils.currentTest;
import static com.todoapplication.utils.TestUtils.testReport;
import static com.todoapplication.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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
public class ToDoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToDoService todoService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateTodo() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();

		when(this.todoService.createToDo(any())).thenReturn(todoDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/todos").content(asJsonString(todoDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(todoDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetTodoById() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();

		when(this.todoService.getToDoById(any())).thenReturn(todoDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(todoDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateTodoById() throws Exception {
		ToDoDTO todoDTO = getTodoDTO();

		when(this.todoService.updateToDoById(any(), any())).thenReturn(todoDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/todos/1").content(asJsonString(todoDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(todoDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteTodoById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetAllTodos() throws Exception {
		Page<ToDoDTO> todoDTOList = getToDoDTOPage(0, 1);

		when(this.todoService.getAllToDos(any())).thenReturn(todoDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(todoDTOList)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testSearchTodosByName() throws Exception {
		String name = "Task";
		List<ToDoDTO> todoDTOList = getTodoDTOList();

		when(this.todoService.searchToDosByName(name)).thenReturn(todoDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos/search?name=" + name)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(todoDTOList)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetCompletedTodos() throws Exception {
		List<ToDoDTO> completedTodoDTOList = getTodoDTOList(); // Change this to represent completed todos

		when(this.todoService.getCompletedToDos()).thenReturn(completedTodoDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos/completed")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(completedTodoDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetTodosWithPriorityGreaterThan() throws Exception {
		int priority = 3; // Change this to the desired priority value
		List<ToDoDTO> highPriorityTodoDTOList = getTodoDTOList(); // Change this to represent todos with higher priority

		when(this.todoService.getToDosWithPriorityGreaterThan(priority)).thenReturn(highPriorityTodoDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/todos/priorityGreaterThan/" + priority)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(highPriorityTodoDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}
}
