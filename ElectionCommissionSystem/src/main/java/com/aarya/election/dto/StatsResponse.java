package com.aarya.election.dto;

public class StatsResponse {
    private long totalVoters;
    private long votersVoted;
    private double turnoutPercent;
    private long totalCandidates;
    private long totalVotes;
    private String leadingCandidateId;
    private String leadingCandidateName;
    private String leadingCandidateParty;
    private String leadingConstituency;
    private int leadingVotes;

    public long getTotalVoters() { return totalVoters; }
    public void setTotalVoters(long totalVoters) { this.totalVoters = totalVoters; }
    public long getVotersVoted() { return votersVoted; }
    public void setVotersVoted(long votersVoted) { this.votersVoted = votersVoted; }
    public double getTurnoutPercent() { return turnoutPercent; }
    public void setTurnoutPercent(double turnoutPercent) { this.turnoutPercent = turnoutPercent; }
    public long getTotalCandidates() { return totalCandidates; }
    public void setTotalCandidates(long totalCandidates) { this.totalCandidates = totalCandidates; }
    public long getTotalVotes() { return totalVotes; }
    public void setTotalVotes(long totalVotes) { this.totalVotes = totalVotes; }
    public String getLeadingCandidateId() { return leadingCandidateId; }
    public void setLeadingCandidateId(String leadingCandidateId) { this.leadingCandidateId = leadingCandidateId; }
    public String getLeadingCandidateName() { return leadingCandidateName; }
    public void setLeadingCandidateName(String leadingCandidateName) { this.leadingCandidateName = leadingCandidateName; }
    public String getLeadingCandidateParty() { return leadingCandidateParty; }
    public void setLeadingCandidateParty(String leadingCandidateParty) { this.leadingCandidateParty = leadingCandidateParty; }
    public String getLeadingConstituency() { return leadingConstituency; }
    public void setLeadingConstituency(String leadingConstituency) { this.leadingConstituency = leadingConstituency; }
    public int getLeadingVotes() { return leadingVotes; }
    public void setLeadingVotes(int leadingVotes) { this.leadingVotes = leadingVotes; }
}
