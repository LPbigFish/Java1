package lab.interfaces;

import lab.events.EventType;

public abstract class Event {
    EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }
}