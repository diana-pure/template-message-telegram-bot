package com.diana.pure;

public enum InputParamType {
    DATE(0),
    PLACE(1),
    DESCRIPTION(2);

    private final int position;

    InputParamType(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
