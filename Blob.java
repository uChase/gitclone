import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.math.BigInteger;

public class Blob {

    public static void main(String[] args) throws IOException {
        try {
            Blob b = new Blob("test.txt");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String sha;

    public Blob(String file) throws NoSuchAlgorithmException, IOException {
        sha = generateSHA(file);

    }

    public String getSHA() {
        return sha;
    }

    public static String generateSHA(String fileName) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        String strContent = readFile(fileName);
        byte[] content = compressToBinary(strContent);
        byte[] messageDigest = digest.digest(content);
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        File dir = new File("./objects");
        if (!dir.exists()) {
            dir.mkdir();
        }
        writeToFile(strContent, "./objects/" + hashtext);
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

    public static byte[] compressToBinary(String text) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOS = new GZIPOutputStream(baos)) {
            gzipOS.write(text.getBytes("UTF-8"));
        }
        return baos.toByteArray();
    }

    public static void writeToFile(String text, String fileName) {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}