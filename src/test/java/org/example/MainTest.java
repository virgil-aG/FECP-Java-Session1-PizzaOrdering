package org.example;

import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    private ArrayList<String> pizzas;
    private ArrayList<Integer> quantities;
    private PrintWriter writer;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    void setupCSV() throws IOException {
        writer = new PrintWriter(new FileWriter("test-results.csv", false));
        writer.println("Test Name,Result,Timestamp");
    }

    @AfterAll
    void closeCSV() {
        writer.close();
    }

    @BeforeEach
    void setUp() {
        pizzas = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    void log(String testName, ExecutableTest executable) {
        String timestamp = LocalDateTime.now().format(formatter);
        try {
            executable.run();
            writer.println(testName + ",PASS," + timestamp);
        } catch (AssertionError | Exception e) {
            writer.println(testName + ",FAIL," + timestamp);
            Assertions.fail("Test '" + testName + "' failed: " + e.getMessage());
        }
    }


    @Test void testAddOrderValidQuantity() {
        log("testAddOrderValidQuantity", () -> {
            Main.addOrder(pizzas, quantities, "Pepperoni", 2);
            assertEquals(1, pizzas.size());
            assertEquals("Pepperoni", pizzas.get(0));
            assertEquals(2, quantities.get(0));
        });
    }

    @Test void testAddOrderInvalidQuantityZero() {
        log("testAddOrderInvalidQuantityZero", () -> {
            Main.addOrder(pizzas, quantities, "Cheese", 0);
            assertTrue(pizzas.isEmpty());
            assertTrue(quantities.isEmpty());
        });
    }

    @Test void testAddOrderInvalidQuantityNegative() {
        log("testAddOrderInvalidQuantityNegative", () -> {
            Main.addOrder(pizzas, quantities, "Cheese", -3);
            assertTrue(pizzas.isEmpty());
            assertTrue(quantities.isEmpty());
        });
    }

    @Test void testUpdateOrderValidIndex() {
        log("testUpdateOrderValidIndex", () -> {
            pizzas.add("Hawaiian");
            quantities.add(3);
            Main.updateOrder(quantities, 0, 5);
            assertEquals(5, quantities.get(0));
        });
    }

    @Test void testUpdateOrderInvalidIndex() {
        log("testUpdateOrderInvalidIndex", () -> {
            Main.updateOrder(quantities, 0, 4);
            assertTrue(quantities.isEmpty());
        });
    }

    @Test void testUpdateOrderInvalidQuantityZero() {
        log("testUpdateOrderInvalidQuantityZero", () -> {
            pizzas.add("Meat Lovers");
            quantities.add(4);
            Main.updateOrder(quantities, 0, 0);
            assertEquals(4, quantities.get(0));
        });
    }

    @Test void testUpdateOrderInvalidQuantityNegative() {
        log("testUpdateOrderInvalidQuantityNegative", () -> {
            pizzas.add("Veggie");
            quantities.add(4);
            Main.updateOrder(quantities, 0, -2);
            assertEquals(4, quantities.get(0));
        });
    }

    @Test void testRemoveOrderValidIndex() {
        log("testRemoveOrderValidIndex", () -> {
            pizzas.add("Supreme");
            quantities.add(2);
            Main.removeOrder(pizzas, quantities, 0);
            assertTrue(pizzas.isEmpty());
            assertTrue(quantities.isEmpty());
        });
    }

    @Test void testRemoveOrderInvalidIndex() {
        log("testRemoveOrderInvalidIndex", () -> {
            Main.removeOrder(pizzas, quantities, 1);
            assertTrue(pizzas.isEmpty());
            assertTrue(quantities.isEmpty());
        });
    }

    @Test void testMultipleOrdersManagement() {
        log("testMultipleOrdersManagement", () -> {
            Main.addOrder(pizzas, quantities, "Pepperoni", 2);
            Main.addOrder(pizzas, quantities, "Cheese", 3);
            assertEquals(2, pizzas.size());

            Main.updateOrder(quantities, 1, 5);
            assertEquals(5, quantities.get(1));

            Main.removeOrder(pizzas, quantities, 0);
            assertEquals("Cheese", pizzas.get(0));
        });
    }

    // Custom functional interface for wrapping test logic
    @FunctionalInterface
    interface ExecutableTest {
        void run() throws Exception;
    }
}
