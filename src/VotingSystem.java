import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VotingSystem {
    private List<Candidate> candidates;
    private List<Voter> voters;
    private Map<Candidate, Integer> voteCount;
    private String password = "19/11/23";

    public String getPassword() {
        return password;
    }

    public VotingSystem() {
        this.candidates = new ArrayList<>();
        this.voters = new ArrayList<>();
        this.voteCount = new HashMap<>();
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
        voteCount.put(candidate, 0);
        
    }

    public void addVoter(Voter voter) {
        voters.add(voter);
    }

    public void vote(Candidate candidate, Voter voter) {
        if (!voter.hasVoted() && candidates.contains(candidate) && voters.contains(voter)) {
            voteCount.put(candidate, voteCount.get(candidate) + 1);
            voter.vote();
            System.out.println(voter.getName() + " voted for " + candidate.getName());
        } else {
            System.out.println("Invalid vote from " + voter.getName());
        }
    }

    public void displayResults() {
        System.out.println("Voting Results:");
        for (Candidate candidate : candidates) {
            if (candidate!=null) {
                System.out.println(candidate.getName() + " (" + candidate.getParty() + "): " + voteCount.get(candidate) + " votes");
            }
            else break;
        }
    }
}