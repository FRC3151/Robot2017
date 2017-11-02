package org.usfirst.frc.team3151.auto.mode;

import edu.wpi.first.wpilibj.Preferences;
import org.usfirst.frc.team3151.auto.AutoMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

abstract class ActionBasedAutoMode implements AutoMode {

    private final List<BooleanSupplier> actions = new ArrayList<>();

    void registerAction(BooleanSupplier action) {
        actions.add(action);
    }

    double readSetting(String key, double def) {
        return Preferences.getInstance().getDouble(key, def);
    }

    @Override
    public void autonomousPeriodic() {
        // continually call the next action until it returns true
        // (which signals that it's completed)
        if (!actions.isEmpty() && actions.get(0).getAsBoolean()) {
            actions.remove(0);
        }
    }

}