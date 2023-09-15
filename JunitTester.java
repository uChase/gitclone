import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JunitTester {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        Utils.writeStringToFile("test1.txt", "this is test 1");
        Utils.writeStringToFile("test2.txt", "this is test 2");
        Utils.writeStringToFile("test3.txt", "this is test 3");
        Utils.writeStringToFile("test4.txt", "this is test 4");
        Utils.writeStringToFile("test5.txt", "this is test 5");
        Utils.deleteFile("./objects/index");
        Utils.deleteDirectory("./objects");

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {

        Utils.deleteFile("test1.txt");
        Utils.deleteFile("test2.txt");
        Utils.deleteFile("test3.txt");
        Utils.deleteFile("test4.txt");
        Utils.deleteFile("test5.txt");
        Utils.deleteFile("index");
        Utils.deleteDirectory("objects");

    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testInitialize() throws Exception {

        // Run the person's code
        // TestHelper.runTestSuiteMethods("testInitialize");

        // check if the file exists
        /// File file = new File("index");
        // Path path = Paths.get("objects");

        // assertTrue(file.exists());
        // assertTrue(Files.exists(path));
    }

    @Test
    @DisplayName("Test if blob exists and if blob has the correct contents.")
    void testCreateBlob() throws Exception {

        // Manually create the files and folders before the 'testAddFile'
        Git git = new Git();
        git.init();

        // TestHelper.runTestSuiteMethods("testCreateBlob", file1.getName());
        Blob blob1 = new Blob("test1.txt");

        // Check blob exists in the objects folder
        File file1 = new File("objects/" + blob1.getSHA());
        assertTrue("Blob file to add not found", file1.exists());

        // Read file contents
        assertEquals("File contents of Blob don't match file contents pre-blob creation",
                Blob.readFile("./objects/" + blob1.getSHA()),
                Blob.readFile("test1.txt"));
    }
}
