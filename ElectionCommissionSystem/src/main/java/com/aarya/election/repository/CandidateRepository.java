package com.aarya.election.repository;

import com.aarya.election.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    List<Candidate> findByConstituency(String constituency);
}
