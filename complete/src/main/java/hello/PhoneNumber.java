package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumber {
    private final int countryCode;
    private final int cityCode;
    private final int number;

    @JsonCreator
    public PhoneNumber(@JsonProperty("countryCode") int countryCode, @JsonProperty("cityCode") int cityCode, @JsonProperty("number") int number) {

        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.number = number;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public int getCityCode() {
        return cityCode;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "countryCode=" + countryCode +
                ", cityCode=" + cityCode +
                ", number=" + number +
                '}';
    }
}
