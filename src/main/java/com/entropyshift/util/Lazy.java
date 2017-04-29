package com.entropyshift.util;

import java.util.function.Supplier;

/**
 * Created by chaitanya.m on 4/26/17.
 */
public class Lazy<T>
{
    Supplier<T> supplier;

    public Lazy(Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    public T get()
    {
        return supplier.get();
    }

}
