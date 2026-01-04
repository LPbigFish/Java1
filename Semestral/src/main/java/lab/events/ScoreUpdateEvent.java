package lab.events;

import lab.interfaces.Event;

public class ScoreUpdateEvent extends Event {
    final int score;
    public ScoreUpdateEvent(int score) {
        super(EventType.UPDATE_SCORE);
        this.score = score;
    }
    public int getScore() {
        return score;
    }
}
