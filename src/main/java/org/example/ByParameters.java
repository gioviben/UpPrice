package org.example;

import java.util.Iterator;
import java.util.List;

public class ByParameters {
    private List<String> byClass;
    private List<String> byId;
    private List<String> byXPATH;

    public ByParameters(List<String> byClass, List<String> byId, List<String> byXPATH) {
        this.byClass = byClass;
        this.byId = byId;
        this.byXPATH = byXPATH;
    }

    public Iterator<String> getIterator() {
        if (!byClass.isEmpty()) {
            return byClass.iterator();
        } else if (!byId.isEmpty()){
            return byId.iterator();
        }else{
            return byXPATH.iterator();
        }
    }

    public List<String> getByClass() {
        return byClass;
    }

    public List<String> getById() {
        return byId;
    }

    public List<String> getByXPATH() {
        return byXPATH;
    }
}
