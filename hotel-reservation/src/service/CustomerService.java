package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static CustomerService instance = null;

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Customer already exists");
        }

        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}