package com.dey.sayantan.thrillio.constants;

public enum Gender {
    MALE(0),
    FEMALE(1),
    TRANSGENDER(2);

    private final int genderIndex;
    private Gender(int genderIndex){this.genderIndex = genderIndex;}
    public int getGenderIndex(){
        return this.genderIndex;
    }
}
