package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String fileName = "tasks.csv";
    static final String[] opcje = {"add", "remove", "list", "exit"};
    static String[][] taski = getStringsFromFile();

    public static void main(String[] args) {
        taski = getStringsFromFile();

        listTaski();
        wyswietlOpcje();
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            switch (input) {

                case "add":
                    addTask(scan);
                    break;
                case "list":
                    listTaski();
                    break;
                case "remove":
                    removeTask(scan);
                    break;
                case "exit":
                    saveToFile();
                    System.out.println(ConsoleColors.RED+"Thank you for using this program!");
                    System.out.println(ConsoleColors.RESET);
                    System.exit(0);
                default:
                    System.out.println("Please select an option.");
            }
            wyswietlOpcje();
        }
        scan.close();

    }

    private static void saveToFile() {
        Path path = Paths.get(fileName);

        String[] outTasks = new String[taski.length];
        for (int i = 0; i < taski.length; i++) {
            outTasks[i] = String.join(", ", taski[i]);
        }
        try {
            Files.write(path, Arrays.asList(outTasks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeTask(Scanner scan) {

        System.out.println("Please select number to remove:");
        listTaski();
        try {
            String nrRemove = scan.nextLine();
            while (!NumberUtils.isParsable(nrRemove)) {
                System.out.println("Given value is not a number.");
                nrRemove = scan.nextLine();
            }
            while (Integer.parseInt(nrRemove) < 0) {
                System.out.println("number should be greater or equal 0.");
                nrRemove = scan.nextLine();
            }
            taski = ArrayUtils.remove(taski, Integer.parseInt(nrRemove));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Given index is out of bound of the list");
        } finally {
            listTaski();
        }
    }

    private static void checkIfNumberAndGreaterEqualZero(Scanner scan) {

    }


    private static void listTaski() {

        for (int i = 0; i < taski.length; i++) {
            System.out.printf(i + ": ");
            for (int j = 0; j < taski[i].length; j++) {
                System.out.printf(taski[i][j] + " ");
            }
            System.out.println();
        }
        ;
    }

    private static void addTask(Scanner scan) {
        taski = Arrays.copyOf(taski, taski.length + 1);
        taski[taski.length - 1] = new String[3];
        System.out.println("Please add task description");
        taski[taski.length - 1][0] = scan.nextLine();
        System.out.println("Please add task due date");
        taski[taski.length - 1][1] = scan.nextLine();
        System.out.println("Is your task important? : true/false");
        taski[taski.length - 1][2] = scan.nextLine();

//        System.out.println(taski.length);
//        System.out.println(taski[taski.length-1][0]+ taski[taski.length-1][1]+ taski[taski.length-1][2]);

    }

    private static String[][] getStringsFromFile() {
        Path path = Paths.get(fileName);
        String[][] taski = null;
        try {
            List<String> wierszePliku = Files.readAllLines(path);
            taski = new String[wierszePliku.size()][wierszePliku.get(0).split(", ").length];
            for (int i = 0; i < wierszePliku.size(); i++) {
                taski[i] = wierszePliku.get(i).split(", ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taski;
    }


    private static void wyswietlOpcje() {

        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (int i = 0; i < opcje.length; i++) {
            System.out.println(opcje[i]);
        }
        System.out.println();
    }
}
