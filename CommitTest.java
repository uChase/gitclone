import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class CommitTest {
    @AfterAll
    static void deleteFiles() {
        File index = new File("index");
        File objects = new File("objects");
        File holdFile = new File("treeHoldingFile");
        index.delete();
        holdFile.delete();
        for (File subfile : objects.listFiles()) {
            subfile.delete();
        }
        objects.delete();
        File HEAD = new File("HEAD");
        HEAD.delete();
    }

    @Test
    void testCommitAndWrite() throws Exception {
        Commit test = new Commit("wowza!!!!!!", "big shaq");
        test.commitAndWrite();

        // set up
        FileWriter commit = new FileWriter("objects/commit");
        BufferedWriter writeToCommit = new BufferedWriter(commit);

        writeToCommit.append("da39a3ee5e6b4b0d3255bfef95601890afd80709\n");
        writeToCommit.append("\n");
        writeToCommit.append("\n");
        writeToCommit.append("big shaq\n");
        writeToCommit.append(test.getDate() + "\n");
        writeToCommit.write("wowza!!!!!!");
        writeToCommit.close();

        String sha = Blob.generateSHA("objects/commit");

        File toTest = new File("objects/" + sha);

        assertTrue(toTest.exists());
    }

    @Test
    void testCreateTree() throws Exception {
        Commit test = new Commit("random filler", "doens't matter cuh");
        test.createTree();

        File toTest = new File("objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
        assertTrue(toTest.exists());
    }
}
