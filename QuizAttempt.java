package Quiz.Application;

import java.util.Date;

public class QuizAttempt {
    private final String quizLevel;
    private final int score;
    private final Date date;

    public QuizAttempt(String quizLevel, int score, Date date) {
        this.quizLevel = quizLevel;
        this.score = score;
        this.date = date;
    }

    public String getQuizLevel() {
        return quizLevel;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    String getAttemptDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
