package pl.zochowski.userInteraction;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import pl.zochowski.exceptions.MyException;
import pl.zochowski.model.Car;
import pl.zochowski.model.Cars;

import java.math.BigDecimal;
import java.util.*;

public class UserSteeringApp {

    private static final String choice1 = "1. SHOW ALL THE CARS LOADED FROM THE JSON FILE";
    private static final String choice2 = "2. SORT CARS ON THE LIST, BY THE CRITERIA OF MY SELECTION";
    private static final String choice3 = "3. SHOW ALL THE CARS ON THE LIST THAT HAVE THE MILEAGE HIGHER OR EQUAL TO THE ONE I PROVIDE";
    private static final String choice4= "4. SHOW THE MOST EXPENSIVE CAR OF EACH BRAND";
    private static final String choice5= "5. SHOW THE AVERAGE, MAX AND IN PRICE AND MILEAGE OF THE CARS COLLECTION";
    private static final String choice6= "6. SHOW THE CARS WITH ALL THE COMPONENTS SORTED ALPHABETICALLY";
    private static final String choice7= "7. SHOW ALL THE DISTINCT COMPONENT AND LIST THE CARS THAT HAVE THIS COMPONENT";
    private static final String choice8= "8. SHOW ALL THE CARS OF THE SELECTED PRIVE RANGE";
    private static final List<String> choiceList = new ArrayList<>(Arrays.asList(choice1,choice2,choice3, choice4,choice5,choice6,choice7,choice8));


    public void userInteractionWithTheApp (){
        boolean exit = false;
        Cars cars = new Cars("cars.json");
        System.out.println("PLEASE SELECT THE NUMBER ASSIGNED TO ONE OF THE OPTIONS ON THE LIST");
        choiceList.stream().forEach(System.out::println);
        int userChoice = GettingInputFromUser.getInteger("TYPE IN YOUR CHOICE", "\\d");
            try {
                switch (userChoice){
                    case 1:
                        System.out.println(cars);
                        break;
                    case 2:
                        List<Car> carsSortedBy = cars.listOfCarsSortedByParameter();
                        System.out.println(carsSortedBy);
                        break;
                    case 3:
                        List<Car> mileAgeCars = cars.carsWithHighMileage(GettingInputFromUser.getInteger("PLEASE PROVIDE THE MILEAGE THRESHOLD", "\\d+"));
                        System.out.println(mileAgeCars);
                        break;
                    case 4:
                        Map<String, Car> mapOfCars = cars.mostExpensiveCarModel();
                        mapOfCars.forEach((k,v) ->System.out.println( k + "-> " + v));
                        break;
                    case 5:
                        cars.statisticsMileAge();
                        cars.priceStats();
                        break;
                    case 6:
                        List<Car> sortedEquipment = cars.equipmentSorted();
                        System.out.println("LIST OF CARS WITH EQUIPMENT SORTED ALPHABETICALLY" + sortedEquipment);
                        break;
                    case 7:
                        Map<String, List<Car>> carsWithGivenEquipment = cars.carMapWithGivenComponent();
                        carsWithGivenEquipment.forEach((k,v) -> System.out.println("COMPONENT: " + k + " AND MODELS THAT HAVE THIS COMPONENT " + v));
                        break;
                    case 8:
                        List<Car> carsWithinThePriceRange = cars.carsWithinThePriceRange(GettingInputFromUser.getBigDecimal("PLEASE PROVIDE THE MINIMUM PRICE THRESHOLD"),
                                GettingInputFromUser.getBigDecimal("PLEASE PROVIDE THE MAXIMUM PRICE THRESHOLD"));
                        System.out.println(carsWithinThePriceRange);
                }
            } catch (MyException e) {
               e.getExceptionInfo();
            }
    }

}
