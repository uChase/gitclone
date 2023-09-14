import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

public class tree {
    public static void main(String[] args) throws IOException {
        tree treeTest = new tree();
        treeTest.add("test.txt");
        treeTest.writeToTree();
    }

    File treeFile;
    ArrayList<String> list;

    public tree() {
        treeFile = new File("./objects/tree");
        list = new ArrayList<String>();
    }

    public void add(String treeEntry) {
        list.add(treeEntry);
    }

    public void remove(String treeEntry) {
        boolean removed = false;
        for (int i = 0; i < list.size() && removed == false; i++) {
            if (list.get(i).contains(treeEntry)) {
                list.remove(i);
                removed = true;
            }
        }
    }

    public void writeToTree() throws IOException {
        FileWriter writer = new FileWriter(treeFile);
        for (int i = 0; i < list.size() - 1; i++) {
            writer.write(list.get(i) + "\n");
        }
        writer.write(list.get(list.size() - 1));
        writer.close();
        File rename = new File("./objects/" + getSHA(getFileContents(treeFile)));
        treeFile.renameTo(rename);

    }

    public String getFileContents(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
        StringBuilder contents = new StringBuilder();
        while (reader.ready()) {
            contents.append((char) reader.read());
        }
        reader.close();
        return contents.toString();
    }

    private String getSHA(String fileContents) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(fileContents.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    // Used for sha1
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
