package com.aarya.election.controller;

import com.aarya.election.model.Candidate;
import com.aarya.election.model.Voter;
import com.aarya.election.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ElectionService electionService;

    // ✅ Admin login (simple credential check)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> creds) {
        String user = creds.get("username");
        String pass = creds.get("password");
        if ("admin".equals(user) && "aarya@123".equals(pass)) {
            return ResponseEntity.ok("✅ Login successful");
        } else {
            return ResponseEntity.status(401).body("❌ Invalid credentials");
        }
    }

    @PostMapping("/voter")
    public ResponseEntity<String> addVoter(@RequestBody Voter voter) {
        String result = electionService.registerVoter(voter);
        return result.startsWith("✅") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/candidate")
    public ResponseEntity<String> addCandidate(@RequestBody Candidate candidate) {
        String result = electionService.registerCandidate(candidate);
        return result.startsWith("✅") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/voters")
    public List<Voter> getAllVoters() {
        return electionService.getAllVoters();
    }

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return electionService.getAllCandidates();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetElection() {
        return ResponseEntity.ok(electionService.resetElection());
    }
}
