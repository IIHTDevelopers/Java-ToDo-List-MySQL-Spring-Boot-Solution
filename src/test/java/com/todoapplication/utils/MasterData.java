package com.todoapplication.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.todoapplication.dto.ToDoDTO;

public class MasterData {

	public static ToDoDTO getTodoDTO() {
		ToDoDTO todoDTO = new ToDoDTO();
		todoDTO.setId(1L);
		todoDTO.setTitle("Complete Task");
		todoDTO.setDescription("Finish the project tasks");
		todoDTO.setCompleted(false);
		todoDTO.setPriority(3);

		return todoDTO;
	}

	public static List<ToDoDTO> getTodoDTOList() {
		List<ToDoDTO> todoDTOList = new ArrayList<>();

		ToDoDTO todoDTO = new ToDoDTO();
		todoDTO.setId(1L);
		todoDTO.setTitle("Complete Task");
		todoDTO.setDescription("Finish the project tasks");
		todoDTO.setCompleted(false);
		todoDTO.setPriority(3);

		todoDTOList.add(todoDTO);

		return todoDTOList;
	}

	public static Page<ToDoDTO> getToDoDTOPage(int pageNo, int pageSize) {
		List<ToDoDTO> todoDTOList = getTodoDTOList(); // Reusing the existing method to get the list

		int start = (int) Math.min(pageNo * pageSize, todoDTOList.size());
		int end = (int) Math.min((pageNo + 1) * pageSize, todoDTOList.size());

		List<ToDoDTO> pageContent = todoDTOList.subList(start, end);

		return new PageImpl<>(pageContent, PageRequest.of(pageNo, pageSize), todoDTOList.size());
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString();
	}
}
