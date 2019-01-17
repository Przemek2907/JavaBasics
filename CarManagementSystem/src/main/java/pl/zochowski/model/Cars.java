package pl.zochowski.model;


import pl.zochowski.converters.CarsJsonConverter;
import pl.zochowski.userInteraction.GettingInputFromUser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Cars {

    private Set<Car> cars;

    public Cars(String jsonFilename) {
        this.cars = new CarsJsonConverter(jsonFilename)
                .fromJson()
                .orElseThrow(IllegalStateException::new)
                .getCars();
    }

    private Map<SortedCriterium, Boolean> getUsersChoiceToSort (){
        Map<SortedCriterium, Boolean> userChoice = new HashMap<>();

        System.out.print("POSSIBLE SEARCH CRITERIA: ");
        for (SortedCriterium option: SortedCriterium.values()){
            System.out.println(option);
        }
        String choice = GettingInputFromUser.getString("PLEASE TYPE IN THE SEARCH CRITERIA OF YOUR CHOICE:", "MODEL|PRICE|COLOR|MILEAGE|model|price|color|mileage").toUpperCase();
        String sortType = GettingInputFromUser.getString("WOULD YOU LIKE TO SORT ASCENDING OR DESCENDING", "ascending|descending|ASCENDING|DESCENDING");
        boolean sortTypeCheck = sortType.toUpperCase().equals("DESCENDING");
        userChoice.put(SortedCriterium.valueOf(choice), sortTypeCheck);
        return userChoice;
    }

    public List<Car> listOfCarsSortedByParameter() {
        Stream<Car> carStream = null;
        Map<SortedCriterium, Boolean> userSortChoice = getUsersChoiceToSort();
        SortedCriterium sortedCriterium = userSortChoice.keySet().stream().findFirst().get();
        Boolean descending = userSortChoice.get(sortedCriterium);

        switch (sortedCriterium) {
            case COLOR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
                break;
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
                break;
            case PRICE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
                break;
            default:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
        }

        List<Car> sortedCars = carStream.collect(Collectors.toList());

        if (descending) {
            Collections.reverse(sortedCars);
        }

        return sortedCars;
    }

    public List<Car> carsWithHighMileage(int mileage) {
        List<Car> carsAboveRequestedMileage = this.cars.stream()
                .filter(p -> p.getMileage() > mileage)
                .collect(Collectors.toList());

        return carsAboveRequestedMileage;
    }

    public Map<String, Long> carsWithGivenColor() {
        return this.cars.stream()
                .collect(Collectors.groupingBy(p -> p.getColor(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        c -> c.getKey().name(), c -> c.getValue(),
                        (e1, e2) -> e1,
                        () -> new HashMap<>()
                ));
    }


    public Map<String, Car> mostExpensiveCarModel() {
        return new TreeMap<>(cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().max(Comparator.comparing(Car::getPrice)).orElseThrow(IllegalStateException::new)
                ))
        );
    }

    public void statisticsMileAge() {
        IntSummaryStatistics iss =
                this.cars.stream().collect(Collectors.summarizingInt(Car::getMileage));
        System.out.println("THE AVERAGE MILEAGE OF THE CAR AMOUNTS TO: " + iss.getAverage());
        System.out.println("THE HIGHEST MILEAGE OF THE CAR AMOUNTS TO: " + iss.getMax());
        System.out.println("THE LOWEST MILEAGE OF THE CAR AMOUNTS TO: " + iss.getMin());
    }

    public void priceStats() {
        System.out.println("THE AVERAGE PRICE OF THE CAR AMOUNTS TO: " + priceAvg());
        System.out.println("THE HIGHEST PRICE OF THE CAR AMOUNTS TO: " + priceMaxCar());
        System.out.println("THE LOWEST PRICE OF THE CAR AMOUNTS TO: " + priceMinCar());
    }

    private BigDecimal priceMaxCar() {

        return cars
                .stream()
                .max(Comparator.comparing(Car::getPrice)).orElseThrow(IllegalStateException::new)
                .getPrice();
    }

    private BigDecimal priceMinCar() {

        return cars
                .stream()
                .min(Comparator.comparing(Car::getPrice)).orElseThrow(IllegalStateException::new)
                .getPrice();
    }

    private BigDecimal priceAvg() {
        BigDecimal totalPrice = cars.stream().map(Car::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice.divide(new BigDecimal(String.valueOf(cars.size())), 2, RoundingMode.CEILING);
    }

    public List<Car> carWithTheHighestPrice() {
        return this.cars.stream()
                .filter(car -> car.getPrice().compareTo(priceMaxCar()) == 0)
                .collect(Collectors.toList());
    }

    public List<Car> equipmentSorted() {

        return this.cars
                .stream()
                .map(c -> {
                    c.setComponents(c.getComponents().stream().sorted().collect(Collectors.toList()));
                    return c;
                })
                .collect(Collectors.toList());

    }

    public Map<String, List<Car>> carMapWithGivenComponent() {

        return cars
                .stream()
                .flatMap(c -> c.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity()/*c -> c*/,
                        c -> cars.stream().filter(car -> car.getComponents().contains(c)).collect(Collectors.toList())
                ));
    }

    public List<Car> carsWithinThePriceRange(BigDecimal downThreshold, BigDecimal highThreshold) {
        return this.cars.stream()
                .filter(car -> car.getPrice().compareTo(downThreshold) > 0)
                .filter(car -> car.getPrice().compareTo(highThreshold) < 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cars
                .stream()
                .map(c -> c.getModel() + " costs " + c.getPrice() + " USD and has covered " + c.getMileage() + " miles. It has " + c.getColor() + " body color and following components " + c.getComponents())
                .collect(Collectors.joining("\n"));
    }

}