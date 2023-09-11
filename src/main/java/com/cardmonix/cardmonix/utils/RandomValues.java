package com.cardmonix.cardmonix.utils;

import lombok.Data;

import java.util.UUID;

@Data
public class RandomValues {

    public static String generateRandom(){
        String generator = UUID.randomUUID().toString();
        return generator.toUpperCase();
    }
}
