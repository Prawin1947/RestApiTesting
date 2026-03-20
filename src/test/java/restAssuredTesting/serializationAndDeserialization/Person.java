package restAssuredTesting.serializationAndDeserialization;

public class Person {
    private String firstName;
    private String lastName;
    private int age;

    // Default constructor is often required for deserialization
    public Person() {
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Getters are used by ObjectMapper during serialization
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
