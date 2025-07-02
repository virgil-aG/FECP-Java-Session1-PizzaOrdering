package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int orderChoice = 0;
        ArrayList<String> pizzas = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();

        System.out.println("---Pizza Delivery---");
        System.out.println("1. Add Order");
        System.out.println("2. Update Order");
        System.out.println("3. Remove Order");
        System.out.println("4. View Orders");
        System.out.println("5. Exit");
        while(orderChoice!=5) {
            System.out.print("Choose option: ");
            orderChoice = scanner.nextInt();
            if (orderChoice == 1) {
                System.out.print("Pizza Type: ");
                String pizzaType = scanner.next();
                System.out.print("Quantity: ");
                int quantity = scanner.nextInt();
                while (quantity <= 0) {
                    System.out.println("Invalid Input. Please input valid quantity.");
                    System.out.print("Quantity: ");
                    quantity = scanner.nextInt();
                }
                addOrder(pizzas, quantities, pizzaType, quantity);
                System.out.println();
            } else if (orderChoice == 2){
                System.out.print("Order number to update: ");
                int index = scanner.nextInt();
                System.out.print("New quantity: ");
                int newQuantity = scanner.nextInt();
                if (newQuantity < 0) {
                    System.out.println("Invalid Input. Please input valid quantity.");
                } else {
                    updateOrder(quantities, index-1, newQuantity);
                }
                System.out.println();
            } else if (orderChoice == 3) {
                System.out.print("Order number to remove: ");
                int removeIndex = scanner.nextInt();
                if ((removeIndex <= 0) || (removeIndex > pizzas.size())){
                    System.out.println("Invalid Order Number.");
                } else {
                    removeOrder(pizzas, quantities, removeIndex-1);
                }
                System.out.println();
            } else if (orderChoice == 4) {
                System.out.println("---Current Orders---");
                printOrders(pizzas, quantities);
                System.out.println();
            } else if (orderChoice == 5) {
                System.out.println("---Thank you!---");
                break;
            } else {
                System.out.println("Invalid Input.");
                break;
            }
        }
    }

    public static void addOrder(ArrayList<String> pizzas, ArrayList<Integer> quantities, String pizzaType, int quantity) {
        if(quantity > 0) {
            pizzas.add(pizzaType);
            quantities.add(quantity);
        }
    }

    public static void updateOrder(ArrayList<Integer> quantities, int index, int newQuantity) {
        if(!quantities.isEmpty() && (newQuantity > 0)) {
            quantities.set(index, newQuantity);
        }
    }

    public static void removeOrder(ArrayList<String> pizzas, ArrayList<Integer> quantities, int index) {
        if(!pizzas.isEmpty()) {
            pizzas.remove(index);
        }
        if(!quantities.isEmpty()) {
            quantities.remove(index);
        }
    }

    public static void printOrders(ArrayList<String> pizzas, ArrayList<Integer> quantities) {
        for(int i = 0; i < pizzas.size(); i++){
            System.out.print(i+1);
            System.out.print(". ".concat(pizzas.get(i)).concat(" x "));
            System.out.println(quantities.get(i));
        }
    }

}