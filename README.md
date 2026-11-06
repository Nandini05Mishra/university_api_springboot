# University API (Spring Boot + MySQL)

REST CRUD API on the `university` database from the DBMS assignment (department, teacher, student, course, teaches, enrollment).

Structure: `entity` (6 entities + 2 composite-key classes), `repository` (6), `controller` (6), `exception` (1 global handler).

## Run

1. Make sure MySQL is running and the `university` database with its 6 tables exists (your DBMS assignment script).
2. Open `src/main/resources/application.properties` and set the database name, `spring.datasource.username` and `spring.datasource.password`.
3. Java 17+ and Maven:
   - IDE: open the folder as a Maven project and run `UniversityApplication`
   - or terminal: `mvn spring-boot:run`
4. API runs at http://localhost:8080

## Endpoints

| Resource   | Base URL          | Operations |
|------------|-------------------|------------|
| Department | /api/departments  | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |
| Teacher    | /api/teachers     | same |
| Student    | /api/students     | same |
| Course     | /api/courses      | same |
| Teaches    | /api/teaches      | GET, GET /teacher/{teacherId}, POST, DELETE /{teacherId}/{courseId} |
| Enrollment | /api/enrollments  | GET, GET /{studentId}/{courseId}, GET /student/{studentId}, POST, PUT /{studentId}/{courseId}, DELETE /{studentId}/{courseId} |

## Sample requests (Postman: Body > raw > JSON)

POST /api/departments
```json
{ "deptId": 7, "deptName": "Civil", "building": "Block F", "budget": 1800000 }
```

POST /api/students
```json
{ "studentId": 1012, "name": "Riya Singh", "city": "Noida", "admissionYear": 2024, "cgpa": 8.4,
  "department": { "deptId": 1 } }
```

POST /api/teachers
```json
{ "teacherId": 108, "name": "Dr. Test Teacher", "designation": "Professor", "experience": 12, "salary": 100000,
  "department": { "deptId": 2 } }
```

POST /api/courses
```json
{ "courseId": 208, "courseTitle": "Machine Learning", "credits": 4, "department": { "deptId": 1 } }
```

POST /api/teaches
```json
{ "id": { "teacherId": 102, "courseId": 201 } }
```

POST /api/enrollments
```json
{ "id": { "studentId": 1010, "courseId": 205 }, "marks": 85, "attendance": 90 }
```

PUT /api/enrollments/1010/205
```json
{ "marks": 90, "attendance": 95 }
```

## Notes

- IDs are supplied by the client (tables do not auto-generate them). POST returns 409 if the id already exists.
- A bad foreign key, a missing required field, or deleting a row that other tables still reference returns 409.
- `ddl-auto=none`: Hibernate never changes your tables.
- Column names assumed: `dept_id, dept_name, building, budget` / `teacher_id, name, designation, experience, salary, dept_id` / `student_id, name, city, admission_year, cgpa, dept_id` / `course_id, course_title, credits, dept_id` / `teaches(teacher_id, course_id)` / `enrollment(student_id, course_id, marks, attendance)`.
