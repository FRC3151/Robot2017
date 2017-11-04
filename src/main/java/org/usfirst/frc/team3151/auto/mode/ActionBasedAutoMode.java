package org.usfirst.frc.team3151.auto.mode;

import edu.wpi.first.wpilibj.Preferences;
import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.auto.AutoMode;

import java.util.ArrayList;
import java.util.List;

abstract class ActionBasedAutoMode implements AutoMode {

    private final List<AutoAction> actions = new ArrayList<>();

    void registerAction(AutoAction action) {
        actions.add(action);
    }

    double readSetting(String key, double def) {
        return Preferences.getInstance().getDouble(key, def);
    }

    @Override
    public void autonomousPeriodic() {
        // linearly execute actions in order until they're all completed
        if (!actions.isEmpty() && actions.get(0).execute() == AutoAction.Result.COMPLETED) {
            actions.remove(0);
        }
    }

}