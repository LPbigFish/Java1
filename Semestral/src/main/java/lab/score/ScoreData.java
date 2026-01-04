package lab.score;

import org.jetbrains.annotations.NotNull;

public record ScoreData(String name, int score) {
    public int getScore() {
        return score;
    }
    public String getName(){
        return name;
    }

    @Override
    public @NotNull String toString() {
        return name + ";" + score;
    }
}
