package com.example.ben.rainy_night.util;

import java.io.File;

/**
 * 封装关于文件相关的操作
 *
 * @author Ben
 * @date 2018/5/16
 */

public class FileUtil {

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return
     */
    public long getFileSize(String path) {
        long size = 0;
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        size = size + getDirectorySize(f);
                    } else if (f.isFile()) {
                        size = size + f.length();
                    }
                }
            } else {
                size = file.length();
            }
        }
        return size;
    }

    /**
     * 删除目录下的文件
     *
     * @param path 文件路径
     */
    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] list = file.listFiles();
                for (File f : list) {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                    } else if (f.isFile()) {
                        f.delete();
                    }
                }
            } else {
                file.delete();
            }
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     * @return
     */
    private long getDirectorySize(File file) {
        long size = 0;
        File[] mFile = file.listFiles();
        for (File f : mFile) {
            if (f.isDirectory()) {
                size = size + getDirectorySize(f);
            } else if (f.isFile()) {
                size = size + f.length();
            }
        }
        return size;
    }

    /**
     * 删除目录文件
     *
     * @param file
     */
    private void deleteDirectory(File file) {
        File[] list = file.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else if (f.isFile()) {
                f.delete();
            }
        }
    }
}
