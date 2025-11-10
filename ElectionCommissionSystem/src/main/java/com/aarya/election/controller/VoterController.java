package com.aarya.election.controller;

import com.aarya.election.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vote")
public class VoterController {

    @Autowired private ElectionService electionService;

    @PostMapping("/{voterId}/{candidateId}")
    public ResponseEntity<String> castVote(@PathVariable String voterId, @PathVariable String candidateId) {
        String result = electionService.castVote(voterId, candidateId);
        return result.startsWith("✅") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}
