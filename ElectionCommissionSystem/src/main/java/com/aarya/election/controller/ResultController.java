package com.aarya.election.controller;

import com.aarya.election.dto.StatsResponse;
import com.aarya.election.model.Candidate;
import com.aarya.election.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ResultController {

    @Autowired private ElectionService electionService;

    @GetMapping("/results")
    public List<Candidate> getResults() {
        return electionService.getAllResults();
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        return electionService.getStats();
    }
}
