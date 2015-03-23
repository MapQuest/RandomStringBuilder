package com.stupidplebs.randomstrings.provider;

public interface RandomnessProvider {
    Integer nextInt();
    Integer nextInt(Integer n);
    Boolean nextBoolean();
}
