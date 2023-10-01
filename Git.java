import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPOutputStream;
import java.math.BigInteger;

public class Git {
    public static void main(String[] args) throws Exception {
        Git git = new Git();
        git.init();
        // git.add("contents.txt");
        git.add("test1.txt");
        // git.delete("contents.txt");
        // git.addDirectory("penis");
        // git.delete("penis");

    }

    public void init() throws IOException {
        File dir = new File("./objects");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File index = new File("index");
        if (!index.exists()) {
            index.createNewFile();
        }
    }

    public void add(String fileName) throws NoSuchAlgorithmException, IOException {
        Blob newBlob = new Blob(fileName);
        String sha = newBlob.getSHA();
        if (existsAlready(fileName, sha)) {
            return;
        }
        delete(fileName);

        FileWriter fileWriter = new FileWriter("index", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        File file = new File("index");
        if (file.length() != 0) {
            bufferedWriter.write('\n');
        }
        bufferedWriter.write("blob : " + sha + " : " + fileName);
        bufferedWriter.close();
        fileWriter.close();

    }

    public void addDirectory(String directory) throws Exception {

        Tree tree = new Tree();
        tree.addDirectory(directory);
        tree.writeToTree();
        String sha = tree.getSha();
        if (existsAlready(directory, sha)) {
            return;
        }

        FileWriter fileWriter = new FileWriter("index", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        File file = new File("index");
        if (file.length() != 0) {
            bufferedWriter.write('\n');
        }
        bufferedWriter.write("tree : " + sha + " : " + directory);
        bufferedWriter.close();
        fileWriter.close();
    }

    public void delete(String fileName) throws IOException {
        File ogFile = new File("index");
        File temp = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(ogFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String line;
        Boolean isFirst = true;
        while (br.ready()) {
            line = br.readLine();

            // splits at the space, your welcome
            String[] name = line.split("\\s+");

            if (!name[4].equals(fileName)) {
                if (!isFirst) {
                    bw.write('\n');

                }
                isFirst = false;
                bw.write(line);
                // if (br.ready()) {
                // bw.write('\n');
                // }
            } else {
                System.out.println("ran");
                File deleteFile = new File("./objects/" + name[2]);
                deleteFile.delete();
            }

        }
        br.close();
        bw.close();
        ogFile.delete();
        temp.renameTo(ogFile);

    }

    public boolean existsAlready(String fileName, String hash) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("index"));

        String line;
        while (br.ready()) {
            line = br.readLine();
            String[] name = line.split("\\s+");
            if (name[4].equals(fileName) && name[2].equals(hash)) {
                br.close();
                return true;
            }

        }
        br.close();
        return false;

    }
}
