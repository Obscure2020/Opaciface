public class ScoredWord implements Comparable<ScoredWord>{

    private String word;
    private double score;

    ScoredWord(String text, double number){
        word = text;
        score = number;
    }

    public String getWord() {
        return word;
    }

    public double getScore() {
        return score;
    }

    public int compareTo(ScoredWord other) {
        Double mine = Double.valueOf(score);
        Double theirs = Double.valueOf(other.getScore());
        if(mine.equals(theirs)) return word.compareTo(other.getWord());
        return theirs.compareTo(mine);
    }
}