import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPOutputStream;
import java.math.BigInteger;

public class Git {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Git git = new Git();
        try {
            git.init();
            git.delete("test.txt");
            git.add("test.txt");
            // git.add("test2.txt");
            // git.delete("test.txt");
            // git.delete("test2.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        if (existsAlready(fileName)) {
            return;
        }
        Blob newBlob = new Blob(fileName);
        String sha = newBlob.getSHA();
        FileWriter fileWriter = new FileWriter("index", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(fileName + " : " + sha + '\n');
        bufferedWriter.close();
        fileWriter.close();

    }

    public void delete(String fileName) throws IOException {
        File ogFile = new File("index");
        File temp = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(ogFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String line;
        while (br.ready()) {
            line = br.readLine();

            // splits at the space, your welcome
            String[] name = line.split("\\s+");

            if (!name[0].equals(fileName)) {
                bw.write(line);
                bw.write('\n');
            } else {
                File deleteFile = new File("./objects/" + name[2]);
                deleteFile.delete();
            }

        }

        ogFile.delete();
        temp.renameTo(ogFile);
        br.close();
        bw.close();

    }

    public boolean existsAlready(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("index"));

        String line;
        while (br.ready()) {
            line = br.readLine();
            String[] name = line.split("\\s+");
            if (name[0].equals(fileName)) {
                br.close();
                return true;
            }

        }
        br.close();
        return false;

    }
}
