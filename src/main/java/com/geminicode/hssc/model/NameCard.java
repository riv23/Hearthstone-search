package com.geminicode.hssc.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Cache
@Entity
public class NameCard {

    @Id
    private String id;

    @Index
    private String name;

    @Index
    private String compute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompute() {
        return compute;
    }

    public void setCompute(String compute) {
        this.compute = compute;
    }
}
