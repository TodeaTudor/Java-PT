package model;

/**
 * A client has its name as a unique identifier and its city as attribute
 */
public class Client {
    private String name;
    private String city;
    private int deleted;

    public Client(String name, String city) {
        this.name = name;
        this.city = city;
        this.deleted = 0;
    }
    public Client() {

        this.deleted = 0;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getDeleted() {
        return deleted;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
