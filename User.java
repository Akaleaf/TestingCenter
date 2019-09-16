package com.akaleaf.course;

/**
 *     Список изменений
 * Изменён метод removeTest:
 *   Удалена лишняя строка в теле цикла: tests[index + 1] = null;
 *
 */

import java.util.Arrays;

/** Класс пользователей */
public class User {

    private String lastName;
    private User next;

    private Test tests[];
    private int testsTop = -1;

    public User() {

    }

    /**
     * Конструктор объектов User.
     *
     * @param lastName    Используется для задания имени для нового объекта.
     * @param testsLength Используется для задания размера массива, в котором хранятся тесты.
     */
    public User(String lastName, int testsLength) {

        this.lastName = lastName;
        tests = new Test[testsLength];

    }

    public void setLastName(String lastName) {

        this.lastName = lastName;

    }

    /**
     * Ищет test в массиве tests по совпадению свойства name и
     * присваивает свойству result объекта test
     * новое значение result.
     *
     * @param name   Используется для нахождения test
     * @param result Новое значение для свойства result у test
     */
    public void setTestResult(String name, int result) {

        int i = 0;

        while (!(tests[i].getName().equals(name))) {

            i++;

        }

        tests[i].setResult(result);

    }

    /**
     * Ищет test в массиве tests по совпадению свойства name и
     * присваивает свойству name объекта test
     * новое значение newName.
     *
     * @param oldName Используется для нахождения test
     * @param newName Новое значение для свойства name у test
     */
    public void setTestName(String oldName, String newName) {

        int i = 0;

        while (i == tests.length && !(tests[i].getName().equals(oldName))) {

            i++;

        }
        
        for (i = 0; i != tests.length; i++) {
        
            if (tests[i] != null && tests[i].getName().equals(oldName))  {
            
                tests[i].setName(newName);
                break;
            
            }
        
        }
        
    }

    /**
     * Удаляет первый(т.к. очередь) test из массива tests
     *
     * @return результат удаления: true - успешно
     *                             false - неудачно.
     */
    public boolean removeTest() {

        if (tests[0] != null) {

            for (int index = 0; index != testsTop + 1; index++) {

                tests[index] = tests[index + 1];

            }

            testsTop--;

            return true;

        } else {

            return false;

        }

    }
    
    /**
     * Удаляет test с совпадающим lastName из массива tests
     *
     * @param testName Удаляемый тест
     * @return результат удаления: true - успешно
     *                             false - неудачно.
     */
    public boolean removeTest(String testName) {

        if (isTestAlreadyExists(testName)) {
            
            int iterator = 0;
            
            while (tests[iterator] != null && !tests[iterator].getName().equals(testName)) {
            
                iterator++;
            
            }
            
            for (iterator = iterator; iterator != testsTop + 1; iterator++) {
            
                tests[iterator] = tests[iterator + 1];
            
            }

            testsTop--;

            return true;

        } else {

            return false;

        }

    }

    /**
     * Добавляет новый test в конец массива tests
     *
     * @param name   Используется для задания свойства name новому test
     * @param result Используется для задания свойства result новому test
     */
    public void addTest(String name, int result) {

        if (!isTestAlreadyExists(name)) {
        
            testsTop++;
            tests[testsTop] = new Test(name, result);
        
        }

        /*
        Увеличение размера массива, если остаётся мало места.
        2 - во сколько раз нужно увеличить размер массива.
         */
        if (testsTop == tests.length - 1) {

            tests = Arrays.copyOf(tests, tests.length * 2);

        }

    }
    
    public boolean isTestAlreadyExists(String testName) {
    
        for (int i = 0; i != testsTop + 1; i++) {
        
            if (tests[i].getName().equals(testName)) {
            
                return true;
            
            }
        
        }
    
        return false;
        
    }

    public User getNext() {

        return next;

    }

    public void setNext(User newNext) {

        this.next = newNext;

    }

    public String getLastName() {

        return lastName;

    }

    public int getTestsTop() {

        return testsTop;

    }

    public Test[] getTests() {

        return tests;

    }

    /**
     * Используется для получения среднего значения среди всех result объектов test
     *
     * @return среднее значение среди всех result объектов test
     */
    public String getAverageResult() {

        if (testsTop != -1) {

            int value = 0;

            for (int index = 0; index != (testsTop + 1); index++) {

                value += tests[index].getResult();

            }

            return Integer.toString(value / (testsTop + 1));

        } else {

            return null;

        }

    }

}
