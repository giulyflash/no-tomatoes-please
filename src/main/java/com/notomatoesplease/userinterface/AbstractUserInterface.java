package com.notomatoesplease.userinterface;

import com.google.common.base.Preconditions;
import com.notomatoesplease.logic.Logic;

public abstract class AbstractUserInterface implements UserInterface {

    private Logic logic;

    public AbstractUserInterface(final Logic logic) {
        Preconditions.checkNotNull(logic, "Logic must not be null!");
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }

}
