package com.asgdrones.drones.enums;

public enum Courses {

    COURSE_1("type1"),
    COURSE_2("type2"),
    COURSE_3("type3");

    private final String name;

    private Courses(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
