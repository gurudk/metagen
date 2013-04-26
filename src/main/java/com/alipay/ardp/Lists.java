package com.alipay.ardp;

import java.util.ArrayList;
import java.util.List;

public class Lists {

    public static <In, Out> List<Out> map(List<In> in, MapFunc<In, Out> f) {
        List<Out> out = new ArrayList<Out>(in.size());
        for (In inObj : in) {
            out.add(f.apply(inObj));
        }
        return out;
    }
    
    public static <In> List<In> filter(List<In> in, Predicate<In> f) {
        List<In> out = new ArrayList<In>();
        for (In inObj : in) {
            if (f.apply(inObj)) {
                out.add(inObj);
            }
        }
        return out;
    }

    public static String join(List<String> strs, String split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.size() - 1; i++) {
            sb.append(strs.get(i)).append(split);
        }
        sb.append(strs.get(strs.size() - 1));
        return sb.toString();
    }

    public static interface MapFunc<In, Out> {
        public Out apply(In in);
    }

    public static interface Predicate<In> {
        public boolean apply(In in);
    }
}
