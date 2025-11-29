package ua.unsober.backend.feature.studentenrollment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.aspects.allowedstage.AllowedAtStage;
import ua.unsober.backend.common.aspects.limit.Limited;
import ua.unsober.backend.common.enums.EnrollmentStage;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student-enrollment")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    @PostMapping
    public StudentEnrollmentResponseDto create(@Valid @RequestBody StudentEnrollmentRequestDto dto) {
        return studentEnrollmentService.create(dto);
    }

    @GetMapping
    public List<StudentEnrollmentResponseDto> getAll() {
        return studentEnrollmentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentEnrollmentResponseDto getById(@PathVariable UUID id) {
        return studentEnrollmentService.getById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<StudentEnrollmentResponseDto> getAllByStudentId(@PathVariable UUID studentId) {
        return studentEnrollmentService.getAllByStudentId(studentId);
    }

    @GetMapping("/student-and-year")
    public List<StudentEnrollmentResponseDto> getAllByStudentIdAndYear(
            @RequestParam UUID studentId,
            @RequestParam Integer year) {
        return studentEnrollmentService.getAllByStudentIdAndYear(studentId, year);
    }

    @GetMapping("/years/{studentId}")
    public List<Integer> getAllYearsByStudentId(@PathVariable UUID studentId) {
        return studentEnrollmentService.getAllYearsByStudentId(studentId);
    }

    @GetMapping("/exists")
    boolean existsByStudentAndCourseId(
            @RequestParam UUID studentId,
            @RequestParam UUID courseId){
        return studentEnrollmentService.existsByStudentAndCourseId(studentId, courseId);
    }

    @GetMapping("/course/{courseId}")
    public List<StudentEnrollmentResponseDto> getAllByCourseId(@PathVariable UUID courseId) {
        return studentEnrollmentService.getAllByCourseId(courseId);
    }

    @PatchMapping("/{id}")
    public StudentEnrollmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentEnrollmentRequestDto dto) {
        return studentEnrollmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentEnrollmentService.delete(id);
    }

    @PostMapping("/enroll-self")
    @Limited(perMinute = 30)
    @AllowedAtStage(stages = {EnrollmentStage.CORRECTION, EnrollmentStage.COURSES})
    public StudentEnrollmentResponseDto enrollSelf(@RequestParam UUID courseId) {
        return studentEnrollmentService.enrollSelf(courseId);
    }

    @PatchMapping("/change-group")
    @Limited(perMinute = 30)
    @AllowedAtStage(stages = {EnrollmentStage.CORRECTION, EnrollmentStage.GROUPS})
    public StudentEnrollmentResponseDto changeGroup(
            @RequestParam UUID enrollmentId,
            @RequestParam UUID groupId) {
        return studentEnrollmentService.changeGroup(enrollmentId, groupId);
    }

    @PatchMapping("/clear-group/{enrollmentId}")
    @Limited(perMinute = 30)
    @AllowedAtStage(stages = {EnrollmentStage.CORRECTION, EnrollmentStage.GROUPS})
    public void withdrawFromGroup(
            @PathVariable UUID enrollmentId) {
        studentEnrollmentService.withdrawFromGroup(enrollmentId);
    }
}
