package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {

    private static final Map<String, String> envMap = new HashMap<>();

    static {
        loadEnv();
    }

    private static void loadEnv() {
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = br.readLine()) != null) {

                // ignore empty lines and comments
                if (line.isBlank() || line.startsWith("#")) continue;

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println(".env file not found. Using defaults or failing gracefully.");
        }
    }

    public static String get(String key) {
        return envMap.get(key);
    }
}
