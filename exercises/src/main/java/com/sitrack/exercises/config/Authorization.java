package com.sitrack.exercises.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class Authorization {
    private final static String ID = "ReportGeneratorTest";

    private final static String SECRET_KEY = "ccd517a1-d39d-4cf6-af65-28d65e192149";

    public static String getAuthorization() {
        long second = Instant.now().getEpochSecond();
        String hash = getHash(ID + SECRET_KEY + second);
        return String.format("SWSAuth application=\"%s\",signature=\"%s\",timestamp=\"%s\"", ID, hash, second);
    }

    private static String getHash(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        md.update(input.getBytes());
        byte[] enc = md.digest();
        return Base64.getEncoder().encodeToString(enc);
    }
}
