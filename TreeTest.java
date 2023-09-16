import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TreeTest {
    Tree tree1 = new Tree();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        File objects = new File("./objects");
        objects.mkdir();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @Test
    void testAdd() throws Exception {
        tree1.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        assertEquals("Blob is not added to list.", tree1.getListObject(0),
                "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");

        tree1.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        assertEquals("Tree is not added to list.", tree1.getListObject(1),
                "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");

        boolean error1 = false;
        try {
            tree1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file1.txt");
        } catch (Exception e) {
            error1 = true;
        }
        assertTrue("Added two blobs of the same file", error1);

        boolean error2 = false;
        try {
            tree1.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        } catch (Exception e) {
            error2 = true;
        }
        assertTrue("Added two trees", error2);
    }

    @Test
    void testGetFileContents() throws IOException {
        File file = new File("contents.txt");
        Utils.writeStringToFile("contents.txt", "testing");
        assertEquals("File is not read correctly", tree1.getFileContents(file), "testing");

    }

    @Test
    void testGetListObject() throws Exception {
        tree1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        assertEquals("get list object works", "blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt",
                tree1.getListObject(0));
    }

    @Test
    void testGetSHA() {
        assertEquals("get SHA is wrong", tree1.getSHA("testString"), "956265657d0b637ef65b9b59f9f858eecf55ed6a");
    }

    @Test
    void testRemove() throws Exception {
        tree1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        tree1.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree1.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");

        tree1.remove("file2.txt");
        assertEquals("Did not remove file", tree1.getListObject(1),
                "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");

        tree1.remove("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        assertEquals("Did not remove tree", tree1.getListObject(0),
                "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
    }

    @Test
    void testWriteToTree() throws Exception {
        tree1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        tree1.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree1.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        tree1.writeToTree();
        String contents = "blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt\ntree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b\nblob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt";
        File treeFile = new File("./objects/" + Utils.getSHA(contents));
        assertTrue("treefile not made", treeFile.exists());
        assertEquals("treefile not made correctly", contents, Utils.getFileContents(treeFile));
    }
}
