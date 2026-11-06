package com.university.repository;

import com.university.entity.Teaches;
import com.university.entity.TeachesId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeachesRepository extends JpaRepository<Teaches, TeachesId> {

    // property path: id.teacherId
    List<Teaches> findByIdTeacherId(Integer teacherId);
}
