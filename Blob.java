import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPOutputStream;
import java.math.BigInteger;

public class Blob {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Blob b = new Blob("test.txt");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String sha;

    public Blob(String file) throws NoSuchAlgorithmException, FileNotFoundException {
        sha = generateSHA(file);

    }

    public String getSHA() {
        return sha;
    }

    public static String generateSHA(String fileName) throws NoSuchAlgorithmException, FileNotFoundException {
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

    public static String readFile(String file) throws FileNotFoundException {
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
            throw e;
        }
    }

    public static void writeToFile(String text, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] bytes = text.getBytes();
            gzipOS.write(bytes, 0, bytes.length);
            gzipOS.close();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}