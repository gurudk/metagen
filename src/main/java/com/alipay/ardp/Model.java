package com.alipay.ardp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

/**
 * 代表一个Bean的映射信息，主要有名字和注释以及field信息（会排除static feild）
 * 
 * @author Argan Wang<kerrigan@alipay.com>
 *
 */
public class Model implements DirectedGraphNode {

    String name;
    String comment;
    List<Field> fields = new ArrayList<Field>();
    Map<String, Model> map;

    Model(ClassDoc doc, Map<String, Model> map) {
        this.map = map;
        this.name = doc.name();
        this.comment = Utils.firstComment(doc.commentText());
        FieldDoc[] flds = doc.fields();
        for (int j = 0; j < flds.length; j++) {
            if (!flds[j].isStatic()) {
                // 排除static的field
                Field field = new Field(flds[j]);
                this.fields.add(field);
            }
        }
    }

    @Override
    public String toString() {
        List<String> elems = Lists.map(fields, new Lists.MapFunc<Field, String>() {
            public String apply(Field in) {
                return "\t\t" + in.fullName() + " --'" + in.comment + "'";
            }
        });
        List<String> ins = Lists.map(fields, new Lists.MapFunc<Field, String>() {
            public String apply(Field in) {
                return "\t\t" + in.name + "='useless'";
            }
        });
        String fixMappings = "\t\t$dataSource='useless',\n\t\t$tableName='useless',\n";
        String cons = "";
        // 类型为List的字段要加类型信息到cons中
        List<Field> listFields = Lists.filter(fields, new Lists.Predicate<Field>() {
            public boolean apply(Field in) {
                return in.isList();
            }
        });
        if (listFields.size() > 0) {
            cons = ",\n" + Lists.join(Lists.map(listFields, new Lists.MapFunc<Field, String>() {
                public String apply(Field in) {
                    return "\t\t" + in.name + "$type='" + in.paramType() + "'";
                }
            }), ",\n");
        }
        return String
                .format("%s{\n\t<elem|\n%s\n\t>,\n\t<cons|\n\t\t$identity='ID',\n\t\t$cnName='%s',\n\t\t$memo='%s'%s\n\t\t>,\n\t<insMapping|\n%s%s\n\t>\n}\n",
                        name, Lists.join(elems, ",\n"), name, comment, cons, fixMappings, Lists.join(ins, ",\n"));
    }

    public Collection<Model> getPredecessors() {
        List<Model> deps = Lists.map(this.fields, new Lists.MapFunc<Field, Model>() {

            public Model apply(Field in) {
                return map.get(in.hasTypeParam?in.paramType():in.type);
            }
        });

        return Lists.filter(deps, new Lists.Predicate<Model>() {

            public boolean apply(Model in) {
                return in != null;
            }
        });
    }

}