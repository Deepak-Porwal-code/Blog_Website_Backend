package com.blog.backend.dto;

public class UserResponse {
    
    private Long id;
    private String username;
    private String email;
    private String name;
    private String avatar;
    
    // Constructors
    public UserResponse() {}
    
    public UserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = username; // Use username as name for simplicity
        this.avatar = "https://api.dicebear.com/7.x/avataaars/svg?seed=" + email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}