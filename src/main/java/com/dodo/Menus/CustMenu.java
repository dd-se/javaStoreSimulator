package com.dodo.Menus;

import java.util.List;
import com.dodo.App;
import com.dodo.Exceptions.CustomerAddressException;
import com.dodo.Exceptions.CustomerEmailException;
import com.dodo.Exceptions.CustomerNameException;
import com.dodo.Models.Customer;
import com.dodo.Models.CustomerHelper;
import com.dodo.Models.Order;
import com.dodo.StoreLogic.Generators;
import com.dodo.Utils.Colors;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.PrintUtils;

public class CustMenu {

    public final static String CUST_MENU = Colors.GREEN +
            "-[CUSTOMER UTILS]-\n" + Colors.RESET + "[1] Add a customer\n"
            + "[2] List customers by total expenditure\nChoose: ";

    public static void customerMenu(List<Customer> storeCustomers, List<Order> storeOrders) {

        System.out.print(CUST_MENU);
        String command = App.scanner.nextLine();

        switch (command) {
            case "1":
                try {
                    System.out.print("Customer name: ");
                    String customerName = App.scanner.nextLine();

                    System.out.print("Customer address: ");
                    String customerAddress = App.scanner.nextLine();

                    System.out.print("Customer email: ");
                    String customerEmail = App.scanner.nextLine();

                    Customer aCustomer = Generators.generateCustomer(customerName, customerAddress, customerEmail,
                            storeCustomers.size() + 1);
                    storeCustomers.add(aCustomer);

                    System.out.println(Colors.YELLOW + aCustomer + " added." + Colors.RESET);
                } catch (CustomerNameException e) {
                    System.out.println(e.getMessage());
                } catch (CustomerAddressException e) {
                    System.out.println(e.getMessage());
                } catch (CustomerEmailException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                List<CustomerHelper> customersTotalExpenditureList = ListUtils
                        .getCustomersTotalExpenditureList(storeCustomers, storeOrders);
                ListUtils.sortCustomersTotalExpenditureList(customersTotalExpenditureList);
                PrintUtils.printCustomersTotalExpenditureList(customersTotalExpenditureList);
                break;
            case "b":
                return;
            default:
                System.out.println(App.INVALID_INPUT);
                break;
        }
        customerMenu(storeCustomers, storeOrders);
    }
}
