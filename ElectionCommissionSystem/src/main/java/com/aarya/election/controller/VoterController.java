package com.aarya.election.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // <-- IMPORT THIS
import org.springframework.web.bind.annotation.*;
import com.aarya.election.service.ElectionService;

@RestController
@RequestMapping("/api/vote")
public class VoterController {

    @Autowired
    private ElectionService service;

    @PostMapping("/{voterPrn}/{candidatePrn}")
    public ResponseEntity<String> castVote(@PathVariable String voterPrn, @PathVariable String candidatePrn) {
        
        boolean success = service.castVote(voterPrn, candidatePrn);
        
        if (success) {
            // ✅ Return HTTP 200 OK
            return ResponseEntity.ok("Vote cast successfully!");
        } else {
            // ✅ Return HTTP 400 Bad Request
            String errorMessage = "Vote failed: You may have already voted, or your PRN is invalid for this election.";
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}