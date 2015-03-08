package com.stupidplebs.randomstrings;

public interface RandomnessProvider {
    Integer nextInt();
    Integer nextInt(Integer n);
    Boolean nextBoolean();
}
