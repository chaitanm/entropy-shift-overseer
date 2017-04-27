package com.entropyshift.util;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by chaitanya.m on 4/26/17.
 */
public class Lazy<T>
{
    Optional<T> value = Optional.empty();
    Supplier<T> supplier;

    public Lazy(Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    public T get()
    {
        if (!value.isPresent())
        {
            value = Optional.of(supplier.get());
        }
        return value.get();
    }

}
