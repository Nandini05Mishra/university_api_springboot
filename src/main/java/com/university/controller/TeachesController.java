package com.university.controller;

import com.university.entity.Teaches;
import com.university.entity.TeachesId;
import com.university.repository.TeachesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * teaches has only the two key columns, so there is nothing to update:
 * to change an assignment, delete it and create a new one.
 */
@RestController
@RequestMapping("/api/teaches")
public class TeachesController {

    private final TeachesRepository repo;

    public TeachesController(TeachesRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Teaches> getAll() {
        return repo.findAll();
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Teaches> getByTeacher(@PathVariable Integer teacherId) {
        return repo.findByIdTeacherId(teacherId);
    }

    @PostMapping
    public ResponseEntity<Teaches> create(@RequestBody Teaches t) {
        if (t.getId() == null || t.getId().getTeacherId() == null || t.getId().getCourseId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.existsById(t.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(t));
    }

    @DeleteMapping("/{teacherId}/{courseId}")
    public ResponseEntity<Void> delete(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        TeachesId id = new TeachesId(teacherId, courseId);
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
