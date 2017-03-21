package org.usfirst.frc.team3151.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

abstract class ActionBasedAutoMode implements AutoMode {

    private final List<BooleanSupplier> actions = new ArrayList<>();

    void resetAutoActions() {
        actions.clear();
    }

    void registerAutoAction(BooleanSupplier action) {
        actions.add(action);
    }

    @Override
    public void tick() {
        // continually call the next action until it returns true
        // (which signals that it's completed)
        if (!actions.isEmpty() && actions.get(0).getAsBoolean()) {
            actions.remove(0);
        }
    }

}
