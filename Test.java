package com.akaleaf.course;

/** Класс Тестов */
public class Test {

    private String name;
    private int    result;

    public Test(String name, int result) {

        this.name = name;
        this.result = result;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setResult(int result) {

        this.result = result;

    }

    public String getName() {

        return name;

    }

    public int getResult() {

        return result;

    }

}
