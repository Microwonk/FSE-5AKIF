package net.microwonk.interfaces;

import java.util.Collection;

public class CSVExport implements IExport {

    @Override
    public <T> String export(Collection<T> data) {
        StringBuilder res = new StringBuilder();
        for (T d: data) {
            res.append(d.toString()).append(",");
        }
        res.deleteCharAt(res.length() -1);

        return res.toString();
    }
}
