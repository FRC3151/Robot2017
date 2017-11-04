package org.usfirst.frc.team3151.auto;

public interface AutoAction {

    Result execute();

    enum Result {

        CONTINUE_EXECUTING,
        COMPLETED

    }

}