import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int size(Candidate[] can){
        int n = 0;

        for (Candidate candidate : can) {
            if(candidate != null) n++;
            else break;
        }
        return n;
    }
    public static int size(Voter[] vot){
        int n = 0;

        for (Voter voter : vot) {
            if(voter != null) n++;
            else break;
        }
        return n;
    }
    public static Candidate[] getcandi(){
        Candidate[] candidates = new Candidate[8];

        // Specify the path of the file to be read
        String candiPath = "src/Candidates.txt";
        String partyPath = "src/Parties.txt";
        int i = 0;

        try (BufferedReader read1 = new BufferedReader(new FileReader(candiPath))) {
            try (BufferedReader read2 = new BufferedReader(new FileReader(partyPath))) {
            String can;
            String par;
            while (((can = read1.readLine()) != null) && ((par = read2.readLine()) != null)) {
                candidates[i] = new Candidate(can,par);
                i++;
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int l = 0;
        for (Candidate candidate : candidates) {
            if (candidate != null) {
                int j =-1;
                for (Candidate candidate2 : candidates) {
                    if (candidate2 != null) {
                        j++;
                        int k=j;
                        // System.out.println(candidate.getParty()+"..."+candidate2.getParty()+"..."+l +"..."+j+"..."+size(candidates));
            
                        if ((candidate.getParty().equals(candidate2.getParty())) && (l<j)) {
                            // System.out.println("bhygvygvtfyvtfygvyfg");
                            while (k<size(candidates)-1) {
                                candidates[k] = candidates[k+1];
                                k++;
                            }
                            candidates[size(candidates)-1] = null;
                        }
                        // System.out.println("fun");
                    }
                }
                // System.out.println("Moon1.."+l);
                l++;
            }
        }

        String filePath = "src/Res.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write content to the file
            for (Candidate candidate : candidates) {
                if (candidate != null) {
                    writer.write(candidate.getName());
                    writer.newLine();
                }
                else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candidates;
    }
    public static void main(String[] args) {
        Scanner lire = new Scanner(System.in);
        VotingSystem votingSystem = new VotingSystem();
        Voter[] voter = new Voter[100];
        int voterNum=-1;

        Candidate[] candidates = getcandi();
        for (Candidate candidate : candidates) {
            votingSystem.addCandidate(candidate);
        }

        boolean on = true;
        int choice = 1;
        boolean user = false;
        boolean admin = false;
        boolean results = false;
        boolean conti = true;
        while (on) {
            int vote;
            String name;
            String code;
            // System.out.println("1. Admin:");
            // System.out.println("2. Voter:");
            // System.out.println("0. To quit:");
            
            
            if (choice == 1){
                System.out.println();
                if(voter[0] == null) System.out.println("Welcome Mr/Mrs admin ");
                else System.out.println("Welcome back Mr/Mrs admin ");
                for (int i = 0; i < 3; i++) {
                    System.out.println();
                    System.out.println("Please enter password: ");
                    String pass = lire.nextLine();
                    if (pass.equals(votingSystem.getPassword())) {
                        admin = true;
                        i = 3;
                    }
                    else System.out.println("Sorry wrong Password");
                }
            }
            else if (choice == 2) user = true;
            else if (choice == 0) results = true;
            else if (choice == 3) on = false;

            while (admin) {
                System.out.println();
                if(voter[0] == null){
                    System.out.println("1. Launch voting session");
                    System.out.println("2. Paste results");
                }
                else{
                    System.out.println("1. Continue voting session");
                    System.out.println("2. Paste results");
                }
                choice = lire.nextInt();
                admin = false;
                switch (choice) {
                    case 1:
                        choice = 2;
                        break;
                    case 2:
                        choice = 0;
                        break;
                    default:
                        System.out.println("Invalid input!!");
                        admin = true;
                        break;
                }
                // votingSystem.displayResults();
            }
            
            while (user) {
                Scanner read = new Scanner(System.in);
                System.out.println();
                System.out.println("Enter your name: ");
                name = read.nextLine();
                System.out.println("Enter your id Number: ");
                code = read.nextLine();
                for (Voter vot : voter) {
                    if(vot != null){
                        // System.out.println(vot.getCode());
                        // System.out.println(code);
                        if(vot.getCode().equals(code)){
                            conti = false;
                            break;
                        }else conti = true;
                    }
                    else break;
                }
                if (conti) {
                    System.out.println();
                    System.out.println("Pick a candidate by number: ");
                    for (int i = 0; i < size(candidates); i++) {
                        System.out.println((i+1)+" "+candidates[i].getName()+" of "+candidates[i].getParty());
                    }
                    System.out.println("Or Enter 0 to quit system: ");
                    vote = read.nextInt();
                    if(vote == 0){
                        choice = 1;
                        user = false;
                    }
                    else if(vote > size(candidates) || vote < 0){
                        System.out.println("Number not in range!!!");
                    }
                    else{   
                        voterNum++;
                        voter[voterNum] = new Voter(name,code);
                        votingSystem.addVoter(voter[voterNum]);
                        votingSystem.vote(candidates[vote-1], voter[voterNum]);
                    }
                }
                else{
                    System.out.println();
                    System.err.println("Sorry can't change vote or vote twice!!");
                }
                
            }
            if (results) {
                System.out.println();
                // System.out.println("Candi "+ size(candidates));
                choice = votingSystem.displayResults();
                if (choice == 2) {
                    candidates = getcandi();
                    for (Candidate candidate : candidates) {
                        votingSystem.addCandidate(candidate);
                    }
                    for (Voter vot : voter) {
                        if(vot != null){
                            // System.out.println(vot.getCode());
                            // System.out.println(code);
                            vot.setCode("0");
                        }
                        else break;
                    }
                    // votingSystem = null;
                    // voter.cl
                }
                results = false;
            }
        }

    }
}