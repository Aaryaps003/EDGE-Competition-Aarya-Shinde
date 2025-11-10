package com.aarya.election.service;

import com.aarya.election.dto.StatsResponse;
import com.aarya.election.model.Candidate;
import com.aarya.election.model.Voter;
import com.aarya.election.repository.CandidateRepository;
import com.aarya.election.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    @Autowired private CandidateRepository candidateRepo;
    @Autowired private VoterRepository voterRepo;
    @Autowired private SimpMessagingTemplate messaging;

    // Voter register (18+, unique ID)
    public String registerVoter(Voter v) {
        try {
            if (v.getAge() < 18) return "❌ Voter must be at least 18 years old!";
            if (voterRepo.findByVoterId(v.getVoterId()) != null) return "⚠️ Voter ID already exists!";
            v.setHasVoted(false);
            voterRepo.save(v);
            return "✅ Voter registered successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error registering voter: " + e.getMessage();
        }
    }

    // Candidate register (unique ID)
    public String registerCandidate(Candidate c) {
        try {
            if (candidateRepo.findById(c.getCandidateId()).isPresent()) return "⚠️ Candidate ID already exists!";
            c.setVoteCount(0);
            candidateRepo.save(c);
            return "✅ Candidate registered successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error registering candidate: " + e.getMessage();
        }
    }

    // Cast vote (exists, not voted, same constituency)
    public String castVote(String voterId, String candidateId) {
        try {
            Voter voter = voterRepo.findByVoterId(voterId);
            if (voter == null) return "❌ Invalid Voter ID!";

            Optional<Candidate> cOpt = candidateRepo.findById(candidateId);
            if (cOpt.isEmpty()) return "❌ Invalid Candidate ID!";
            Candidate cand = cOpt.get();

            if (voter.isHasVoted()) return "⚠️ You have already voted!";
            if (!voter.getConstituency().equalsIgnoreCase(cand.getConstituency()))
                return "❌ Voter and Candidate are from different constituencies!";

            cand.setVoteCount(cand.getVoteCount() + 1);
            voter.setHasVoted(true);
            candidateRepo.save(cand);
            voterRepo.save(voter);

            // WS push + XML export
            messaging.convertAndSend("/topic/results", cand);
            exportResultsToXML();

            return "✅ Vote cast successfully for " + cand.getName() + " (" + cand.getParty() + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error casting vote: " + e.getMessage();
        }
    }

    public List<Candidate> getAllResults() { return candidateRepo.findAll(); }
    public List<Candidate> getAllCandidates() { return candidateRepo.findAll(); }
    public List<Voter> getAllVoters() { return voterRepo.findAll(); }

    public String resetElection() {
        try {
            for (Candidate c : candidateRepo.findAll()) {
                c.setVoteCount(0);
                candidateRepo.save(c);
            }
            for (Voter v : voterRepo.findAll()) {
                v.setHasVoted(false);
                voterRepo.save(v);
            }
            exportResultsToXML();
            return "✅ Election data reset successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error resetting election: " + e.getMessage();
        }
    }

    // Stats for dashboard
    public StatsResponse getStats() {
        StatsResponse s = new StatsResponse();
        long totalVoters = voterRepo.count();
        long votersVoted = voterRepo.countByHasVoted(true);
        long totalCandidates = candidateRepo.count();
        List<Candidate> all = candidateRepo.findAll();
        long totalVotes = all.stream().mapToLong(Candidate::getVoteCount).sum();
        Candidate leader = all.stream().max((a,b) -> Integer.compare(a.getVoteCount(), b.getVoteCount())).orElse(null);

        s.setTotalVoters(totalVoters);
        s.setVotersVoted(votersVoted);
        s.setTurnoutPercent(totalVoters == 0 ? 0.0 : (votersVoted * 100.0) / totalVoters);
        s.setTotalCandidates(totalCandidates);
        s.setTotalVotes(totalVotes);
        if (leader != null) {
            s.setLeadingCandidateId(leader.getCandidateId());
            s.setLeadingCandidateName(leader.getName());
            s.setLeadingCandidateParty(leader.getParty());
            s.setLeadingConstituency(leader.getConstituency());
            s.setLeadingVotes(leader.getVoteCount());
        }
        return s;
    }

    // XML export (for XML data sharing objective)
    public void exportResultsToXML() {
        try {
            List<Candidate> all = candidateRepo.findAll();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.newDocument();

            Element root = doc.createElement("ElectionResults");
            doc.appendChild(root);

            for (Candidate c : all) {
                Element cand = doc.createElement("Candidate");

                Element id = doc.createElement("CandidateId");
                id.appendChild(doc.createTextNode(c.getCandidateId()));
                cand.appendChild(id);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(c.getName()));
                cand.appendChild(name);

                Element party = doc.createElement("Party");
                party.appendChild(doc.createTextNode(c.getParty()));
                cand.appendChild(party);

                Element cons = doc.createElement("Constituency");
                cons.appendChild(doc.createTextNode(c.getConstituency()));
                cand.appendChild(cons);

                Element votes = doc.createElement("Votes");
                votes.appendChild(doc.createTextNode(String.valueOf(c.getVoteCount())));
                cand.appendChild(votes);

                root.appendChild(cand);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer tr = tf.newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(doc),
                    new StreamResult(new File("src/main/resources/static/results.xml")));

            System.out.println("🗳️ XML file updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
