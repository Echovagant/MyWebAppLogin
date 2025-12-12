package com.mypro.competition.model;

import java.util.Date;

/**
 * 报名实体类
 * 记录学生对某一竞赛的报名信息。
 */
public class Registration {
    private int id;
    private int userId;
    private int competitionId;
    private Date registrationDate;
    private String status; // 例如: "Pending", "Approved", "Rejected"
    private String teamName; // 团队名称（如果是团队赛）
    private User studentUser; // 关联的学生用户
    private Competition competition; // 关联的竞赛

    // 构造函数
    public Registration(int id, int userId, int competitionId, Date registrationDate, String status, String teamName) {
        this.id = id;
        this.userId = userId;
        this.competitionId = competitionId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.teamName = teamName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCompetitionId() { return competitionId; }
    public void setCompetitionId(int competitionId) { this.competitionId = competitionId; }
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public User getStudentUser() { return studentUser; }
    public void setStudentUser(User studentUser) { this.studentUser = studentUser; }
    public Competition getCompetition() { return competition; }
    public void setCompetition(Competition competition) { this.competition = competition; }
}