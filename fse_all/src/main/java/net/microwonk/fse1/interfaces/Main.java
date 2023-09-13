package net.microwonk.fse1.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> data = new ArrayList<>(Arrays.asList("3====D", "Nicolas", "Montag", "Dienstag"));

        IExport exporter = new XMLExport();

        System.out.println(exporter.export(data));

        exporter = new CSVExport();

        System.out.println('\n' + exporter.export(data));
    }
}
