package com.notomatoesplease.logic;

import com.google.common.base.Preconditions;
import com.notomatoesplease.persistence.Persistence;

public abstract class AbstractLogic implements Logic {
    private Persistence persistence;

    public AbstractLogic(final Persistence persistence) {
        Preconditions.checkNotNull(persistence, "Persistence must not be null!");
        this.persistence = persistence;
    }

    public Persistence getPersistence() {
        return persistence;
    }

}
