package com.todoapplication.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todoapplication.dto.ToDoDTO;

public interface ToDoService {

	ToDoDTO createToDo(ToDoDTO ToDoDTO);

	ToDoDTO getToDoById(Long id);

	ToDoDTO updateToDoById(Long id, ToDoDTO ToDoDTO);

	void deleteToDoById(Long id);

	List<ToDoDTO> searchToDosByName(String name);

	List<ToDoDTO> getCompletedToDos();

	List<ToDoDTO> getToDosWithPriorityGreaterThan(int priority);

	Page<ToDoDTO> getAllToDos(Pageable pageable);
}
