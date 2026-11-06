package com.university.controller;

import com.university.entity.Student;
import com.university.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Student> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getOne(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student body) {
        if (body.getStudentId() != null && repo.existsById(body.getStudentId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();   // id already taken
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id, @RequestBody Student body) {
        return repo.findById(id).map(existing -> {
            existing.setName(body.getName());
            existing.setCity(body.getCity());
            existing.setAdmissionYear(body.getAdmissionYear());
            existing.setCgpa(body.getCgpa());
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
