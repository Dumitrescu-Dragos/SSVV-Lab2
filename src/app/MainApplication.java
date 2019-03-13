package app;


import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaFileRepository;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import repository.NotaXMLRepo;
import repository.TemaFileRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import view.UI;

import java.time.LocalDate;


public class MainApplication {

    public static void main(String[] args) {
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

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        //Add initial data
        service.addStudent(new Student("137","Dumitrescu Dragos-Lucian", 934, "dragos@dragos.dragos"));
        service.addTema(new Tema("13","Algebra Problems",3, 1));
        service.addNota(new Nota("137#13","137","13",9.0, LocalDate.of(2018,10,8)),"Good");
        UI ui = new UI(service);
        ui.run();
    }

}
