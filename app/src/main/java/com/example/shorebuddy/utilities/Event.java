package com.example.shorebuddy.utilities;

public class Event<T> {
    private boolean hasBeenHandled = false;
    private final T content;

    public Event(T value) {
        content = value;
    }

    public T getContentIfNotHandled(boolean hasBeenHandled) {
        if (this.hasBeenHandled) {
            return null;
        } else {
            this.hasBeenHandled = hasBeenHandled;
            return content;
        }
    }

    public T peekContent() {
        return content;
    }
}
