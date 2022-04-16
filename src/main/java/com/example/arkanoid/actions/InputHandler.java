package com.example.arkanoid.actions;

import javafx.event.EventHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The InputHandler class, thanks to extend of the EventHandler class, determines possible events, such as pressing or releasing a key.
 */

public class InputHandler implements EventHandler<KeyEvent> {
    final private Set<KeyCode> activeKeys = new HashSet<>();

    /**
     * The method is used to determine the set of active keys.
     *
     * @param event It accepts instances of the KayEvent class as a parameter.
     */

    @Override
    public void handle(KeyEvent event) {
        if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
            activeKeys.add(event.getCode());
        } else if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
            activeKeys.remove(event.getCode());
        }
    }

    /**
     * @return Returns an immutable set of the specified set of active keys.
     */

    public Set<KeyCode> getActiveKeys() {
        return Collections.unmodifiableSet(activeKeys);
    }
}
