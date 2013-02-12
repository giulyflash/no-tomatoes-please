package com.notomatoesplease.logic;

import com.google.common.base.Preconditions;
import com.notomatoesplease.persistence.Persistence;

public abstract class AbstractLogic implements Logic {
    private final Persistence persistence;

    public AbstractLogic(final Persistence persistence) {
        Preconditions.checkNotNull(persistence, "Persistence must not be null!");
        this.persistence = persistence;
    }

    protected Persistence getPersistence() {
        return persistence;
    }

}
