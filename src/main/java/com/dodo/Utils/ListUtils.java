package com.dodo.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.dodo.Exceptions.CustomerNotFoundException;
import com.dodo.Exceptions.OrderNotFoundException;
import com.dodo.Models.Customer;
import com.dodo.Models.CustomerHelper;
import com.dodo.Models.Order;
import com.dodo.Models.Product;

public class ListUtils {

    public static void sortOrdersByNewestFirst(List<Order> collection) {
        Collections.sort(collection, Order.SORT_BY_NEWEST_FIRST);
    }

    public static void sortOrdersByOldestFirst(List<Order> collection) {
        Collections.sort(collection, Order.SORT_BY_OLDEST_FIRST);
    }

    public static void sortCustomersTotalExpenditureList(List<CustomerHelper> collection) {
        Collections.sort(collection);
        if (!collection.isEmpty()) {
            CustomerHelper customerTotalSpent = collection.get(0);
            customerTotalSpent.setName(customerTotalSpent.getName() + " (⌐■_■)");
        }
    }

    public static List<CustomerHelper> getCustomersTotalExpenditureList(List<Customer> customers,
            List<Order> orders) {

        List<CustomerHelper> customersTotalExpenditureList = new ArrayList<>();
        for (Customer customer : customers) {
            double totalSpent = getOrderTotalCost(customer.getCustId(), orders);
            customersTotalExpenditureList
                    .add(new CustomerHelper(customer.getCustId(), customer.getName(), totalSpent));
        }
        return customersTotalExpenditureList;
    }

    public static double getOrderTotalCost(int custId, List<Order> orders) {
        double totalOrderCost = 0;
        for (Order order : orders) {
            if (custId == order.getCustId()) {
                totalOrderCost += order.totalOrderCost();
            }
        }
        return totalOrderCost;
    }

    public static Object pickRandomFromCollection(List<?> collection) {
        Random random = new Random();
        Object picked = collection.get(random.nextInt(collection.size()));
        return picked;

    }

    public static Order findOrderById(List<Order> orderList, int orderId) throws OrderNotFoundException {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        throw new OrderNotFoundException("Order with that ID does not exist.");
    }

    public static Customer findCustByName(String custName, List<Customer> customers) throws CustomerNotFoundException {
        for (Customer customer : customers) {
            if (!custName.isEmpty() && customer.getName().toLowerCase().contains(custName.toLowerCase())) {
                return customer;
            }
        }
        throw new CustomerNotFoundException("No customer with that name.");
    }

    // Source: https://www.geeksforgeeks.org/how-to-sort-a-treemap-by-value-in-java/
    public static <K, V extends Comparable<V>> Map<K, V> sortMapByValue(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K keyword1, K keyword2) {
                int result = map.get(keyword2).compareTo(map.get(keyword1));
                if (result == 0)
                    return 1;
                return result;
            }
        };
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }

    public static String convertToTitleCase(String text) {
        if (text.isEmpty()) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] words = text.split("\\s+");
        for (String word : words) {
            char[] chars = word.toCharArray();
            stringBuilder.append(String.valueOf(chars[0]).toUpperCase());
            for (int i = 1; i < chars.length; i++) {
                stringBuilder.append(String.valueOf(chars[i]).toLowerCase());
            }
            if (words.length > 1)
                stringBuilder.append(" ");
        }
        return stringBuilder.toString().strip();

    }

    public static Map<String, Integer> getMostPopularProducts(List<Order> orders) {
        Map<String, Integer> soldProductsMap = new TreeMap<String, Integer>();
        for (Order order : orders) {
            for (Product product : order.getProductList()) {
                if (soldProductsMap.get(product.getProductName()) != null) {
                    soldProductsMap.put(product.getProductName(), soldProductsMap.get(product.getProductName()) + 1);
                } else {
                    soldProductsMap.put(product.getProductName(), 1);
                }
            }
        }
        Map<String, Integer> sortedList = ListUtils.sortMapByValue(soldProductsMap);
        return sortedList;
    }

    public static void main(String[] args) {
        String[] Strings = { "Mamma mia", "mamma Mia", "MAmMa  Mia", "mammaMia", "" };
        for (String string : Strings) {
            String convertToTitleCase = convertToTitleCase(string);
            System.out.println(convertToTitleCase);
        }

    }
}
