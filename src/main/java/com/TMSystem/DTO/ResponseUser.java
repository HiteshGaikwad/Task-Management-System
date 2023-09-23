package com.TMSystem.DTO;

import java.util.List;

public class ResponseUser {

    private long id;
    private String username;
    private String password;
    private List<String> taskTitles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getTaskTitles() {
        return taskTitles;
    }

    public void setTaskTitles(List<String> taskTitles) {
        this.taskTitles = taskTitles;
    }
}
