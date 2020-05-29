package AssociationAssets;

/**
 * Class Field represents a football field in which games take place.
 * It is defined by a name, a city and it's capacity.
 */
public class Field {

    //region Fields
    String name;
    String city;
    int capacity;
    //endregion

    public Field(String name, String city, int capacity) {
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }


    //region Setters & Getters
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    //endregion
}
