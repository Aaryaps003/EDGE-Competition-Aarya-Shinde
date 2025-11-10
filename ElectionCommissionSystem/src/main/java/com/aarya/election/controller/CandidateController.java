package com.aarya.election.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aarya.election.model.Candidate;
import com.aarya.election.service.ElectionService;
import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private ElectionService service;

    @GetMapping("/{cls}/{div}")
    public List<Candidate> getCandidates(@PathVariable String cls, @PathVariable String div) {
        return service.getCandidatesByClassAndDiv(cls, div);
    }
}