import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void writeStringToFile(String filename, String contents) throws IOException {

        File file = new File(filename);

        FileWriter writer = new FileWriter(file);
        writer.write(contents);
        writer.close();

    }

    public static void deleteDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (directory.exists()) {

            File[] allContents = directory.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file.getPath());
                }
            }
            directory.delete();
        }
    }

}
