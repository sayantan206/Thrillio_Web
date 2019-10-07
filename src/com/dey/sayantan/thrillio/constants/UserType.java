package com.dey.sayantan.thrillio.constants;

public enum UserType {
    USER("user"),
    EDITOR("editor"),
    CHIEF_EDITOR("chiefeditor"),
    EMAIL_ADMIN("emailadmin");

    private final String type;
    private UserType(String type){this.type = type;}
    }
