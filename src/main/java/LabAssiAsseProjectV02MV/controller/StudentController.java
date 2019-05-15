package LabAssiAsseProjectV02MV.controller;

import java.util.concurrent.atomic.AtomicLong;

import LabAssiAsseProjectV02MV.domain.Student;
import LabAssiAsseProjectV02MV.repository.NotaXMLRepo;
import LabAssiAsseProjectV02MV.repository.StudentXMLRepo;
import LabAssiAsseProjectV02MV.repository.TemaXMLRepo;
import LabAssiAsseProjectV02MV.service.Service;
import LabAssiAsseProjectV02MV.validation.NotaValidator;
import LabAssiAsseProjectV02MV.validation.StudentValidator;
import LabAssiAsseProjectV02MV.validation.TemaValidator;
import org.springframework.web.bind.annotation.*;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

@CrossOrigin
@RestController
public class StudentController {

    private final Service service;
    private final AtomicLong counter = new AtomicLong();

    StudentController() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        this.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @RequestMapping(method=RequestMethod.GET, value="/students")
    public Iterable<Student> getAllStudents() {
        return service.getAllStudenti();
    }

    @RequestMapping(method=RequestMethod.POST, value="/student")
    public Student createNewStudent(@RequestBody Student student){
        service.addStudent(student);
        return student;
    }
}