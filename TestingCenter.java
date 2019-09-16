package com.akaleaf.course;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 *     Список изменений
 *
 *
 */

/** Центр тестирования */
public class TestingCenter {

    private User head;

    public User getHead() {

        return head;

    }

    /**
     * Добавляет нового пользователя в конец списка или после пользователя с
     * соответствующим lastName, если таковой задан.
     *
     * @param newUser добавляемый пользователь в список
     * @param lastNameAfterWhich lastName пользователя, после которого необходимо
     *                           добавить newUser
     * @return результат выполнения работы: true - успешно
     *                                      false - не успешно
     * @see User
     */
    public boolean addUser(User newUser, String lastNameAfterWhich) {

        if (head == null) {

            head = newUser;

            return true;

        } else {

            if (!hasAlreadyExists(newUser.getLastName())) {

                User helpUser = head;

                if (lastNameAfterWhich.equals("")) {

                    while (helpUser.getNext() != null) {

                        helpUser = helpUser.getNext();

                    }

                    helpUser.setNext(newUser);

                    return true;

                } else {

                    User workUser = findMe(lastNameAfterWhich);

                    if (workUser != null) {

                        newUser.setNext(workUser.getNext());
                        workUser.setNext(newUser);

                        return true;

                    }

                    return false;

                }

            } else {

                return false;

            }

        }

    }

    /**
     * Метод определяет есть ли уже в системе пользователь с таким именем.
     *
     * @param lastName имя пользователя
     * @return если пользователь с именем lastName есть - true,
     *         если пользователя с именем lastName нет - false.
     */
    public boolean hasAlreadyExists(String lastName) {

        return (findMe(lastName) != null);

    }

    /**
     * Удаляет пользователя removableUser из списка
     *
     * @param lastName Используется для нахождения пользователя
     *                 с соответствующим lastName
     * @return результат выполнения работы: true - успешно
     *                                      false - не успешно.
     * @see User
     */
    public boolean removeUser(String lastName) {

        User helpUser = head;

        if (findMe(lastName) != null) {

            if (lastName.equals(head.getLastName())) {

                head = head.getNext();
                return true;

            }

            while ((helpUser.getNext() != null) && !(helpUser.getNext().getLastName().equals(lastName))) {

                helpUser = helpUser.getNext();

            }

            if (helpUser.getNext().getNext() != null) {

                helpUser.setNext(helpUser.getNext().getNext());
                return true;

            } else {

                helpUser.setNext(null);
                return true;

            }

        } else {

            return false;

        }

    }
    
    /**
     * Служит для получения количества пользователей в системе
     * 
     * @return количество пользователей в системе
     */
    public int getUsersCount() {

        User helpUser = head;
        int usersCount = 0;

        while (helpUser != null) {
            
            helpUser = helpUser.getNext();
            usersCount++;

        }

        return usersCount;

    }

    /**
     * Создаёт или перезаписывает уже созданный файл с именем <fileName>.course,
     * в котором записывается текущее состояние системы в формате, напоминающем
     * XML. Создание(или перезапись) файла ведётся в папку проекта.
     *
     * Более подробно о формате содержимого файла см. документацию loadFromFile()
     *
     * @param fileName используется для имени файла,
     *                 конечный вариант файла выглядит как <fileName>.course
     */
    public void saveToFile(String fileName) {

        /*
        Имя файла
         */
        String fName = fileName + ".course";

        try {

            FileWriter file = new FileWriter(fName);
            String indent = "    ";
            User helpUser = head;
            file.write("<testingCenter>");
            file.append("\n");

            /*
            Ничего неочевидного в записи файла
             */
            while (helpUser != null) {

                file.write(indent + "<user>");
                file.append("\n");

                file.write(indent + indent + "<lastName>");
                file.append("\n");

                file.write(indent + indent + indent + helpUser.getLastName());
                file.append("\n");

                file.write(indent + indent + "</lastName>");
                file.append("\n");

                file.write(indent + indent + "<testsTop>");
                file.append("\n");

                file.write(indent + indent + indent + helpUser.getTestsTop());
                file.append("\n");

                file.write(indent + indent + "</testsTop>");
                file.append("\n");

                if (helpUser.getTestsTop() != -1) {

                    file.write(indent + indent + "<tests>");
                    file.append("\n");

                }

                for (int i = 0; i != helpUser.getTestsTop() + 1; i++) {

                    file.write(indent + indent + indent + "<test>");
                    file.append("\n");

                    file.write(indent + indent + indent + indent + "<name>");
                    file.append("\n");

                    file.write(indent + indent + indent + indent + indent + helpUser.getTests()[i].getName());
                    file.append("\n");

                    file.write(indent + indent + indent + indent + "</name>");
                    file.append("\n");

                    file.write(indent + indent + indent + indent + "<result>");
                    file.append("\n");

                    file.write(indent + indent + indent + indent + indent + helpUser.getTests()[i].getResult());
                    file.append("\n");

                    file.write(indent + indent + indent + indent + "</result>");
                    file.append("\n");

                    file.write(indent + indent + indent + "</test>");
                    file.append("\n");

                }

                if (helpUser.getTestsTop() != -1) {

                    file.write(indent + indent + "</tests>");
                    file.append("\n");

                }
                file.write(indent + "</user>");
                file.append("\n");



                helpUser = helpUser.getNext();

            }

            file.write("</testingCenter>");

            file.flush();
            file.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    /**
     * Метод загружает ранее сохранённую или вручную созданную систему в существующую систему.
     *
     * Система из файла может быть соединена с существующей системой или
     * система из файла перезапишет существующую систему.
     *
     * Файл, из которого считывается ранее сохранённая система должен быть формата ".course"
     * и иметь строгую структуру, напоминающая структуру файлов XML.
     *
     * Метод работает проверкой тегов в файле:
     *
     * Теги 0 уровня: testingCenter;
     * Теги 1 уровня: user;
     * Теги 2 уровня: lastName, testsCount, testsTop, tests;
     * Теги 3 уровня: test;
     * Теги 4 уровня: name, result.
     *
     * Тег testingCenter - открывающий тег не обязателен, закрывающий тег
     *   обязателен - используется для определения конца сканирования(записи).
     * Тег user используется для определения нового пользователя.
     * Теги lastName, testsCount, testsTop, tests, относятся к тегу user и могут быть
     *   только внутри тега user. Используются для опредления строк со свойствами
     *   пользователя.
     * Тег test относится к тегу tests. Используется для определения добавления
     *   нового теста к пользователю.
     * Теги name, result относятся к тегу test. Используются для определения строк
     *   со свойствами теста.
     *
     * Файл сканируется только до первого закрывающего тега testingCenter, все дальнейшие
     * строки файла игнорируются и будут преданы забвению.
     *
     * При дальнейшем разборе кода нижеописанного метода рекомендуется держать
     * под рукой <fileName>.course или какой-либо иной файл .course формата.
     *
     * @param fName    имя файла без приписки формата,
     *                 в конечном варианте имя файла
     *                 будет <fileName>.course
     * @param merge    Объединить уже с существующей
     *                 системой или нет
     *                 (true - объединить)
     *                 (false - не объединять, создаётся
     *                 новая система, старая просто отбрасывается)
     */
    public void loadFromFile(String fName, boolean merge) {

        /*
        Отброс старой системы в случае merge == false.
         */
        if (!merge) {

            head = null;

        }

        /*
        Создание объекта для работы с файлом.
         */
        try {

            FileReader file = new FileReader(fName);
            FileReader file1 = new FileReader(fName);
            /*
            Следующие 2 объявления и цикл используются для счёта количества значащих строк в файле.
            Далее информация передаётся при инициализации служебного массива fileArray.
             */
            Scanner scanForCount = new Scanner(file1);
            int countOfStrokes = 1;

            while (scanForCount.hasNextLine() && !(scanForCount.nextLine().equals("</testingCenter>"))) {

                countOfStrokes++;

            }

            Scanner scan = new Scanner(file);
            int index = 0;
            /*
            Служебный массив - используется для более удобной работы с содержимым файла.
             */
            String fileArray[] = new String[countOfStrokes];

            /*
            Считывание строк файла в служебный массив fileArray.
             */
            while (scan.hasNextLine()) {

                /*
                При первой встрече </testingCenter> - остановка сканирования файла.
                 */
                fileArray[index] = scan.nextLine();
                if ((index != 0) && (fileArray[index - 1].equals("</testingCenter>"))) {

                    break;

                }

                index++;

            }

            /*
            Сокращение служебного массива до значащих элементов:
            убираются null элементы массива, начиная с конца до появления первого не null элемента.
             */
            fileArray = Arrays.copyOf(fileArray, index);

            int i = 0;
            String indent = "    ";

            /*
            Цикл прохода по каждому элементу служебного массива (элементы - ранее строки в файле).
             */
            while (fileArray.length != i) {

                /*
                Если в служебном массиве встречается тег 1 уровня user.
                 */
                if (fileArray[i].equals(indent + "<user>")) {

                    String lastName = "";
                    int testsCount = 0;
                    int i1 = i;

                    /*
                    Создание и добавление нового пользователя в систему.
                     */
                    User newUser = new User(lastName, 10);
                    addUser(newUser, "");

                    /*
                    Просмотр содержимого <user>..</user>.
                     */
                    while (!(fileArray[i1].equals(indent + "</user>"))) {

                        /*
                        При встрече тега lastName.
                         */
                        if (fileArray[i1].equals(indent + indent + "<lastName>")) {

                            int ind = 12;
                            String newLastName = "";

                            while (ind != fileArray[i1 + 1].length()) {

                                newLastName += fileArray[i1 + 1].charAt(ind);
                                ind++;

                            }

                            /*
                            Изменение свойства lastName ранее добавленного пользователя на
                            содержимое <lastName>..</lastName>.
                             */
                            findMe(lastName).setLastName(newLastName);
                            lastName = newLastName;

                        }

                        /*
                        При встрече тега tests.
                         */
                        if (fileArray[i1].equals(indent + indent + "<tests>")) {

                            int i2 = i1;

                            /*
                            Просмотр содержимого <tests>..</tests>.
                             */
                            while (!(fileArray[i2].equals(indent + indent + "</tests>"))) {

                                /*
                                При встрече тега test.
                                 */
                                if (fileArray[i2].equals(indent + indent + indent + "<test>")) {

                                    int i3 = i2;
                                    /*
                                    Добавление теста к ранее добавленному пользователю.
                                     */
                                    findMe(lastName).addTest("", 0);
                                    String testName = "";

                                    /*
                                    Просмотр содержимого <test>..</tests>.
                                     */
                                    while (!(fileArray[i3].equals(indent + indent + indent + "</test>"))) {

                                        /*
                                        При встрече тега name.
                                         */
                                        if (fileArray[i3].equals(indent + indent + indent + indent + "<name>")) {

                                            int ind = 20;
                                            String newName = "";

                                            while (ind != fileArray[i3 + 1].length()) {

                                                newName += fileArray[i3 + 1].charAt(ind);
                                                ind++;

                                            }

                                            /*
                                            Изменение свойства name ранее добавленного теста на
                                            содержимое <name>..</name>.
                                             */
                                            findMe(lastName).setTestName("", newName);
                                            testName = newName;

                                        }

                                        /*
                                        При встрече тега result.
                                         */
                                        if (fileArray[i3].equals(indent + indent + indent + indent + "<result>")) {

                                            int ind = 20;
                                            String newResult = "";

                                            while (ind != fileArray[i3 + 1].length()) {

                                                newResult += fileArray[i3 + 1].charAt(ind);
                                                ind++;

                                            }

                                            /*
                                            Изменение свойства result ранее добавленного теста на
                                            содержимое <result>..</result>.
                                             */
                                            findMe(lastName).setTestResult(testName, Integer.valueOf(newResult));

                                        }

                                        i3++;

                                    }

                                }

                                i2++;

                            }

                        }

                        i1++;

                    }

                }

                i++;

            }

            /*
            Приехали.
            Одинокий catch.
             */
        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /**
     * Находит пользователя посредством сравнения lastName
     *
     * @param lastName Использвуется для нахождения пользователя с соответсвующим lastName
     * @return пользователя с соответствующим lastName или null в случае ненахождения
     *         пользователя с соотвутствующим lastName
     * @see User
     */
    public User findMe(String lastName) {

        /*
        Вспомогательная переменная
         */
        User helpUser = head;

        if (helpUser != null) {

            while ((helpUser.getNext() != null) && !(helpUser.getLastName().equals(lastName))) {

                helpUser = helpUser.getNext();

            }

            if (helpUser.getLastName().equals(lastName)) {

                return helpUser;

            } else {

                return null;

            }

        } else {

            return null;

        }

    }
    
    /**
     * Находит пользователя посредством сравнения lastName и возвращает его порядковый номер начинающийся с 1
     *
     * @param index Какой по счёту
     * @return пользователя с соответствующим lastName или null в случае ненахождения
     *         пользователя с соотвутствующим lastName
     * @see User
     */
    public User findMe(int index) {
         
        int iter = 1;
        User helpUser = head;
         
        while (iter != index) {
             
            helpUser = helpUser.getNext();    
            iter++;
             
        }
         
        return helpUser;
         
         
    }

    /**
     * Метод отрезает кусок конца строки соответствующий cut, если таковой имеется, от cutting.
     * К примеру cutMe("guy.course", ".course") вернёт "guy".
     *
     * В случае если cut в конце строки cutting не был найден, возвращается неизменённый cutting.
     * К примеру cutMe("guy.course", ".txt") вернёт "guy.course".
     *
     * @param cutting Строка, от которой нужно отрезать cut
     * @param cut Строка, которую нужно отрезать с конца cutting
     * @return cutting
     */
    public String cutMe(String cutting, String cut) {

        if (cutting.length() > cut.length() + 1) {

            String cuttable = "";

            for (int i = cutting.length() - cut.length(); i != cutting.length(); i++) {

                cuttable += cutting.charAt(i);

            }

            String newFileName = "";

            if (cuttable.equals(cut)) {

                for (int i = 0; i != cutting.length() - cut.length(); i++) {

                    newFileName += cutting.charAt(i);

                }

                cutting = newFileName;

            }

        }

        return cutting;

    }

}