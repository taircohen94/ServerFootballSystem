package AcceptanceTests.DataObjects;

public class FieldDetails {

    String name;
    String city;
    String capacity;

    public FieldDetails(String name, String city, String capacity) {
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
