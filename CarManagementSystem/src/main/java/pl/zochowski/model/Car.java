package pl.zochowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


public class Car {
    private String model;
    private BigDecimal price;
    private Color color;
    private int mileage;
    private List<String> components;


    public Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.price = carBuilder.price;
        this.color = carBuilder.color;
        this.mileage = carBuilder.mileage;
        this.components = carBuilder.components;
    }


    public static CarBuilder builder() {
        return new CarBuilder();
    }


    public static class CarBuilder {
        private String model;
        private BigDecimal price;
        private Color color;
        private int mileage;
        private List<String> components;

        public CarBuilder model(String model) {
            try {
                if (model == null) {
                    throw new NullPointerException("MODEL OF THE CAR HAS NOT BEEN PROVIDED");
                } else if (!model.matches("([A-Z]+(\\s)*)+")) {
                    throw new IllegalArgumentException("MODEL OF THE CAR CANNOT CONSIST OF THE CHARACTERS PROVIDED");
                }
                this.model = model;
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
            return this;
        }

        public CarBuilder price(BigDecimal price) {
            try {
                if (price == null) {
                    throw new NullPointerException("PRICE OF THE CAR HAS NOT BEEN PROVIDED");
                } else if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("PRICE OF THE CAR HAS TO BE GREATER THAN ZERO");
                }
                this.price = price;
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
            return this;
        }

        public CarBuilder color(Color color) {
            try {
                if (color == null) {
                    throw new NullPointerException("COLOR OF THE CAR HAS NOT BEEN PROVIDED");
                } else if (color.name().equals(Color.values())) {
                    throw new IllegalArgumentException("THERE IS NO SUCH COLOR ON THE LIST");
                }
                this.color = color;
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
            return this;
        }

        public CarBuilder mileage(int mileage) {
            try {
                if (mileage < 0) {
                    throw new IllegalArgumentException("MILEGAE OF THE CAR CANNOT BE LESS THAN ZERO");
                }
                this.mileage = mileage;
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
            return this;
        }

        public CarBuilder components(List<String> components) {
            try {
                if (components == null) {
                    throw new NullPointerException("THE CAR HAS TO HAVE COMPONENTS");
                } else if (!components.stream().anyMatch(s -> s.matches("([A-Z]+(\\s)*)+"))) {
                    throw new IllegalArgumentException("INVALID CHARACTERS FOUND WHEN TRYING TO CREATE THE LIST OF COMPONENTS FOR THE CAR");
                }
                this.components = components;
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }

    @Override
    public String toString() {
        return "A "+ color + " " + model + " car costs "
                + price + " USD, has " + mileage + " km already and has the following components: "
                + components + "\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return mileage == car.mileage &&
                Objects.equals(model, car.model) &&
                Objects.equals(price, car.price) &&
                color == car.color &&
                Objects.equals(components, car.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, color, mileage, components);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }


}
