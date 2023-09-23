package com.TMSystem.Repository;

import com.TMSystem.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    boolean existsById(long id);
}
