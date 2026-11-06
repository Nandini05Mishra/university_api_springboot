package com.university.controller;

import com.university.entity.Course;
import com.university.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository repo;

    public CourseController(CourseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Course> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getOne(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course body) {
        if (body.getCourseId() != null && repo.existsById(body.getCourseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();   // id already taken
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Integer id, @RequestBody Course body) {
        return repo.findById(id).map(existing -> {
            existing.setCourseTitle(body.getCourseTitle());
            existing.setCredits(body.getCredits());
            existing.setDepartment(body.getDepartment());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);   // FK violations become 409 via GlobalExceptionHandler
        return ResponseEntity.noContent().build();
    }
}
