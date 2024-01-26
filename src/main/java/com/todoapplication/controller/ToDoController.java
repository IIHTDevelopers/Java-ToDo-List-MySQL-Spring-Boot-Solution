package com.todoapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.dto.ToDoDTO;
import com.todoapplication.service.ToDoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todos")
@Validated
public class ToDoController {

	private final ToDoService todoService;

	@Autowired
	private Environment env;

	@Autowired
	public ToDoController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@PostMapping
	public ResponseEntity<ToDoDTO> createTodo(@Valid @RequestBody ToDoDTO todoDTO) {
		ToDoDTO createdTodo = todoService.createToDo(todoDTO);
		return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ToDoDTO> getTodoById(@PathVariable Long id) {
		ToDoDTO todoDTO = todoService.getToDoById(id);
		return new ResponseEntity<>(todoDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ToDoDTO> updateTodoById(@PathVariable Long id, @Valid @RequestBody ToDoDTO todoDTO) {
		ToDoDTO updatedTodo = todoService.updateToDoById(id, todoDTO);
		return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
		todoService.deleteToDoById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<Page<ToDoDTO>> getAllTodos(Pageable pageable) {
		Page<ToDoDTO> todoPage = todoService.getAllToDos(pageable);
		return new ResponseEntity<>(todoPage, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<ToDoDTO>> searchTodosByName(@RequestParam("name") String name) {
		List<ToDoDTO> searchedTodos = todoService.searchToDosByName(name);
		return new ResponseEntity<>(searchedTodos, HttpStatus.OK);
	}

	@GetMapping("/completed")
	public ResponseEntity<List<ToDoDTO>> getCompletedTodos() {
		List<ToDoDTO> completedTodos = todoService.getCompletedToDos();
		return new ResponseEntity<>(completedTodos, HttpStatus.OK);
	}

	@GetMapping("/priorityGreaterThan/{priority}")
	public ResponseEntity<List<ToDoDTO>> getTodosWithPriorityGreaterThan(@PathVariable int priority) {
		List<ToDoDTO> highPriorityTodos = todoService.getToDosWithPriorityGreaterThan(priority);
		return new ResponseEntity<>(highPriorityTodos, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<String> getProfile() {
		String profileData = env.getProperty("profile.validate.data", "This is default profile");
		return new ResponseEntity<>(profileData, HttpStatus.OK);
	}
}
