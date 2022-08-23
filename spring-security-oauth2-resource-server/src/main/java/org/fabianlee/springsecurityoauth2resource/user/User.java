package org.fabianlee.springsecurityoauth2resource.user;

import java.io.Serializable;

public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }
}
