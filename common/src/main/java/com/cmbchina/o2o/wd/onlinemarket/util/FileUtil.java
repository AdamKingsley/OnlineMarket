package com.cmbchina.o2o.wd.onlinemarket.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author
 * @create 2017-01-20-15:13
 */

public class FileUtil {
    public static final String KEY_MD5 = "MD5";
    public static final String CHARSET_ISO88591 = "ISO-8859-1";

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename.toLowerCase();
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename.toLowerCase();
    }


    /**
     * Get MD5 of one file:hex string,test OK!
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /***
     * Get MD5 of one file！test ok!
     *
     * @param filepath
     * @return
     */
    public static String getFileMD5(String filepath) {
        File file = new File(filepath);
        return getFileMD5(file);
    }

    /**
     * MD5 encrypt,test ok
     *
     * @param data
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    public static byte[] encryptMD5(String data) throws Exception {
        return encryptMD5(data.getBytes(CHARSET_ISO88591));
    }

    /***
     * compare two file by Md5
     *
     * @param file1
     * @param file2
     * @return
     */
    public static boolean isSameMd5(File file1, File file2) {
        String md5_1 = FileUtil.getFileMD5(file1);
        String md5_2 = FileUtil.getFileMD5(file2);
        return md5_1.equals(md5_2);
    }

    /***
     * compare two file by Md5
     *
     * @param filepath1
     * @param filepath2
     * @return
     */
    public static boolean isSameMd5(String filepath1, String filepath2) {
        File file1 = new File(filepath1);
        File file2 = new File(filepath2);
        return isSameMd5(file1, file2);
    }

    public static boolean close(InputStream stream) throws IOException {
        if (stream != null) {
            stream.close();
        }
        return true;
    }

    public static boolean close(OutputStream stream) throws IOException {
        if (stream != null) {
            stream.close();
        }
        return true;
    }

    public static boolean delete(String filepath, String exclusion) {
        if (!StringUtils.isEmpty(filepath)) {
            delete(new File(filepath), exclusion);
        }
        return true;
    }

    public static boolean delete(String filepath) {
        if (!StringUtils.isEmpty(filepath)) {
            delete(new File(filepath));
        }
        return true;
    }

    public static void delete(File file, String exclusion) {
        System.gc();
        if (file.isDirectory()) {
            //取得这个目录下的所有子文件对象
            File[] files = file.listFiles();
            //遍历该目录下的文件对象
            for (File f : files) {
                if (f.getName().toLowerCase().equals(exclusion.toLowerCase())) {
                    //不删除排除的文件
                    continue;
                }
                //判断子目录是否存在子目录,如果是文件则删除
                System.out.println(f.getAbsolutePath());
                if (f.isDirectory()) {
                    delete(f);
                } else {
                    System.out.println("delete  " + f.getAbsolutePath());
                    f.delete();
                }
            }
        }
        file.delete();
    }


    public static void delete(File file) {
        System.gc();
        if (file.isDirectory()) {
            //取得这个目录下的所有子文件对象
            File[] files = file.listFiles();
            //遍历该目录下的文件对象
            for (File f : files) {
                //判断子目录是否存在子目录,如果是文件则删除
                System.out.println(f.getAbsolutePath());
                if (f.isDirectory()) {
                    delete(f);
                } else {
                    System.out.println("delete  " + f.getAbsolutePath());
                    f.delete();
                }
            }
        }
        file.delete();
    }

    public static String join(String path1, String path2) {
        while (path1.endsWith("\\") || path1.endsWith("/")) {
            path1 = path1.substring(0, path1.length() - 1);
        }
        while (path2.startsWith("\\") || path2.startsWith("/")) {
            path2 = path2.substring(1);
        }
        return path1 + File.separator + path2;
    }


    public static Long getFileSize(String filepath) {
        long size = 0L;
        if (!StringUtil.isEmpty(filepath)) {
            File file = new File(filepath);
            if (file.exists() && file.isFile()) {
                size = file.length();
            }
        }
        return size;
    }

    public static String getFileContent(String filepath) {
        String result = null;
        File file = new File(filepath);
        if (file.exists() && file.isFile()) {
            try {
                result = FileUtils.readFileToString(file, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(FileUtil.join("D:/file/", "/name"));
    }

}
