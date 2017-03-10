package org.usfirst.frc.team3151.auto.action;

import java.util.function.BooleanSupplier;

public final class DelayAutoAction implements BooleanSupplier {

    private final long ms;
    private long firstTicked = 0;

    public DelayAutoAction(long ms) {
        this.ms = ms;
    }

    @Override
    public boolean getAsBoolean() {
        if (firstTicked == 0) {
            firstTicked = System.currentTimeMillis();
        }

        return System.currentTimeMillis() >= firstTicked + ms;
    }

}