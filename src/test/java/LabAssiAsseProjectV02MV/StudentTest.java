package LabAssiAsseProjectV02MV;


import LabAssiAsseProjectV02MV.domain.Student;
import LabAssiAsseProjectV02MV.repository.NotaXMLRepo;
import LabAssiAsseProjectV02MV.repository.StudentFileRepository;
import LabAssiAsseProjectV02MV.repository.StudentXMLRepo;
import LabAssiAsseProjectV02MV.repository.TemaXMLRepo;
import LabAssiAsseProjectV02MV.service.Service;
import LabAssiAsseProjectV02MV.validation.NotaValidator;
import LabAssiAsseProjectV02MV.validation.StudentValidator;
import LabAssiAsseProjectV02MV.validation.TemaValidator;
import LabAssiAsseProjectV02MV.validation.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StudentTest {

    private static Service service;
    private static StudentXMLRepo repo;

    @BeforeClass
    public static void setup() {
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        String filename = "fisiere/testStudenti.xml";

        repo = new StudentXMLRepo(filename);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        NotaValidator notaValidator = new NotaValidator(repo, temaXMLRepository);
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        System.out.println("Create service");
        service = new Service(repo, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Before
    public void resetRepo()
    {
        String originalFilename = "fisiere/Studenti.xml";
        String filename = "fisiere/testStudenti.xml";
        try{
            Scanner scanner = new Scanner(Paths.get(originalFilename));
            FileWriter fileWriter = new FileWriter(filename, false);
            String content = scanner.useDelimiter("\\A").next();
            fileWriter.write(content);
            scanner.close();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        repo = new StudentXMLRepo(filename);
    }

    @Test(expected = ValidationException.class)
    public void testCase1(){
        Student student1 = new Student("2","Maior Alexndra",-1,"alexandra@gmail.com");
        service.addStudent(student1);
        assertNotNull(service.findStudent("2"));
        String name = service.findStudent("2").getNume();
        Student student2 = new Student("2","Dragos Dumitrescu",934,"dargso@dragos.com");
        assertSame(service.findStudent("2").getNume(), name);
    }

    @Test(expected = ValidationException.class)
    public void testCase2(){
        Student student = new Student("106","Dragos",-1,"email");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testCase3(){
        Student student = new Student("107","Alexandra",Integer.MAX_VALUE + 1,"email");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testCase4(){
        Student student = new Student("","Alex",933,"email");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testCase5(){
        Student student = new Student("108","",933,"email");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void testCase6(){
        Student student = new Student("109","Dragos",935,"");
        service.addStudent(student);
    }

    @Test
    public void testCase7(){
        assertNull(service.findStudent("110"));
        Student student = new Student("110","Alexandra",934,"alexandra.mail.com");
        service.addStudent(student);
        assertNotNull(service.findStudent("110"));
    }


    @Test
    public void testCase8(){
        Student student = new Student("200","Alex",0,"alex.com");
        service.addStudent(student);
        assertNotNull(service.findStudent("200"));
    }

    @Test
    public void testCase9(){
        Student student = new Student("202","Alex",1,"alex.com");
        service.addStudent(student);
        assertNotNull(service.findStudent("202"));
    }

    @Test
    public void testCase10(){
        Student student = new Student("203","Dragos",Integer.MAX_VALUE,"dragos.com");
        service.addStudent(student);
        assertNotNull(service.findStudent("203"));
    }

    @Test
    public void testCase11(){
        Student student = new Student("204","Alex",Integer.MAX_VALUE - 1,"alex.com");
        service.addStudent(student);
        assertNotNull(service.findStudent("204"));
    }
}
