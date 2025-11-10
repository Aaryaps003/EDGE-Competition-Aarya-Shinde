package com.aarya.election.repository;

import com.aarya.election.model.Voter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoterRepository extends MongoRepository<Voter, String> {
    Voter findByVoterId(String voterId);
    long countByHasVoted(boolean hasVoted);
}
