package com.todoapplication.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapplication.entity.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

	List<ToDo> findByTitleContainingIgnoreCase(String title);

	List<ToDo> findByCompletedTrue();

	List<ToDo> findByPriorityGreaterThan(int priority);

	Page<ToDo> findAll(Pageable pageable);
}
