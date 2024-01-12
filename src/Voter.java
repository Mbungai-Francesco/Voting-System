class Voter {
    private String name;
    private boolean hasVoted;
    private String code;

    public Voter(String name, String code) {
        this.name = name;
        this.code = code;
        this.hasVoted = false;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void vote() {
        this.hasVoted = true;
    }
}