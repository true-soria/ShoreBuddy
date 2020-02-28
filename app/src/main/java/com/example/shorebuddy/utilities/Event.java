package com.example.shorebuddy.utilities;

public class Event<T> {
    private boolean hasBeenHandled = false;
    private T content;

    public Event(T value) {
        content = value;
    }

    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            return content;
        }
    }

    public T peekContent() {
        return content;
    }
}
