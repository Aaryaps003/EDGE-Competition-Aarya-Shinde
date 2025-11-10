package com.aarya.election.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "voters")
public class Voter {
    @Id
    private String voterId;
    private String name;
    private int age;
    private String constituency;
    private boolean hasVoted;

    public String getVoterId() { return voterId; }
    public void setVoterId(String voterId) { this.voterId = voterId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getConstituency() { return constituency; }
    public void setConstituency(String constituency) { this.constituency = constituency; }
    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
}
