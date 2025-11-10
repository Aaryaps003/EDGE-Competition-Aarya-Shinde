package com.aarya.election.repository;

import com.aarya.election.model.Voter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoterRepository extends MongoRepository<Voter, String> {
    // Finds a voter by their PRN (which is the @Id)
    // We use findById instead of a custom method for the primary key
    
    // Custom query to find a voter by the prn field (if it wasn't the ID)
    Voter findByPrn(String prn);
    
    // ✅ This is the correct method for the dashboard.
    // It matches your "hasVoted" field in Voter.java
    long countByHasVoted(boolean hasVoted); 
}