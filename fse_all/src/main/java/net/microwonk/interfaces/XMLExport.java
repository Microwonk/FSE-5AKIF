package net.microwonk.interfaces;

import java.util.Collection;

public class XMLExport implements IExport {

    @Override
    public <T> String export(Collection<T> data) {
        StringBuilder res = new StringBuilder("<data>");
        for (T d: data) {
            res.append("\n  <data>").append(d.toString()).append("</data>");
        }
        res.append("\n<data>");

        return res.toString();
    }

}
