package org.codechimp.androidutils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Environment;

public class FileUtils {
    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     *
     * @param fromFile - FileInputStream for the file to copy from.
     * @param toFile   - FileInputStream for the file to copy to.
     */
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }


    /**
     * Check if SD card is present
     *
     * @return true if an SD card is present
     */
    public static boolean isSdPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private static final int BUFFER_SIZE = 8192;

    /**
     * Open a file and return its contents in a String
     *
     * @param file - File to open
     * @return File contents
     * @throws IOException
     */
    public static String readTextFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"), BUFFER_SIZE);
        try {
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } finally {
            br.close();
        }
    }

    /**
     * Open a file and return its contents as a byte array
     *
     * @param file - File to open
     * @return File contents
     * @throws IOException
     */
    public static byte[] readBinaryFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while ((len = fis.read(buffer)) > 0) {
                stream.write(buffer, 0, len);
            }

            byte[] output = stream.toByteArray();
            stream.close();
            return output;
        } finally {
            fis.close();
        }
    }

    /**
     * Generate the SHA1 for a file
     *
     * @param file - file to generate SHA1 for
     * @return SHA1 for passed file
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public String getSha1ForFile(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(file);
        String out = null;
        try {
            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;

            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            out = sb.toString();
        } finally {
            fis.close();
        }
        return out;
    }
}