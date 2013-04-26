/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.sun.tools.javadoc.Main;

/**
 * Metadata define template gengerator
 * 
 * @goal metagen
 * 
 * @phase process-sources
 * 
 * @author ji.zhangyj
 * @version $Id: MetadataDefineTemplateMojo.java, v 0.1 2013-4-24 ����3:07:09 ji.zhangyj Exp $
 */
public class MetaGenMojo extends AbstractMojo {

    /**
     * Java package names
     * @parameter
     * @required
     */
    private List<String> packageNames = new ArrayList<String>();

    /**
     * Project source file directory
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     */
    private String       sourceDirectory;

    /**
     * build directory
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private String       directory;

    /**
     * Jar file list
     * @parameter
     */
    private List<Jar>    jars         = new ArrayList<Jar>();

    /**
     * Local repository directory
     * @parameter expression="${settings.localRepository}"
     */
    private String       repository;

    /** 
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("Hello��begin to work!");

        for (String packageName : packageNames) {
            //ɨ��Դ�ļ�Ŀ¼
            String srcDir = sourceDirectory + File.separator
                            + packageName.replace(".", File.separator) + File.separator;
            File fileDir = new File(srcDir);
            if (fileDir != null && fileDir.isDirectory() && fileDir.exists()) {
                Main.execute("javadoc", MetaGenDoclet.class.getName(), new String[] { "-private",
                        "-sourcepath", sourceDirectory,//Դ�ļ�λ��
                        "-subpackages", packageName,//Ҫ�����package name
                        "-tpl", srcDir + "metadata-def.txt", });
            }

            //ɨ��jar������
            //1.����jar���ã�ȷ���ļ�Ŀ¼
            //2.��ѹĿ¼
            //3.���������ĵ�
            //4.ɾ����ʱĿ¼
            for (Jar jar : jars) {
                String jarDir = repository + File.separator
                                + jar.getGroupId().replace(".", File.separator) + File.separator
                                + jar.getArtifactId() + File.separator + jar.getVersion();

                File jarDirFile = new File(jarDir);
                File jarTempDir = new File(jarDirFile + File.separator + "temp");
                if (jarDirFile.exists()) {
                    File[] files = jarDirFile.listFiles();
                    if (files != null && files.length != 0) {
                        for (File f : files) {
                            if (f.getName().endsWith("-sources.jar")) {
                                try {
                                    //��ѹ�ļ�
                                    Utils.unzipJar(f, jarTempDir);
                                    //ɨ�������ı�    
                                    Main.execute(
                                        "javadoc",
                                        MetaGenDoclet.class.getName(),
                                        new String[] { "-private",
                                                "-sourcepath",
                                                jarTempDir.getAbsolutePath(),//Դ�ļ�λ��
                                                "-subpackages",
                                                packageName,//Ҫ�����package name
                                                "-tpl",
                                                jarDirFile.getAbsolutePath() + "/metadata-def.txt", });

                                    //ɾ���ļ�
                                    Utils.deleteFile(jarTempDir);
                                } catch (FileNotFoundException e) {
                                    getLog().error("�ļ�δ�ҵ�~", e);
                                } catch (IOException e) {
                                    getLog().error("��ȡ�ļ��쳣", e);
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public List<String> getPackageNames() {
        return packageNames;
    }

    public void setPackageNames(List<String> packageNames) {
        this.packageNames = packageNames;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<Jar> getJars() {
        return jars;
    }

    public void setJars(List<Jar> jars) {
        this.jars = jars;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
}
