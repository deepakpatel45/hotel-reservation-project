package model;

public class Customer{

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)\\.com$";
        return email != null && email.matches(regex);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName +
                ", Email: " + email;
    }
}