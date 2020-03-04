package com.example.shorebuddy.utilities;

public class Event<T> {
    private boolean mHasBeenHandled = false;
    private T mContent;

    public Event(T value) {
        mContent = value;
    }

    public T getContentIfNotHandled(boolean hasBeenHandled) {
        if (mHasBeenHandled) {
            return null;
        } else {
            mHasBeenHandled = hasBeenHandled;
            return mContent;
        }
    }

    public T peekContent() {
        return mContent;
    }
}
