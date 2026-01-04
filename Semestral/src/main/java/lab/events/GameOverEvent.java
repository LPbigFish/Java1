package lab.events;

import lab.interfaces.Event;

public class GameOverEvent extends Event {
    public GameOverEvent() {
        super(EventType.GAME_OVER);
    }
}