package com.university.controller;

import com.university.entity.Enrollment;
import com.university.entity.EnrollmentId;
import com.university.repository.EnrollmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository repo;

    public EnrollmentController(EnrollmentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Enrollment> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{studentId}/{courseId}")
    public ResponseEntity<Enrollment> getOne(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        return repo.findById(new EnrollmentId(studentId, courseId)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getByStudent(@PathVariable Integer studentId) {
        return repo.findByIdStudentId(studentId);
    }

    @PostMapping
    public ResponseEntity<Enrollment> create(@RequestBody Enrollment e) {
        if (e.getId() == null || e.getId().getStudentId() == null || e.getId().getCourseId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.existsById(e.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(e));
    }

    @PutMapping("/{studentId}/{courseId}")
    public ResponseEntity<Enrollment> update(@PathVariable Integer studentId, @PathVariable Integer courseId,
                                             @RequestBody Enrollment e) {
        return repo.findById(new EnrollmentId(studentId, courseId)).map(existing -> {
            existing.setMarks(e.getMarks());
            existing.setAttendance(e.getAttendance());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<Void> delete(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        EnrollmentId id = new EnrollmentId(studentId, courseId);
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
