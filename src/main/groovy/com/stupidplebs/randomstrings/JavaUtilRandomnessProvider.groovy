package com.stupidplebs.randomstrings;

import java.util.Random;

public class JavaUtilRandomnessProvider implements RandomnessProvider {
    private final Random random = new Random();
    
    public JavaUtilRandomnessProvider() {
    }

    @Override
    public Integer nextInt() {
        return random.nextInt();
    }

    @Override
    public Integer nextInt(final Integer n) {
        return random.nextInt(n);
    }

    @Override
    public Boolean nextBoolean() {
        return random.nextBoolean();
    }

}
