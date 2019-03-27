package LabAssiAsseProjectV02MV;

import LabAssiAsseProjectV02MV.domain.Tema;
import LabAssiAsseProjectV02MV.repository.NotaXMLRepo;
import LabAssiAsseProjectV02MV.repository.StudentXMLRepo;
import LabAssiAsseProjectV02MV.repository.TemaXMLRepo;
import LabAssiAsseProjectV02MV.service.Service;
import LabAssiAsseProjectV02MV.validation.NotaValidator;
import LabAssiAsseProjectV02MV.validation.StudentValidator;
import LabAssiAsseProjectV02MV.validation.TemaValidator;
import LabAssiAsseProjectV02MV.validation.ValidationException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.testng.Assert.assertNotNull;

public class AssignmentTest {

    private static Service service;
    private static TemaXMLRepo repo;

    @BeforeClass
    public static void setup() {
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        String filename = "fisiere/testTeme.xml";

        repo = new TemaXMLRepo(filename);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);

        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, repo);
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        System.out.println("Create service");
        service = new Service(studentXMLRepository, studentValidator, repo, temaValidator, notaXMLRepository, notaValidator);
    }

    @After
    public void resetRepo()
    {
        String originalFilename = "fisiere/Teme.xml";
        String filename = "fisiere/testTeme.xml";
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
        repo = new TemaXMLRepo(filename);
    }

    @Test(expected = ValidationException.class)
    public void testCase1(){
        Tema tema = new Tema("1","Laboratory 1",3,0);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void testCase2(){
        Tema tema = new Tema("1","Laboratory 1",15,12);
        service.addTema(tema);
    }

}
