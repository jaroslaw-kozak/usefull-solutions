import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Downloader {

    public static File get(String from, String destination) throws IOException {
        URL url = new URL(from);
        BufferedImage img = ImageIO.read(url);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destination);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();

        return new File(destination);
    }
}