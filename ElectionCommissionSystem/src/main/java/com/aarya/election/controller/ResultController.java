package com.aarya.election.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aarya.election.model.Candidate;
import com.aarya.election.model.ElectionStats;
import com.aarya.election.service.ElectionService;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ElectionService service;

    @GetMapping
    public List<Candidate> results() {
        return service.getAllResults();
    }
    
    @GetMapping("/stats")
    public ElectionStats getStats() {
        return service.getElectionStats();
    }
}