package com.isaac.testcxc.exceptions;

public class CatalogNotFoundException extends Exception {
    public CatalogNotFoundException() {
        super("Missing required field");
    }
}
