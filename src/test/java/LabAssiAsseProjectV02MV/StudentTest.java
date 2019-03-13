package LabAssiAsseProjectV02MV;


import LabAssiAsseProjectV02MV.domain.Student;
import LabAssiAsseProjectV02MV.repository.StudentFileRepository;
import org.junit.Test;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StudentTest {

    @Test
    public void addNewStudentTest(){
        String filename = "fisiere/testStudenti.txt";
        StudentFileRepository repo = new StudentFileRepository(filename);
        repo.loadFromFile();

        assertNull(repo.findOne("90"));
        Student student = new Student("90","Dragos Dumitrescu",934,"dargso@dragos.com");
        repo.save(student);
        assertNotNull(repo.findOne("90"));

        //keep the initial state of the test
        repo.delete("90");
        repo.writeToFile();
    }

    @Test
    public void addExistingStudentTest(){
        String filename = "fisiere/testStudenti.txt";
        StudentFileRepository repo = new StudentFileRepository(filename);
        repo.loadFromFile();

        assertNotNull(repo.findOne("3"));
        String name = repo.findOne("3").getNume();
        Student student = new Student("3","Dragos Dumitrescu",934,"dargso@dragos.com");
        repo.save(student);
        assertSame(repo.findOne("3").getNume(), name);

        //keep the initial state of the test
        repo.delete("90");
        repo.writeToFile();
    }
}
