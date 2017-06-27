package com.example.siemens.testfirebase;

import java.util.HashMap;
import java.util.Map;

public class Drug {

    public Integer id;
    public String name;
    public String text;

    Drug() {}

    Drug(Integer id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("id", id);
        result.put("name", name);
        result.put("text", text);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
