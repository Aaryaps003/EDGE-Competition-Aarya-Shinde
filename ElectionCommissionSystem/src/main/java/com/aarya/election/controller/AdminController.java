package com.aarya.election.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aarya.election.model.Candidate;
import com.aarya.election.model.Voter;
import com.aarya.election.service.ElectionService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ElectionService service;

    @PostMapping("/addVoter")
    public String addVoter(@RequestBody Voter v) {
        return service.addVoter(v) ? "Voter added successfully" : "Duplicate PRN or invalid data";
    }

    @PostMapping("/addCandidate")
    public String addCandidate(@RequestBody Candidate c) {
        return service.addCandidate(c) ? "Candidate added successfully"
                : "Duplicate PRN or backlog found";
    }

    @GetMapping("/export")
    public String exportResults() {
        service.exportResultsToXML();
        return "Results exported successfully!";
    }
}