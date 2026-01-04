package lab.events;

import lab.interfaces.Event;

public class SwitchScenesEvent extends Event {
    public SwitchScenesEvent() {
        super(EventType.SWITCH_SCENES);
    }
}
