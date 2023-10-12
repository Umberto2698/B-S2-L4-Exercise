package org.example;

import com.github.javafaker.Faker;
import enteties.Customer;
import enteties.Order;
import enteties.Product;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws InterruptedException {
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

        System.err.println("Lista prodotti:");
        productsList.forEach(System.out::println);

        TimeUnit.MILLISECONDS.sleep(1500);

        List<Order> orderList = new ArrayList<>();
        for (Customer customer : customerList) {
            List<Product> orderProductList = new ArrayList<>();
            int randomNumberOfOrder=new Random().nextInt(1,4);
            int counter=0;
            do {
                int rnd=new Random().nextInt(1,10);
                for (int j = 0; j < rnd; j++) {
                    int n = (int) Math.floor(Math.random() * 40);
                    orderProductList.add(productsList.get(n));
                }
                counter++;
                orderList.add(new Order(orderProductList, customer));
            }while (counter<randomNumberOfOrder);
        }

        System.err.println("Lista ordini:");
        orderList.forEach(System.out::println);

        TimeUnit.MILLISECONDS.sleep(1500);
        //*********************************************** Esercizio 1 *******************************************
        System.err.println("Numero 1");

        Map<Customer, List<Order>> customerOrders = orderList.stream().collect(Collectors.groupingBy(Order::getCustomer));
        customerOrders.forEach((customer, orders)-> System.out.println("Customer "+ customer.getName()+ ", orders:"+ orders));

        TimeUnit.MILLISECONDS.sleep(1500);
        //*********************************************** Esercizio 2 *******************************************
        System.err.println("Numero 2");
        Map<Customer, Double> customerTotalExpense = orderList.stream().collect(Collectors.groupingBy(Order::getCustomer,
                Collectors.summingDouble(order-> order.getProducts().stream().mapToDouble(Product::getPrice).sum()*100.0/100.0
        )));
        customerTotalExpense.forEach((customer, total)-> System.out.println("Customer "+ customer.getName()+ ", total expense:"+ total));

        TimeUnit.MILLISECONDS.sleep(1500);
        //*********************************************** Esercizio 3 *******************************************
        System.err.println("Numero 3");
        List<Product> sortedProductList= productsList.stream().sorted(Comparator.comparing(Product::getPrice,Comparator.reverseOrder())).toList();
        Product productWithHighestPrice=sortedProductList.get(0);
        List<Product> highestValueProductList= sortedProductList.stream().filter(product -> product.getPrice()==productWithHighestPrice.getPrice()).toList();
        highestValueProductList.forEach(System.out::println);

        TimeUnit.MILLISECONDS.sleep(1500);
        //*********************************************** Esercizio 4 *******************************************
        System.err.println("Numero 4");
        List<Double> averegeOrderExpense=
                orderList.stream().map(Order::getProducts).toList().stream()
                        .map(listProducts ->listProducts.stream().collect(Collectors.averagingDouble(Product::getPrice))*100.0/100.0).toList();
        averegeOrderExpense.forEach(System.out::println);
    }
}
