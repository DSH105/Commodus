package com.dsh105.simpleutils.exceptions;

public class ManifestAttributeNotFoundException extends RuntimeException {

    public ManifestAttributeNotFoundException(String s) {
        super(s);
    }

    public ManifestAttributeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ManifestAttributeNotFoundException(Throwable throwable) {
        super(throwable);
    }
}