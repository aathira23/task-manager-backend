package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCategoryIgnoreCase(String category);

    List<Task> findByDeadline(LocalDateTime deadline);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    List<Task> findByStatusIgnoreCase(String status);

}

