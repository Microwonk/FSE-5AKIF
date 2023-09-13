package net.microwonk.fse1.interfaces;

import java.util.Collection;

public interface IExport {
    <T>String export(Collection<T> data);

}
