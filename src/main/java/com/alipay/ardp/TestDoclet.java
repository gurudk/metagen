/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.alipay.ardp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;

/**
 * 
 * @author ji.zhangyj
 * @version $Id: TestDoclet.java, v 0.1 2013-4-24 下午6:58:14 ji.zhangyj Exp $
 */

public class TestDoclet {

    /**
     * Doclet开始方法，将被下面的 main(} 方法间接调用
     * 
     * @param root
     *            参见：http://java.sun.com/j2se/1.5.0/docs/guide/javadoc/doclet/
     *            spec/index.html?com/sun/javadoc/package-summary.html
     * @return
     */
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        Set<Field> fields = new HashSet<Field>();
        Map<String, Model> map = new HashMap<String, Model>();

        for (int i = 0; i < classes.length; ++i) {
            Model model = new Model(classes[i], map);
            map.put(model.name, model);

            fields.addAll(model.fields);
        }
        TopologicalSorter<Model> sorter = new TopologicalSorter<Model>(map.values(), false);
        sorter.sort();

        List<Model> models = sorter.getSorted();

        for (Model doc : models) {
            System.out.println("***" + doc);
            final Set<String> set = new HashSet<String>();

            set.add("String");
            set.add("Date");
            set.add("Money");

            for (Field f : Lists.filter(doc.fields, new Lists.Predicate<Field>() {
                public boolean apply(Field in) {
                    return !set.contains(in.type);
                }
            })) {
                System.out.println("\t" + f.name + " " + f.type);
            }
        }

        return true;
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    public static void main(String[] arg) {
        System.out.println("Test");
        String srcDir = "/Users/argan/Study/java/javadoc/ardp-bean";
        Main.execute("javadoc", TestDoclet.class.getName(), new String[] { "-private",
                "-sourcepath", srcDir,// 源文件位置
                "-encoding", "UTF-8", 
                "-subpackages", "com.alipay.ardp.core.model",// 要处理的package
                                                                                   // name
        });

    }
}
