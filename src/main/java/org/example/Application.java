package org.example;

import com.github.javafaker.Faker;
import enteties.Customer;
import enteties.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class Application {

    public static void main(String[] args) {
        Faker faker = new Faker(Locale.ITALY);

        Supplier<Product> bookSupplier = () -> new Product(faker.book().title(), "book");
        Supplier<Product> televisionSupplier = () -> new Product(faker.food().fruit(), "fruits");
        Supplier<Product> babyProductSupplier = () -> new Product(faker.pokemon().name()+ "peluche", "baby");
        Supplier<Product> boysProductSupplier = () -> new Product(faker.pokemon().name()+ "card", "boys");

        Supplier<Customer> tierOneCustomer = () -> new Customer(faker.name().name(), 1);
        Supplier<Customer> tierTwoCustomer = () -> new Customer(faker.name().name(), 2);
        Supplier<Customer> tierThreeCustomer = () -> new Customer(faker.name().name(), 3);


        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customerList.add(tierOneCustomer.get());
            customerList.add(tierTwoCustomer.get());
            customerList.add(tierThreeCustomer.get());
        }

        List<Product> productsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productsList.add(bookSupplier.get());
            productsList.add(televisionSupplier.get());
            productsList.add(babyProductSupplier.get());
            productsList.add(boysProductSupplier.get());
        }
    }
}
