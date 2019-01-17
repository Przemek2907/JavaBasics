package pl.zochowski.converters;

import pl.zochowski.model.CarsJson;

public class CarsJsonConverter extends JsonConverter<CarsJson> {
    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
