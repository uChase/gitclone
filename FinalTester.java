import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FinalTester {
        @BeforeAll
        static void setUpBeforeClass() throws Exception {
                Utils.writeStringToFile("test1.txt", "this is test 1");
                Utils.writeStringToFile("test2.txt", "this is test 2");
                Utils.writeStringToFile("test3.txt", "this is test 3");
                Utils.writeStringToFile("test4.txt", "this is test 4");
                Utils.writeStringToFile("test5.txt", "this is test 5");
                Utils.writeStringToFile("test6.txt", "this is test 6");
                Utils.writeStringToFile("test7.txt", "this is test 7");
                Utils.writeStringToFile("test8.txt", "this is test 8");
                Utils.writeStringToFile("test9.txt", "this is test 9");
                Utils.writeStringToFile("test10.txt", "this is test 10");
                Utils.makeDir("./penis");
                Utils.writeStringToFile("./penis/test", "test");
                Utils.writeStringToFile("./penis/test.txt", "this is penis test 1 part two");

        }

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
                Utils.deleteFile("HEAD");
                Utils.deleteFile("test1.txt");
                Utils.deleteFile("test2.txt");
                Utils.deleteFile("test3.txt");
                Utils.deleteFile("test4.txt");
                Utils.deleteFile("test5.txt");
                Utils.deleteFile("test6.txt");
                Utils.deleteFile("test7.txt");
                Utils.deleteFile("test8.txt");
                Utils.deleteFile("test9.txt");
                Utils.deleteFile("test10.txt");
                Utils.deleteFile("index");
                Utils.deleteFile("treeHoldingFile");
                Utils.deleteDirectory("penis");

        }

        // Test the behaviour of commits whilest doing deletes and edits
        // note: the traversal behaviour is tested in commit test
        @Test
        public void testFinal() throws Exception {
                Git git = new Git();
                git.init();
                git.add("test1.txt");
                git.add("test2.txt");
                Commit c1 = new Commit("test 1", "chase");
                c1.commitAndWrite();
                git.add("test3.txt");
                git.addDirectory("penis");
                git.add("test4.txt");
                Commit c2 = new Commit("test 2", "chase");
                c2.commitAndWrite();
                Utils.writeStringToFile("test3.txt", "this is test 3 part two");
                git.edited("test3.txt");
                git.add("test1.txt");
                Commit c3 = new Commit("test 3", "chase");
                c3.commitAndWrite();
                git.add("test5.txt");
                git.add("test6.txt");
                Commit c4 = new Commit("test 4", "chase");
                c4.commitAndWrite();
                git.delete("test4.txt");
                Utils.writeStringToFile("test1.txt", "this is test 1 part two");
                git.edited("test5.txt");
                Commit c5 = new Commit("test 5", "chase");
                c5.commitAndWrite();
                git.addDirectory("penis");
                git.add("test7.txt");
                Commit c6 = new Commit("test 6", "chase");
                c6.commitAndWrite();
                Utils.writeStringToFile("./penis/test.txt", "this is penis test 2 part two");
                git.editedDir("penis");
                Commit c7 = new Commit("test 7", "chase");
                c7.commitAndWrite();
                git.deleteDir("penis");
                Commit c8 = new Commit("test 8 ", "chase");
                c8.commitAndWrite();
                git.addDirectory("penis");
                Commit c9 = new Commit("this is just so i can show checkout dir", "k?");
                c9.commitAndWrite();
                System.out.println(Commit.getTreeSha1(c1.sha));
                System.out.println(Commit.getTreeSha1(c2.sha));
                System.out.println(Commit.getTreeSha1(c3.sha));
                System.out.println(Commit.getTreeSha1(c4.sha));
                System.out.println(Commit.getTreeSha1(c5.sha));
                System.out.println(Commit.getTreeSha1(c6.sha));
                System.out.println(Commit.getTreeSha1(c7.sha));
                System.out.println(Commit.getTreeSha1(c8.sha));
                System.out.println(Commit.getTreeSha1(c9.sha));

                assertEquals(
                                "blob : 5ff8d6a1ea48cbb8c3ea8bf7c0a901a4e7581569 : test1.txt\nblob : a11b4a39fdb42e418c5dc60730ba24ccfa388ec4 : test2.txt",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c1.getSHA()))));
                assertEquals(
                                "blob : a21c745148a6289b227a16502c7624191c00515c : test3.txt\n" + //
                                                "tree : 416eae58c94051e7d49b87729b16e4f9002f0323 : penis\n" + //
                                                "blob : c768b4b6de4e9cef407e59f59e2fadad2651db51 : test4.txt\n" + //
                                                "tree : 41ee207ea30eecb53d7a7acf3ca2ca463fd5d5a8",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c2.getSHA()))));
                assertEquals(
                                "blob : 4ac13a6d129d9b4e114a6a9c41d81f6622cc0cd8 : test3.txt\n" + //
                                                "tree : 416eae58c94051e7d49b87729b16e4f9002f0323 : penis\n" + //
                                                "blob : c768b4b6de4e9cef407e59f59e2fadad2651db51 : test4.txt\n" + //
                                                "tree* : 41ee207ea30eecb53d7a7acf3ca2ca463fd5d5a8\n" + //
                                                "tree : 50e1acb27bf4dfebee926743a9282606f8cd965c",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c3.getSHA()))));
                assertEquals(
                                "blob : 41c5c9da5c6381254e9d06093ef477abd9dd3bd9 : test5.txt\n" + //
                                                "blob : d0ba035cd24107506c41691d4887ae96df37b3b3 : test6.txt\n" + //
                                                "tree : a4d8a47d0659a9768bbd4658f4ceb36df979dbd6",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c4.getSHA()))));
                assertEquals(
                                "blob : 41c5c9da5c6381254e9d06093ef477abd9dd3bd9 : test5.txt\n" + //
                                                "blob : d0ba035cd24107506c41691d4887ae96df37b3b3 : test6.txt\n" + //
                                                "blob : 4ac13a6d129d9b4e114a6a9c41d81f6622cc0cd8 : test3.txt\n" + //
                                                "tree : 416eae58c94051e7d49b87729b16e4f9002f0323 : penis\n" + //
                                                "tree* : 41ee207ea30eecb53d7a7acf3ca2ca463fd5d5a8\n" + //
                                                "tree : 36dbcafabba17d97b1af31b769d9f9d209cb80b3",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c5.getSHA()))));
                assertEquals(
                                "blob : e2df23b548ad9f48eca0e51e4920cf63c89f61e : test7.txt\n" + //
                                                "tree : bc5ab9cec10ec807165116f1fd03bf29c9962f5d",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c6.getSHA()))));
                assertEquals(
                                "tree : 7d671b8114009d5f7b857c7ba3994aca0c86662d : penis\n" + //
                                                "blob : e2df23b548ad9f48eca0e51e4920cf63c89f61e : test7.txt\n" + //
                                                "blob : 41c5c9da5c6381254e9d06093ef477abd9dd3bd9 : test5.txt\n" + //
                                                "blob : d0ba035cd24107506c41691d4887ae96df37b3b3 : test6.txt\n" + //
                                                "blob : 4ac13a6d129d9b4e114a6a9c41d81f6622cc0cd8 : test3.txt\n" + //
                                                "tree* : 41ee207ea30eecb53d7a7acf3ca2ca463fd5d5a8\n" + //
                                                "tree : 98257b04ddab95557b5a4f144d8195d6cf03c919",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c7.getSHA()))));
                assertEquals(
                                "blob : e2df23b548ad9f48eca0e51e4920cf63c89f61e : test7.txt\n" + //
                                                "blob : 41c5c9da5c6381254e9d06093ef477abd9dd3bd9 : test5.txt\n" + //
                                                "blob : d0ba035cd24107506c41691d4887ae96df37b3b3 : test6.txt\n" + //
                                                "blob : 4ac13a6d129d9b4e114a6a9c41d81f6622cc0cd8 : test3.txt\n" + //
                                                "tree* : 41ee207ea30eecb53d7a7acf3ca2ca463fd5d5a8\n" + //
                                                "tree : 517dc8c57991107d2c861430cca80bd4a4841f80",
                                Utils.getFileContents(new File("./objects/" + Commit.getTreeSha1(c8.getSHA()))));
                // Utils.deleteFile("HEAD");

                Utils.deleteFile("test1.txt");
                Utils.deleteFile("test2.txt");
                Utils.deleteFile("test3.txt");
                Utils.deleteFile("test4.txt");
                Utils.deleteFile("test5.txt");
                Utils.deleteFile("test6.txt");
                Utils.deleteFile("test7.txt");
                Utils.deleteFile("test8.txt");
                Utils.deleteFile("test9.txt");
                Utils.deleteFile("test10.txt");
                Utils.deleteFile("index");
                Utils.deleteFile("treeHoldingFile");
                Utils.deleteDirectory("penis");

                git.checkout(Utils.getFileContents(new File("HEAD")));
                File t1 = new File("test1.txt");
                File t2 = new File("test2.txt");
                File t3 = new File("test3.txt");
                File t4 = new File("test5.txt");
                File t5 = new File("test6.txt");
                File t6 = new File("test7.txt");
                File penis = new File("penis");
                assertTrue("file does not exit", t1.exists());
                assertTrue("file does not exit", t3.exists());
                assertTrue("file does not exit", t4.exists());
                assertTrue("file does not exit", t5.exists());
                assertTrue("file does not exit", t6.exists());
                assertTrue("file does not exit", penis.exists());
                assertTrue("file does not exit", t2.exists());
        }
}
