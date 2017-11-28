import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public static void saveFromString(String path, String fileName, String fileString) throws IOException {
        System.out.println("Saving file: " + path + fileName);
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, fileName);
        FileWriter newFile = new FileWriter(file);
        newFile.write(fileString);
        newFile.flush();
    }
}