package com.university.controller;

import com.university.entity.Teacher;
import com.university.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository repo;

    public TeacherController(TeacherRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getOne(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher body) {
        if (body.getTeacherId() != null && repo.existsById(body.getTeacherId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();   // id already taken
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Integer id, @RequestBody Teacher body) {
        return repo.findById(id).map(existing -> {
            existing.setName(body.getName());
            existing.setDesignation(body.getDesignation());
            existing.setExperience(body.getExperience());
            existing.setSalary(body.getSalary());
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
