package Lab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

class PizzaInfo
{
    private final String name ;
    private final double price;

    PizzaInfo(String _name, double _price)
    {
        name = _name;
        price = _price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}
class PizzaMenu
{
    private final ArrayList<PizzaInfo> pizzaArray = new ArrayList<>();
    PizzaMenu()
    {
        pizzaArray.add(new PizzaInfo("Cheese", 5));
        pizzaArray.add(new PizzaInfo("Chicken", 7.50));
        pizzaArray.add(new PizzaInfo("Seafood", 10));
    }

    public ArrayList<PizzaInfo> getPizzaArray() {
        return pizzaArray;
    }

    public void DisplayMenu()
    {
        for(int i = 0; i < pizzaArray.size() ; ++ i)
        {
            System.out.println(i + 1 + ":" + pizzaArray.get(i).getName() + ":$" + DoubleStringFormat(pizzaArray.get(i).getPrice()));
        }
    }

    public static String DoubleStringFormat(double value)
    {

        // not a good practice cast double to long as maybe the size is difference
        if(value == (long) value)
            return String.format("%d",(long)value);
        else
            return String.format("%s",value);
    }

}
class PizzaOrder
{
    private final String name;
    private final int quantity;
    private final double price;
    PizzaOrder(String _name, double _price , int _quantity )
    {
        name = _name;
        price = _price;
        quantity = _quantity;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
}
class PizzaCustomer
{
    private final String name;



    private final ArrayList<PizzaOrder> pizzaArray = new ArrayList<>();
    PizzaCustomer(String _name)
    {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PizzaOrder> getPizzaArray() {
        return pizzaArray;
    }

    public void AddOrder(PizzaInfo pizza , int quantity)
    {
        pizzaArray.add(new PizzaOrder(pizza.getName(), pizza.getPrice(), quantity));
    }

    public void DisplayOrder()
    {
        System.out.println(name + ", you have order");
        double finalPrice = 0.0;
        for (PizzaOrder pizzaOrder : pizzaArray) {
            double totalPrice = pizzaOrder.getPrice() * pizzaOrder.getQuantity();
            System.out.println(pizzaOrder.getName() + ":$" + DoubleStringFormat(pizzaOrder.getPrice()) + " x " + pizzaOrder.getQuantity() + " = $" + DoubleStringFormat(totalPrice));
            finalPrice += totalPrice;
        }
        System.out.println("Total $" + finalPrice);
    }

    public static String DoubleStringFormat(double value)
    {
        // not a good practice cast double to long as maybe the size is difference
        if(value == (long) value)
            return String.format("%d",(long)value);
        else
            return String.format("%s",value);
    }

}

class PizzaCasher
{
    // id
    private int OrderID =0 ;
    // for this lab purpose i just init all here, not a ood practice for dependency
    Hashtable<String, PizzaCustomer> orderList = new Hashtable<>();

    public void AddNewOrder(PizzaCustomer pizzaCustomer)
    {
        LocalDate currentDate = LocalDate.now();
        ++OrderID;
        // should generate the ID to customer recipt, for this lab  just ignore it.
        String currentDatePlusOrderID = "" + OrderID + " " + currentDate;
        orderList.put(currentDatePlusOrderID,pizzaCustomer);

    }
    public static String DoubleStringFormat(double value)
    {
        // not a good practice cast double to long as maybe the size is difference
        if(value == (long) value)
            return String.format("%d",(long)value);
        else
            return String.format("%s",value);
    }
    public void PrintALlOrder()
    {
      orderList.forEach(this::PrintOrder);
    }

    public void PrintOrder(String key,  PizzaCustomer value)
    {
        System.out.println("OrderID:" + key);
        System.out.println("Name:" + value.getName());

        ArrayList<PizzaOrder> pizzaArray = value.getPizzaArray();

        double finalPrice = 0.0;
        for (PizzaOrder pizzaOrder : pizzaArray) {
            double totalPrice = pizzaOrder.getPrice() * pizzaOrder.getQuantity();
            System.out.println(pizzaOrder.getName() + ":$" + DoubleStringFormat(pizzaOrder.getPrice()) + " x " + pizzaOrder.getQuantity() + " = $" + DoubleStringFormat(totalPrice));
            finalPrice += totalPrice;
        }
        System.out.println("Total $" + finalPrice);
        System.out.println();

    }

}

public class Lab4Q3
{

    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in).useDelimiter("\n");
        PizzaMenu menu = new PizzaMenu();
        PizzaCasher pizzaCasher = new PizzaCasher();

        while(true) {
            String msg;
            System.out.println("Welcome! How can i address you?");
            PizzaCustomer customer = new PizzaCustomer(userInput.next());
            System.out.println(customer.getName() + " below is our pizza menu");
            menu.DisplayMenu();
            System.out.println("Please type the index follow by space and then follow by quantity");
            msg = userInput.next();
            String[] data = msg.split(" ");

            for (int i = 0; i < data.length; i += 2) {
                // check value
                if (i + 1 > data.length)
                    break;

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(data[i + 1]);
                } catch (NumberFormatException ignored) {

                }
                if (quantity < 1)
                    continue;

                int number = 0;

                try {
                    number = Integer.parseInt(data[i]);
                } catch (NumberFormatException ignored) {

                }

                if (number < 0)
                    continue;

                int index = number - 1;

                if (index > menu.getPizzaArray().size())
                    continue;

                // processing
                customer.AddOrder(menu.getPizzaArray().get(index), quantity);

            }
            // possible make as a loop that user can edit order again .
            customer.DisplayOrder();
            pizzaCasher.AddNewOrder(customer);

            String options;
            do {
                System.out.println("Adding new customer order? Y/N");
                options = userInput.next();
            }
            while(options.charAt(0) != 'Y' && options.charAt(0) != 'y' && options.charAt(0) != 'N' && options.charAt(0) != 'n');

            if(options.charAt(0) == 'N' || options.charAt(0) == 'n')
                break;
        }

        System.out.println();
        System.out.println("Here the summary of all order");
        pizzaCasher.PrintALlOrder();

    }
}
