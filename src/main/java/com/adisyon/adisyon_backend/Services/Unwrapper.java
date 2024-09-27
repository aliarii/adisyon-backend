package com.adisyon.adisyon_backend.Services;

import java.util.Optional;

import com.adisyon.adisyon_backend.Exception.NotFoundException;

public class Unwrapper {
    public static <T> T unwrap(Optional<T> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException(id.toString());
    }
}
