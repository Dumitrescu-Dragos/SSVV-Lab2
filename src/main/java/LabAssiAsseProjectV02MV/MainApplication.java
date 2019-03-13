package LabAssiAsseProjectV02MV;


import LabAssiAsseProjectV02MV.domain.Nota;
import LabAssiAsseProjectV02MV.domain.Student;
import LabAssiAsseProjectV02MV.domain.Tema;
import LabAssiAsseProjectV02MV.repository.NotaXMLRepo;
import LabAssiAsseProjectV02MV.repository.StudentXMLRepo;
import LabAssiAsseProjectV02MV.repository.TemaXMLRepo;
import LabAssiAsseProjectV02MV.service.Service;
import LabAssiAsseProjectV02MV.validation.NotaValidator;
import LabAssiAsseProjectV02MV.validation.StudentValidator;
import LabAssiAsseProjectV02MV.validation.TemaValidator;
import LabAssiAsseProjectV02MV.view.UI;

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
