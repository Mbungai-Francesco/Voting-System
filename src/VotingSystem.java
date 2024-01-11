import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    public int displayResults() {
        System.out.println("Voting Results:");
        Candidate[] maxCandi = new Candidate[100];
        int max = 0;
        int i = 0;
        for (Candidate candidate : candidates) {
            if (candidate!=null) {
                System.out.println(candidate.getName() + " (" + candidate.getParty() + "): " + voteCount.get(candidate) + " votes");
                if(max <= voteCount.get(candidate)){
                    max = voteCount.get(candidate);
                }
            }
            else break;
        }
        for (Candidate candidate : candidates) {
            if (candidate!=null) {
                if(max == voteCount.get(candidate)){
                    maxCandi[i] = new Candidate(candidate.getName(),candidate.getParty());
                    i++;
                }
            }
            else break;
        }
    
        if (i==1) {
            System.out.println();
            System.out.println();
            System.out.println("!!!!"+maxCandi[0].getName()+" of "+maxCandi[0].getParty()+" Won the elections..!!");
            System.out.println();
            System.out.println();
            return 3;
        }
        else{
            System.out.println("We had a draw between: ");
            for (int j = i-1; j >= 0; j--) {
                System.out.println(maxCandi[j].getName()+" of "+maxCandi[j].getParty());
            }
            String canPath = "src/Candidates.txt";
            String partPath = "src/Parties.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(canPath))) {
                try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(partPath))) {
                    // Write content to the file
                    for (Candidate candidate : maxCandi) {
                        if (candidate != null) {
                            writer.write(candidate.getName());
                            writer.newLine();
                            writer2.write(candidate.getParty());
                            writer2.newLine();
                        }
                        else break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 2;
        }
    }
}