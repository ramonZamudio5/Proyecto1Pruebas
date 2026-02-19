/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.util;

import java.util.UUID;

/**
 *
 * @author payde
 */
public final class GeneradorId {

    public GeneradorId() {
    }
    
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
