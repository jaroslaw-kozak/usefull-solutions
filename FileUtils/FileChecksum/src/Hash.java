import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Hash {

    /**
     * Calculates a checksum of provided file.
     * 
     * @param input File
     * @param name algorithm name: MD5, SHA1, SHA-256, SHA-512
     * @return
     */
    public static String get(File input, String name) {
        return toHex(checksum(input, name));
    }

    private static byte[] checksum(File input, String hashName) {
        try (InputStream in = new FileInputStream(input)) {
            MessageDigest digest = MessageDigest.getInstance(hashName);
            byte[] block = new byte[4096];
            int length;
            while ((length = in.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            return digest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}