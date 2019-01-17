package pl.zochowski.userInteraction;

import pl.zochowski.exceptions.MyException;
import pl.zochowski.model.SortedCriterium;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class GettingInputFromUser {
    private static Scanner scanner = new Scanner(System.in);


    public static String getString(String message, String regex) {
        try {
            String line;
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            return line;
        } catch (Exception e) {
            throw new MyException("WRONG DATA FORMAT");
        }
    }

    public static int getInteger(String message, String regex) {
        String line = null;
        int choiceNumber = 0;
        try{
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            choiceNumber = Integer.parseInt(line);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return choiceNumber;
    }

    public static BigDecimal getBigDecimal(String message) {
        try {
            final String regex = "\\d+(.\\d{2})*";
            String line;
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            return new BigDecimal(line);
        } catch (Exception e) {
            throw new MyException("WRONG DATA FORMAT. DATA DOES NOT MATCH A NUMBER FORMAT");
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        GettingInputFromUser.scanner = scanner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GettingInputFromUser that = (GettingInputFromUser) o;
        return Objects.equals(scanner, that.scanner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scanner);
    }
}
