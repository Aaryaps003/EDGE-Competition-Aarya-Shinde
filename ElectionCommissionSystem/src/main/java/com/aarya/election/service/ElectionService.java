package com.aarya.election.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import com.aarya.election.model.Candidate;
import com.aarya.election.model.Voter;
import com.aarya.election.model.ElectionStats; 
import com.aarya.election.repository.CandidateRepository;
import com.aarya.election.repository.VoterRepository;

@Service
public class ElectionService {

    @Autowired
    private VoterRepository voterRepo;

    @Autowired
    private CandidateRepository candRepo;

    @Autowired
    private SimpMessagingTemplate messaging;

    // Add new voter
    public boolean addVoter(Voter v) {
        Voter existing = voterRepo.findByPrn(v.getPrn());
        if (existing != null) return false; // duplicate PRN
        voterRepo.save(v);
        return true;
    }

    // Add new candidate
    public boolean addCandidate(Candidate c) {
        Candidate existing = candRepo.findByPrn(c.getPrn());
        if (existing != null) return false; // duplicate PRN
        if ("yes".equalsIgnoreCase(c.getBacklog())) return false;
        candRepo.save(c);
        return true;
    }

    // Cast vote
    public boolean castVote(String voterPrn, String candidatePrn) {
        // We use findById for the @Id field. It returns an Optional.
        Optional<Voter> voterOpt = voterRepo.findById(voterPrn);

        // Handle NOTA vote
        if (voterOpt.isPresent() && "NOTA".equalsIgnoreCase(candidatePrn)) {
            Voter voter = voterOpt.get();
            if (voter.isHasVoted()) return false; // Already voted
            
            voter.setHasVoted(true);
            voterRepo.save(voter);
            
            broadcastResults();
            return true;
        }
        
        Optional<Candidate> candOpt = candRepo.findById(candidatePrn);

        if (voterOpt.isEmpty() || candOpt.isEmpty()) return false;

        Voter voter = voterOpt.get();
        Candidate candidate = candOpt.get();

        if (voter.isHasVoted()) return false;

        // Prevent cross-division voting
        if (!voter.getStudentClass().equals(candidate.getStudentClass()) ||
            !voter.getDivision().equals(candidate.getDivision())) return false;

        // Update votes
        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candRepo.save(candidate);

        voter.setHasVoted(true);
        voterRepo.save(voter);

        // Update XML
        exportResultsToXML();

        // Broadcast live results
        broadcastResults();
        
        return true;
    }
    
    // Helper method to broadcast results
    private void broadcastResults() {
        List<Candidate> allResults = getAllResults();
        messaging.convertAndSend("/topic/results", allResults);
    }

    // Export XML (No changes needed, this logic is fine)
    public void exportResultsToXML() {
        try {
            List<Candidate> all = candRepo.findAll();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.newDocument();

            Element root = doc.createElement("ElectionResults");
            doc.appendChild(root);

            for (Candidate c : all) {
                Element cand = doc.createElement("Candidate");
                // ... (rest of your XML logic is correct) ...
                Element prn = doc.createElement("PRN");
                prn.appendChild(doc.createTextNode(c.getPrn()));
                cand.appendChild(prn);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(c.getName()));
                cand.appendChild(name);

                Element cls = doc.createElement("Class");
                cls.appendChild(doc.createTextNode(c.getStudentClass()));
                cand.appendChild(cls);

                Element div = doc.createElement("Division");
                div.appendChild(doc.createTextNode(c.getDivision()));
                cand.appendChild(div);

                Element votes = doc.createElement("Votes");
                votes.appendChild(doc.createTextNode(String.valueOf(c.getVoteCount())));
                cand.appendChild(votes);

                root.appendChild(cand);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc),
                    new StreamResult(new File("src/main/resources/static/results.xml")));

            System.out.println("✅ XML updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all results
    public List<Candidate> getAllResults() {
        return candRepo.findAll();
    }

    // Get candidates for voting
    public List<Candidate> getCandidatesByClassAndDiv(String cls, String div) {
        return candRepo.findByStudentClassAndDivision(cls, div);
    }
    
    // ✅ NEW METHOD FOR DASHBOARD
    public ElectionStats getElectionStats() {
        long totalVoters = voterRepo.count();
        // This method name now matches your Voter.java field ("hasVoted")
        // and your VoterRepository
        long votersWhoVoted = voterRepo.countByHasVoted(true);
        
        long totalVotesCast = candRepo.findAll().stream()
                                    .mapToLong(Candidate::getVoteCount)
                                    .sum();
                                    
        return new ElectionStats(totalVoters, votersWhoVoted, totalVotesCast);
    }
}