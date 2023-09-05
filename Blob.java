import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class Blob {

    public static void main(String[] args) {
        try {
            Blob b = new Blob("test.txt");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Blob(String file) throws NoSuchAlgorithmException {
        String sha = generateSHA(file);

    }

    public static String generateSHA(String fileName) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        String content = readFile(fileName);
        byte[] messageDigest = digest.digest(content.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        File dir = new File("./objects");
        if (!dir.exists()) {
            dir.mkdir();
        }
        writeToFile(content, "./objects/" + hashtext);
        return hashtext;

    }

    public static String readFile(String file) {
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String c;
            String sub = "";
            try {
                while (br.ready()) {
                    c = br.readLine();
                    System.out.println(c);
                    sub += c;
                    if (br.ready()) {
                        sub += "\n";
                    }
                }
                br.close();
                fr.close();
                return sub;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToFile(String text, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter b = new BufferedWriter(fileWriter);
            b.write(text);
            b.close();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}