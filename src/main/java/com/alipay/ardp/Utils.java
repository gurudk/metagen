/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Utils
 * 
 * @author ji.zhangyj
 * @version $Id: FileUtil.java, v 0.1 2013-4-25 下午3:40:32 ji.zhangyj Exp $
 */
public class Utils {
    public static void deleteFile(File file) {
        if (file.exists()) { //判断文件是否存在
            if (file.isFile()) { //判断是否是文件
                file.delete(); //delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { //否则如果它是一个目录
                File files[] = file.listFiles(); //声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { //遍历目录下所有的文件
                    deleteFile(files[i]); //把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } 
    }

    public static void unzipJar(File src, File desDir) throws FileNotFoundException,
                                                            IOException {
        JarFile jarFile = new JarFile(src);
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        if (!desDir.exists())
            desDir.mkdirs(); //建立用户指定存放的目录
        byte[] bytes = new byte[1024];

        while (jarEntrys.hasMoreElements()) {
            ZipEntry entryTemp = jarEntrys.nextElement();
            File desTemp = new File(desDir.getAbsoluteFile() + File.separator + entryTemp.getName());

            if (entryTemp.isDirectory()) { //jar条目是空目录
                if (!desTemp.exists())
                    desTemp.mkdirs();
            } else { //jar条目是文件
                //因为manifest的Entry是"META-INF/MANIFEST.MF",写出会报"FileNotFoundException"
                File desTempParent = desTemp.getParentFile();
                if (!desTempParent.exists())
                    desTempParent.mkdirs();

                BufferedInputStream in = new BufferedInputStream(jarFile.getInputStream(entryTemp));
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desTemp));

                int len = in.read(bytes, 0, bytes.length);
                while (len != -1) {
                    out.write(bytes, 0, len);
                    len = in.read(bytes, 0, bytes.length);
                }

                in.close();
                out.flush();
                out.close();
            }
        }
        jarFile.close();
    }
    
    public static Set<String> readAll(String fileName) throws IOException{
        BufferedReader  reader = new BufferedReader(new FileReader(fileName));
        Set<String> s = new HashSet<String>();
        String line;
        while((line = reader.readLine()) != null){
            if(!"".equals(line)){
                s.add(line);
            }
        }
        return s;
    }

    public static String firstComment(String commentText) {
        if(commentText == null || "".equals(commentText)){
            return commentText;
        }
        int idx = commentText.indexOf("\n");
        int idx1 = commentText.indexOf("<");
        
        if(idx != -1 && idx1 != -1){
            return commentText.substring(0, idx<idx1?idx:idx1);
        }else if(idx == -1 && idx1==-1){
            return commentText;
        }else{
            return commentText.substring(0, idx<idx1?idx1:idx);
        }
    }
    
    public static void main(String[] arg) throws IOException {
        Set<String> set = readAll("e:/atomic_blacklist.csv");
        for(String s:set){
            System.out.println(s);
        }
    }
}
