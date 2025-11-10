package com.aarya.election.repository;

import com.aarya.election.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    
    // Custom query to find a candidate by the prn field
    Candidate findByPrn(String prn);

    // Custom query to find candidates by class and division
    List<Candidate> findByStudentClassAndDivision(String studentClass, String division);
}