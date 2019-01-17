package pl.zochowski;

import pl.zochowski.exceptions.MyException;
import pl.zochowski.model.Car;
import pl.zochowski.model.Cars;
import pl.zochowski.userInteraction.GettingInputFromUser;
import pl.zochowski.userInteraction.UserSteeringApp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        userInteractionWithTheApp();

    }

    public static void userInteractionWithTheApp () {
        boolean exit = false;
        System.out.println("WELCOME IN THE CAR MANAGEMENT APP");
        System.out.println("THE DATA FROM THE JSON FILE HAS BEEN LOADED");
        do {
            UserSteeringApp userSteeringApp = new UserSteeringApp();
            userSteeringApp.userInteractionWithTheApp();
            String choiceToContinueAppRunning = GettingInputFromUser.getString("DO YOU WANT TO CLOSE THE APP? (Y/N)", "(Y|N|n|y)");
            exit = choiceToContinueAppRunning.toUpperCase().equals("Y");
        } while (!exit);
    }
}
