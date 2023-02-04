package com.bonnieapps.springboot2app.student;

import com.bonnieapps.springboot2app.exceptions.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return  studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.
                findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        studentRepository.save(student);
    }


    public void deleteStudentByID(Long studentId) {
         boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new ApiRequestException("student with id "+studentId+" doesn't exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional //so that we don't use repository queries
    public void updateStudentByID(Long studentId, String name, String email) {
            Student student = studentRepository.findById(studentId).
                    orElseThrow(
                            () -> new IllegalStateException("" +
                            "student with id "+studentId+" doesn't exists")
                    );
        if (name != null && name.length()>0 &&
                student.getName().equals(name)
        ) {
            student.setName(name);
        }

        if (email != null && email.length()>0 &&
                student.getEmail().equals(email)
        ) {
            Optional<Student> studentByEmail = studentRepository.
                    findStudentByEmail(student.getEmail());
            if(studentByEmail.isPresent()){
                throw new IllegalStateException("Email Taken");
            }
            student.setEmail(email);
        }
    }
}
