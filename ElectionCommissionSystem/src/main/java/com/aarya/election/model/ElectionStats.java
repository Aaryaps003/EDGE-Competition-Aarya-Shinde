package com.aarya.election.model;

// This is a new class to hold your dashboard statistics
public class ElectionStats {
    
    private long totalVoters;
    private long votersWhoVoted;
    private long totalVotesCast; // Sum of all candidate votes

    // Constructors
    public ElectionStats() {}

    public ElectionStats(long totalVoters, long votersWhoVoted, long totalVotesCast) {
        this.totalVoters = totalVoters;
        this.votersWhoVoted = votersWhoVoted;
        this.totalVotesCast = totalVotesCast;
    }

    // Getters and Setters
    public long getTotalVoters() { return totalVoters; }
    public void setTotalVoters(long totalVoters) { this.totalVoters = totalVoters; }

    public long getVotersWhoVoted() { return votersWhoVoted; }
    public void setVotersWhoVoted(long votersWhoVoted) { this.votersWhoVoted = votersWhoVoted; }

    public long getTotalVotesCast() { return totalVotesCast; }
    public void setTotalVotesCast(long totalVotesCast) { this.totalVotesCast = totalVotesCast; }
}