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
 * @version $Id: FileUtil.java, v 0.1 2013-4-25 ����3:40:32 ji.zhangyj Exp $
 */
public class Utils {
    public static void deleteFile(File file) {
        if (file.exists()) { //�ж��ļ��Ƿ����
            if (file.isFile()) { //�ж��Ƿ����ļ�
                file.delete(); //delete()���� ��Ӧ��֪�� ��ɾ������˼;
            } else if (file.isDirectory()) { //�����������һ��Ŀ¼
                File files[] = file.listFiles(); //����Ŀ¼�����е��ļ� files[];
                for (int i = 0; i < files.length; i++) { //����Ŀ¼�����е��ļ�
                    deleteFile(files[i]); //��ÿ���ļ� ������������е���
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
            desDir.mkdirs(); //�����û�ָ����ŵ�Ŀ¼
        byte[] bytes = new byte[1024];

        while (jarEntrys.hasMoreElements()) {
            ZipEntry entryTemp = jarEntrys.nextElement();
            File desTemp = new File(desDir.getAbsoluteFile() + File.separator + entryTemp.getName());

            if (entryTemp.isDirectory()) { //jar��Ŀ�ǿ�Ŀ¼
                if (!desTemp.exists())
                    desTemp.mkdirs();
            } else { //jar��Ŀ���ļ�
                //��Ϊmanifest��Entry��"META-INF/MANIFEST.MF",д���ᱨ"FileNotFoundException"
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
