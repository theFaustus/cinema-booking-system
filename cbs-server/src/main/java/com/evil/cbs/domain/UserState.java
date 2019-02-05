package com.evil.cbs.domain;

import lombok.Getter;

@Getter
public enum UserState {
    ENABLED(1), DISABLED(0);

    private int value;

    UserState(int i) {
        this.value = i;
    }


}
