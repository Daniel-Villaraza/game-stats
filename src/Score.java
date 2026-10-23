public class Score implements Comparable<Score> {
    private int scoreValue = 0;

    public Score(int score) {
        scoreValue = score;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int score) {
        scoreValue = score;
    }

    public String toString() {
        return "Score: " + scoreValue;
    }

    public int compareTo(Score otherScore) {
        if (scoreValue < otherScore.getScoreValue()) {
            return -1;
        } else if (scoreValue == otherScore.getScoreValue()) {
            return 0;
        }

        return 1;
    }
}
