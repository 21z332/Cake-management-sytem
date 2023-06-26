import java.util.*;
import java.text.*;
abstract class Cake {
    private String flavor;
    private String size;
    private double basePrice;

    public Cake(String flavor, String size, double basePrice) {
        this.flavor = flavor;
        this.size = size;
        this.basePrice = basePrice;
    }

    public abstract double calculatePrice(String size);

    public String getFlavor() {
        return flavor;
    }

    public String getSize() {
        return size;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String toString() {
        return flavor + " Cake (" + size + "): $" + getBasePrice();
    }
}
class GenoiseCake extends Cake {
    public GenoiseCake(String size, double basePrice) {
        super("genoise", size, basePrice);
    }

    public double calculatePrice( String size) {
        double multiplier;
        switch (size) {
            case "small":
                multiplier = 1.2;
                break;
            case "medium":
                multiplier = 1.5;
                break;
            case "large":
                multiplier = 1.8;
                break;
            default:
                multiplier = 1.0;
                break;
        }
        return getBasePrice() * multiplier;
    }
}

class ChiffonCake extends Cake {
    public ChiffonCake(String size, double basePrice) {
        super("chiffon", size, basePrice);
    }

    public double calculatePrice(String size) {
        double multiplier;
        switch (size) {
            case "small":
                multiplier = 1.3;
                break;
            case "medium":
                multiplier = 1.6;
                break;
            case "large":
                multiplier = 1.9;
                break;
            default:
                multiplier = 1.0;
                break;
        }
        return getBasePrice() * multiplier;
    }
}

class ChocolateCake extends Cake {
    public ChocolateCake(String size, double basePrice) {
        super("chocolate", size, basePrice);
    }

    public double calculatePrice(String size) {
        double multiplier;
        switch (size) {
            case "small":
                multiplier = 0.9;
                break;
            case "medium":
                multiplier = 1.2;
                break;
            case "large":
                multiplier = 1.5;
                break;
            default:
                multiplier = 1.0;
                break;
        }
        return getBasePrice() * multiplier;
    }
}

class VanillaCake extends Cake {
    public VanillaCake(String size, double basePrice) {
        super("vanilla", size, basePrice);
    }

    public double calculatePrice(String size) {
        double multiplier;
        switch (size) {
            case "small":
                multiplier = 1.0;
                break;
            case "medium":
                multiplier = 1.3;
                break;
            case "large":
                multiplier = 1.6;
                break;
            default:
                multiplier = 1.0;
                break;
        }
        return getBasePrice() * multiplier;
    }
}

interface Customer {
    void displayInfo();
    double getDiscount();
    boolean isNewCustomer();
}

class RegularCustomer implements Customer
{
    private String name;
    private int contactNumber;
   
    public RegularCustomer(String name,int contactNumber)
    {
        this.name=name;
        this.contactNumber=contactNumber;
    }
   
    public void displayInfo() {
        System.out.println("Customer Name: " + name + " (" + contactNumber + ")");
    }
   
    public double getDiscount()
    {
        return 0.0;
    }
   
    public boolean isNewCustomer() {
        return false;
    }
}

class PremiumCustomer implements Customer
{
    private String name;
    private int contactNumber;
   
    public PremiumCustomer(String name,int contactNumber)
    {
        this.name=name;
        this.contactNumber=contactNumber;
    }
   
    public void displayInfo() {
        System.out.println("Customer Name: " + name + " (" + contactNumber + ")");
    }
    public double getDiscount()
    {
        return 0.1;
    }
   
    public boolean isNewCustomer() {
        return false;
    }
}

class NewCustomer implements Customer {
    // New implementation for new customers...

    private String name;
    private int contactNumber;

    public NewCustomer(String name, int contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public void displayInfo() {
        System.out.println("Customer Name: " + name + " (" + contactNumber + ")");
    }

    public double getDiscount() {
        return 0.2;
    }

    public boolean isNewCustomer() {
        return true;
    }
}

class CakeShop {
    private Map<Cake, Integer> availableCakes;
    public List<Customer> customers;
   
    public CakeShop() {
        availableCakes = new HashMap<>();
        customers=new ArrayList<>();
    }
   
    public void addCustomer(Customer customer) {
        if (customer.isNewCustomer()) {
        customers.add(customer);
        }
        else if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }
   
    public void addCake(Cake cake, int quantity) {
        if (availableCakes.containsKey(cake)) {
            quantity += availableCakes.get(cake);
        }
        availableCakes.put(cake, quantity);
    }

    public double bookCake(Customer customer, String cakeName,String size ,double quantity) {
        double price=0;
        Cake selectedCake = null;
        for (Cake cake : availableCakes.keySet()) {
            if (cake.getFlavor().equals(cakeName)&&cake.getSize().equals(size)) {
                selectedCake = cake;
                break;
            }
        }
       
        if (selectedCake == null || availableCakes.get(selectedCake) == 0) {
            System.out.println("Booking failed. Cake not available.");
            return 0;
        }


        int remainingQuantity = availableCakes.get(selectedCake) - (int)quantity;
        availableCakes.put(selectedCake, remainingQuantity);

        System.out.println("Booking confirmed:");
        //customer.displayInfo();
        System.out.println("Cake: " + selectedCake);
        price=selectedCake.calculatePrice(size);
       
        if (customer instanceof PremiumCustomer)
        {
        double discount = customer.getDiscount();
        price = price - (price * discount);
        }
        else if (customer.isNewCustomer())
        {
            double discount = customer.getDiscount();
            price = price - (price * discount);
        }

        //System.out.println("Total Price: $" + price);

        // Update the availability count
        if (remainingQuantity == 0) {
            availableCakes.remove(selectedCake);
        }
       
        return (price*quantity);
    }

    public void displayAvailableCakes() {
        System.out.println("Available Cakes: \n");
        for (Map.Entry<Cake, Integer> entry : availableCakes.entrySet()) {
            Cake cake = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(cake + " - Quantity: " + quantity);
        }
    }
}

class Main {
    public static void main(String[] args) {
        // Create cake shop
        String cakeName;
        double quantity;
        String custName;
        int contactNumber;
        boolean isPremium=false;
        double totalPrice=0;
        String size;
       
       
        Scanner sc=new Scanner(System.in);
        CakeShop cakeShop = new CakeShop();
       
        // Create cakes
        Cake chocolateCakesmall = new ChocolateCake("small", 25.0);
        Cake chocolateCakeMedium = new ChocolateCake("medium", 35.0);
        Cake chocolateCakeLarge = new ChocolateCake("large", 45.0);
       
        Cake vanillaCakesmall = new VanillaCake("small", 35.0);
        Cake vanillaCakeMedium = new VanillaCake("medium", 45.0);
        Cake vanillaCakeLarge = new VanillaCake("large", 55.0);
       
        Cake chiffonCakesmall = new ChiffonCake("small", 45.0);
        Cake chiffonCakeMedium = new ChiffonCake("medium", 55.0);
        Cake chiffonCakeLarge = new ChiffonCake("large", 65.0);
       
        Cake genoiseCakesmall = new GenoiseCake("small", 55.0);
        Cake genoiseCakeMedium = new GenoiseCake("medium", 65.0);
        Cake genoiseCakeLarge = new GenoiseCake("large", 75.0);

        // Add cakes to the cake shop with quantity
        cakeShop.addCake(chocolateCakesmall, 20);
        cakeShop.addCake(chocolateCakeMedium, 20);
        cakeShop.addCake(chocolateCakeLarge, 20);
       
        cakeShop.addCake(vanillaCakesmall, 20);
        cakeShop.addCake(vanillaCakeMedium, 20);
        cakeShop.addCake(vanillaCakeLarge, 20);
       
        cakeShop.addCake(chiffonCakesmall, 20);
        cakeShop.addCake(chiffonCakeMedium, 20);
        cakeShop.addCake(chiffonCakeLarge, 20);
       
        cakeShop.addCake(genoiseCakesmall, 20);
        cakeShop.addCake(genoiseCakeMedium, 20);
        cakeShop.addCake(genoiseCakeLarge, 20);
       
        System.out.println("Welcome!");
        System.out.println("What is you name?");
        custName=sc.next();
        System.out.println("Can you say your contact number?");
        contactNumber=sc.nextInt();
        System.out.println("Are you a new customer or an existing customer[regular or premium customer]?");
        String customerType = sc.next();
       
       
       
        Customer premiumCustomer=null;
        Customer newCustomer=null;
        Customer regularCustomer=null;
       
        if (customerType.equals("new")) {
    newCustomer = new NewCustomer(custName, contactNumber);
    cakeShop.addCustomer(newCustomer);
        } else if (customerType.equals("premium")) {
            premiumCustomer = new PremiumCustomer(custName, contactNumber);
            isPremium=true;
            cakeShop.addCustomer(premiumCustomer);
        } else {
            regularCustomer = new RegularCustomer(custName, contactNumber);
            isPremium=false;
            cakeShop.addCustomer(regularCustomer);
        }
       
       
        // Display available cakes
        cakeShop.displayAvailableCakes();
        System.out.println();
       
       
        System.out.println("How many types of cakes do you want: ");
        int n=sc.nextInt();
        //sc.nextLine();
        for(int i=0;i<n;i++)
        {
            //sc.nextLine();
            System.out.println("What cake do you want?");
            cakeName=sc.next().toLowerCase();
            System.out.println("How much number of "+cakeName+ " do you want?");
            quantity=sc.nextDouble();
           
            System.out.println("Which size of cake do you want?");
            size=sc.next().toLowerCase();
            //sc.nextLine();
           
            if(isPremium)
            {
                totalPrice+=cakeShop.bookCake(premiumCustomer,cakeName,size,quantity);
            }
            else if(customerType.equals("regular"))
            {
                totalPrice+=cakeShop.bookCake(regularCustomer,cakeName,size,quantity);
            }
            else
            {
                totalPrice+=cakeShop.bookCake(newCustomer,cakeName,size,quantity);
            }
        }
        System.out.println("TotalBill: $"+totalPrice);
    }
}
