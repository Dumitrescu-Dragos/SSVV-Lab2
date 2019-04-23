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
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.testng.Assert.assertNotNull;

public class TopDownTest {
    private static Service service;
    private static StudentXMLRepo studentRepo;
    private static TemaXMLRepo temaRepo;
    private static NotaXMLRepo notaRepo;

    private static List<String> addedStudents;
    private static List<String> addedTeme;
    private static List<String> addedNote;

    @BeforeClass
    public static void setup() {

        addedStudents = new ArrayList<String>();
        addedTeme = new ArrayList<String>();
        addedNote = new ArrayList<String>();

        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        String filename1 = "fisiere/testTeme.xml";
        String filename2 = "fisiere/testStudenti.xml";
        String filename3 = "fisiere/testNote.xml";

        temaRepo = new TemaXMLRepo(filename1);
        notaRepo = new NotaXMLRepo(filename3);
        studentRepo = new StudentXMLRepo(filename2);

        NotaValidator notaValidator = new NotaValidator(studentRepo, temaRepo);
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        System.out.println("Create service");
        service = new Service(studentRepo, studentValidator, temaRepo, temaValidator, notaRepo, notaValidator);
    }

    @Test
    public void testCase1(){
        String id = "1111";
        Assert.assertNull(service.findStudent(id));
        Student student = new Student(id,"Alexandra",934,"alexandra.mail.com");
        service.addStudent(student);
        Assert.assertNotNull(service.findStudent(id));
        addedStudents.add(id);
    }

    @Test
    public void testCase2(){
        testCase1();
        String id = "151";
        assertNull(service.findTema(id));
        Tema tema = new Tema(id, "Laboratory 5", 5,4);
        service.addTema(tema);
        assertNotNull(service.findTema(id));
        addedTeme.add(id);
    }

    @Test
    public void testCase3(){
        testCase2();
        String id = "100";
        Assert.assertNull(service.findNota(id));
        Nota nota = new Nota( id, "1111", "151", 10 , LocalDate.of(2018,10,14));
        service.addNota(nota, "Bad");
        Assert.assertNotNull(service.findNota(id));
        addedNote.add(id);
    }

    @After
    public void resetRepo()
    {
        addedStudents.forEach(id -> service.deleteStudent(id));
        addedTeme.forEach(id -> service.deleteTema(id));
        addedNote.forEach(id -> service.deleteNota(id));
    }
}
