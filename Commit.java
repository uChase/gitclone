import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

public class Commit {
    String parentCommit = "";
    String nextCommit = "";
    String treeSha;
    String summary;
    String author;
    Date date;
    String sha;

    public static void main(String[] args) throws Exception {
        Commit test = new Commit("this is a test commit", "William Abraham");
        test.commitAndWrite();

    }

    public Commit(String parentCommit, String summary, String author) throws Exception {
        this.parentCommit = parentCommit;
        this.summary = summary;
        this.author = author;
        treeSha = createTree();
        this.date = new Date(java.lang.System.currentTimeMillis());
    }

    // optional constructor without parentCommit parameter
    public Commit(String summary, String author) throws Exception {
        this.summary = summary;
        this.author = author;
        treeSha = createTree();
        this.date = new Date(java.lang.System.currentTimeMillis());
    }

    public void commitAndWrite() throws NoSuchAlgorithmException {
        try {
            FileWriter commit = new FileWriter("objects/commit");
            BufferedWriter writeToCommit = new BufferedWriter(commit);

            writeToCommit.append(treeSha + "\n");
            writeToCommit.append(parentCommit + "\n");
            writeToCommit.append(nextCommit + "\n");
            writeToCommit.append(author + "\n");
            writeToCommit.append(getDate() + "\n");
            writeToCommit.write(summary);
            writeToCommit.close();

            // creates blob of commit contents
            Blob hashed = new Blob("objects/commit");
            sha = hashed.getSHA();

            File toRemove = new File("objects/commit");
            toRemove.delete();

            FileWriter headFile = new FileWriter("HEAD");
            BufferedWriter writer = new BufferedWriter(headFile);
            writer.write(sha);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSHA() {
        return sha;
    }

    // local String gets local region time format
    public String getDate() {
        return date.toLocaleString();
    }

    // creates an empty tree - will probably change later
    public String createTree() throws Exception {
        Git toInit = new Git();
        toInit.init();

        Tree init = new Tree();
        init.add("");
        init.writeToTree();
        return init.getSha();
    }
}
