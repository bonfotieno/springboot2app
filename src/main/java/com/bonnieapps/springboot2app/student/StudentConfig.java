package com.bonnieapps.springboot2app.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration /*
            annotation which indicates that the class has @Bean definition methods.
            So Spring container can process the class and generate Spring Beans to be used in the application.
                */
public class StudentConfig {

    private final StudentRepository studentRepository;

    public StudentConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {

            Student bonnie =  new Student(
                        "Bonnie",
                        "bon@kaili.co",
                        LocalDate.of(2000, Month.DECEMBER, 3)
                );

            Student alex =  new Student(
                    "Alex",
                    "alex@kaili.co",
                    LocalDate.of(2000, Month.MAY, 6)
            );
            studentRepository.saveAll(List.of(bonnie,alex));

        };
    }
}
