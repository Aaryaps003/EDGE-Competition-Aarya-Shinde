package com.aarya.election.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidates")
public class Candidate {

    @Id
    private String prn;
    private String name;
    private String studentClass;
    private String division;
    private String backlog;
    private int voteCount = 0;

    public Candidate() {}

    public Candidate(String prn, String name, String studentClass, String division, String backlog) {
        this.prn = prn;
        this.name = name;
        this.studentClass = studentClass;
        this.division = division;
        this.backlog = backlog;
        this.voteCount = 0;
    }

    // Getters and Setters
    public String getPrn() { return prn; }
    public void setPrn(String prn) { this.prn = prn; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getBacklog() { return backlog; }
    public void setBacklog(String backlog) { this.backlog = backlog; }

    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
}
