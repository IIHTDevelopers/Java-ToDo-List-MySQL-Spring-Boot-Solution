package com.todoapplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.todoapplication.dto.ToDoDTO;
import com.todoapplication.entity.ToDo;
import com.todoapplication.exception.ResourceNotFoundException;
import com.todoapplication.repo.ToDoRepository;
import com.todoapplication.service.ToDoService;

@Service
public class ToDoServiceImpl implements ToDoService {

	private final ToDoRepository toDoRepository;

	@Autowired
	public ToDoServiceImpl(ToDoRepository ToDoRepository) {
		this.toDoRepository = ToDoRepository;
	}

	@Override
	public ToDoDTO createToDo(ToDoDTO ToDoDTO) {
		ToDo ToDo = convertToEntity(ToDoDTO);
		ToDo savedToDo = toDoRepository.save(ToDo);
		return convertToDTO(savedToDo);
	}

	@Override
	public ToDoDTO getToDoById(Long id) {
		ToDo ToDo = toDoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ToDo not found"));
		return convertToDTO(ToDo);
	}

	@Override
	public ToDoDTO updateToDoById(Long id, ToDoDTO ToDoDTO) {
		ToDo existingToDo = toDoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ToDo not found"));

		// Update the fields of existingToDo with values from ToDoDTO
		existingToDo.setTitle(ToDoDTO.getTitle());
		existingToDo.setDescription(ToDoDTO.getDescription());
		existingToDo.setCompleted(ToDoDTO.isCompleted());
		existingToDo.setPriority(ToDoDTO.getPriority());

		ToDo updatedToDo = toDoRepository.save(existingToDo);
		return convertToDTO(updatedToDo);
	}

	@Override
	public void deleteToDoById(Long id) {
		if (!toDoRepository.existsById(id)) {
			throw new ResourceNotFoundException("ToDo not found");
		}
		toDoRepository.deleteById(id);
	}

	@Override
	public Page<ToDoDTO> getAllToDos(Pageable pageable) {
		Page<ToDo> ToDoEntities = toDoRepository.findAll(pageable);
		return ToDoEntities.map(this::convertToDTO);
	}

	@Override
	public List<ToDoDTO> searchToDosByName(String name) {
		List<ToDo> ToDoEntities = toDoRepository.findByTitleContainingIgnoreCase(name);
		return convertToDTOList(ToDoEntities);
	}

	@Override
	public List<ToDoDTO> getCompletedToDos() {
		List<ToDo> completedToDos = toDoRepository.findByCompletedTrue();
		return convertToDTOList(completedToDos);
	}

	@Override
	public List<ToDoDTO> getToDosWithPriorityGreaterThan(int priority) {
		List<ToDo> highPriorityToDos = toDoRepository.findByPriorityGreaterThan(priority);
		return convertToDTOList(highPriorityToDos);
	}

	private ToDoDTO convertToDTO(ToDo ToDo) {
		return new ToDoDTO(ToDo.getId(), ToDo.getTitle(), ToDo.getDescription(), ToDo.isCompleted(),
				ToDo.getPriority());
	}

	private ToDo convertToEntity(ToDoDTO ToDoDTO) {
		ToDo ToDo = new ToDo();
		ToDo.setId(ToDoDTO.getId());
		ToDo.setTitle(ToDoDTO.getTitle());
		ToDo.setDescription(ToDoDTO.getDescription());
		ToDo.setCompleted(ToDoDTO.isCompleted());
		ToDo.setPriority(ToDoDTO.getPriority());
		return ToDo;
	}

	private List<ToDoDTO> convertToDTOList(List<ToDo> todoEntities) {
		return todoEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
}
