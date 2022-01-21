package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.dodo.Menus.MainMenu;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.TakeOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainMenuTest {
    private Store store;
    private String inputData;
    private PrintStream defaultOutStream;
    private ByteArrayOutputStream outStream;
    private ByteArrayInputStream bais;

    @BeforeEach
    public void beforeEachTest() {
        outStream = new ByteArrayOutputStream();
        defaultOutStream = System.out;
        System.setOut(new PrintStream(outStream));
        store = TestData.getTestStore();
    }

    @AfterEach
    public void afterEachTest() {
        System.setOut(defaultOutStream);
        TakeOrder.setCustomer(null);
    }

    private String getConsoleOutput() {
        return outStream.toString();
    }

    @Test
    public void testMainMenuInvalidInput() {
        inputData = "2\nb";
        MainMenu.mainMenu(inputData, store);
        assertTrue(getConsoleOutput().contains("Invalid input."));
    }

    @Test
    public void shouldSetCustomertoTomas() {
        inputData = "1\nToMAs\nADd 2\nABORT\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.mainMenu("1", store);
        assertEquals("Tomas Brolin", TakeOrder.getCustomer().getName());
    }

    @Test
    public void shouldSetCustomerToMartin() {
        inputData = "1\nMartin\nadd 2\nABORT\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.mainMenu("1", store);
        assertEquals("Martin Dahlin", TakeOrder.getCustomer().getName());
    }

    @Test
    public void shouldFailToSetCustomer() {
        inputData = "1\nasasasassa\nABORT\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.mainMenu("1", store);
        assertEquals(null, TakeOrder.getCustomer());
        assertTrue(getConsoleOutput().contains("No customer with that name."));
    }

    @Test
    public void ShouldEnterCustMenuAndReturn() {
        inputData = "b";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.mainMenu("3", store);
    }

    @Test
    public void ShouldEnterProdMenuAndReturn() {
        inputData = "b";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.mainMenu("2", store);

    }

    @Test
    public void ShouldEnterLoopAndQuit() {
        inputData = "e";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        MainMenu.startLoop();
        assertTrue(getConsoleOutput().contains("Bye"));
    }
}
