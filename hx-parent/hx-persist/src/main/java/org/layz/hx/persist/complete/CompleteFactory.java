package org.layz.hx.persist.complete;

import org.layz.hx.base.inte.Complete;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CompleteFactory {
    private static final List<Complete> store;

    static {
        store = new ArrayList<>();
        for (Complete entity : ServiceLoader.load(Complete.class)) {
            store.add(entity);
        }
    }

    /**
     * 信息完善
     * @param obj
     */
    public static void complete(Object obj){
        for (Complete complete : store) {
            if(complete.support(obj)) {
                complete.complete(obj);
            }
        }
    }
}
