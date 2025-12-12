package com.mypro.competition.model;

/**
 * 用户实体类
 * 代表参与竞赛系统的用户（学生或管理员）。
 */
public class User {
    private int id;
    private String username; // 学号/工号
    private String password; // 密码
    private String name;     // 真实姓名
    private String role;     // 角色：student 或 admin
    private String studentId; // 学号
    private String major;    // 专业

    // 构造函数
    public User(int id, String username, String password, String name, String role, String studentId, String major) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.studentId = studentId;
        this.major = major;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; } // 确保有这个方法
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
}