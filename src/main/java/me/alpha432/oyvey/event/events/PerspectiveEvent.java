package me.alpha432.oyvey.event.events;

import me.alpha432.oyvey.event.EventStage;

public
class PerspectiveEvent
        extends EventStage {
    private float aspect;

    public
    PerspectiveEvent ( float f ) {
        this.aspect = f;
    }

    public
    float getAspect ( ) {
        return this.aspect;
    }

    public
    void setAspect ( float f ) {
        this.aspect = f;
    }
}