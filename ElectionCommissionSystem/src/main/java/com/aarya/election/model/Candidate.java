package com.aarya.election.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidates")
public class Candidate {
    @Id
    private String candidateId;
    private String name;
    private String party;
    private String constituency;
    private int voteCount;

    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    public String getConstituency() { return constituency; }
    public void setConstituency(String constituency) { this.constituency = constituency; }
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
}
