package com.mypro.competition.model;

import java.util.Date;

public class Competition {
    private int id;
    private String name;         // 竞赛名称
    private String level;        // 竞赛级别
    private String formType;     // 参赛形式（个人赛/团体赛）
    private Date startDate;      // 开始日期
    private Date endDate;        // 结束日期
    private String type;         // 竞赛类型

    // 构造函数
    public Competition(int id, String name, String level, String formType, Date startDate, Date endDate, String type) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.formType = formType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getFormType() { return formType; }
    public void setFormType(String formType) { this.formType = formType; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}