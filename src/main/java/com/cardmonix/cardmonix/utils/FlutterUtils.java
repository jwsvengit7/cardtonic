package com.cardmonix.cardmonix.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
@Data
public class FlutterUtils {

    @Value("${flutter.port.number}")
    public static String FLUTTER_PORT_NUMBER;

    public static String getFlutterPortNumber(){
        return FLUTTER_PORT_NUMBER;
    }

}
