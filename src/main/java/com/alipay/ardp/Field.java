package com.alipay.ardp;

import java.util.Arrays;

import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;

/**
 * 代表Bean中的一个field，包含名字类型和注释
 * 
 * 对于List<Bean> 类型有特殊处理，目前不支持List的基本类型的参数化
 * 对于Map<String,String>将会转换成List<KeyValuePair>，对于Map的其他参数化类型暂不支持
 * 
 * @author Argan Wang <kerrigan@alipay.com>
 *
 */
public class Field {
    private static String[] strstr = new String[] { "String", "String" };

    Field(FieldDoc doc) {
        this.name = capitalize(doc.name());
        this.type = capitalize(doc.type().typeName());
        this.comment = Utils.firstComment(doc.commentText());
        if (doc.type().asParameterizedType() != null) {
            this.hasTypeParam = true;
            this.typeParam = map(doc.type().asParameterizedType().typeArguments());
        }
        if ("Map".equals(this.type) && this.hasTypeParam && Arrays.equals(this.typeParam, strstr)) {
            this.type = "List";
            this.typeParam = new String[] { "KeyValuePair" };
        }
        if("Money".equals(this.type)){
            this.type = "Long";
        }
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private String[] map(Type[] types) {
        String[] results = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            results[i] = types[i].typeName();
        }
        return results;
    }

    String name;
    String type;
    String[] typeParam;
    String comment;
    boolean hasTypeParam = false;

    public boolean isList() {
        return "List".equals(this.type);
    }

    public String fullName() {
        return hasTypeParam ? name + "[]" : name;
    }

    public String paramType() {
        if (typeParam.length > 0) {
            return typeParam[0];
        }
        return "?";
    }

    @Override
    public boolean equals(Object o) {
        Field other = (Field) o;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s{<cons|$cnName='%s',$memo='%s',$type='%s'>}\n", name, comment, comment, type);
    }
}