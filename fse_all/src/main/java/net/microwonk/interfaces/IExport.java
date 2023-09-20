package net.microwonk.interfaces;

import java.util.Collection;

public interface IExport {
    <T>String export(Collection<T> data);

}
