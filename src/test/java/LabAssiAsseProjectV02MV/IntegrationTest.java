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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

import static junit.framework.TestCase.assertNull;
import static org.testng.Assert.assertNotNull;

public class IntegrationTest {

    private static Service service;
    private static StudentXMLRepo studentRepo;
    private static TemaXMLRepo temaRepo;
    private static NotaXMLRepo notaRepo;

    @BeforeClass
    public static void setup() {
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
    public void testcase1(){
        Assert.assertNull(service.findStudent("111"));
        Student student = new Student("111","Alexandra",934,"alexandra.mail.com");
        service.addStudent(student);
        Assert.assertNotNull(service.findStudent("111"));
        service.deleteStudent("111");
    }

    @Test
    public void testcase2(){
        assertNull(service.findTema("15"));
        Tema tema = new Tema("15", "Laboratory 5", 5,4);
        service.addTema(tema);
        assertNotNull(service.findTema("15"));
        service.deleteTema("15");
    }

    @Test
    public void testcase3(){
        Assert.assertNull(service.findNota("100"));
        Nota nota = new Nota( "100", "1", "2", 10 , LocalDate.of(2018,10,14));
        service.addNota(nota, "Bad");
        Assert.assertNotNull(service.findNota("100"));
        service.deleteNota("100");
    }


    @Test
    public void testcase4(){
        testcase1();
        testcase2();
        testcase3();
    }

    @After
    public void resetRepo()
    {
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        String filename1 = "fisiere/testTeme.xml";
        String filename2 = "fisiere/testStudenti.xml";
        String filename3 = "fisiere/testNote.xml";
        try{
            Scanner scanner1 = new Scanner(Paths.get(filenameTema));
            Scanner scanner2 = new Scanner(Paths.get(filenameStudent));
            Scanner scanner3 = new Scanner(Paths.get(filenameNota));
            FileWriter fileWriter1 = new FileWriter(filename1, false);
            FileWriter fileWriter2 = new FileWriter(filename2, false);
            FileWriter fileWriter3 = new FileWriter(filename3, false);
            String content1 = scanner1.useDelimiter("\\A").next();
            String content2 = scanner2.useDelimiter("\\A").next();
            String content3 = scanner3.useDelimiter("\\A").next();
            fileWriter1.write(content1);
            fileWriter2.write(content2);
            fileWriter3.write(content3);
            scanner1.close();
            scanner2.close();
            scanner3.close();
            fileWriter1.close();
            fileWriter2.close();
            fileWriter3.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        temaRepo.loadFromFile();
        notaRepo.loadFromFile();
        studentRepo.loadFromFile();
    }
}
