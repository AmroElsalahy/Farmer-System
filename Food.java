/**
 * This class represents the food in the farm to feed the animals
 *
 * @author Mayra Barrera
 */
public class Food {

    private String name;
    private double price;
    private int availableQuantity;

    /**
     * Creates a new product given a name and price, food quantity starts with 1
     * @param name the type of the product
     * @param price the price of the product
     */
    public Food(String name, double price){
        this.name = name;
        this.price = price;

        availableQuantity = 0;
    }

    /**
     * Name getter
     * @return returns the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Name getter
     * @return returns the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Name getter
     * @return returns the available product
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int count){
        availableQuantity += count;
    }

    /**
     * Return the cost to buy X quantity of a product
     * @param quantity the units of product to buy
     * @return the cost to buy the product
     */
    public double costToBuyFood(int quantity){
        return price * quantity;
    }

    /**
     * Add x product to the available product
     * @param quantity the units of product to buy
     * @param money the amount given by the farmer
     * @return if the food was bought or not
     */
    public boolean buyFood (int quantity, double money){

        if(costToBuyFood(quantity) <= money){
            availableQuantity += quantity;
            return true;
        }
        return false;
    }

    /**
     * Detects if a product is low or not based on the needed units
     * @param neededUnits the units needed for this specific product
     * @return if there are enough units or not
     */
    public boolean enoughFood (int neededUnits){
        if(neededUnits < availableQuantity){
            return true;
        }
        return false;
    }

    /**
     * @return a string representation of the Product as "name - price CAD - availableQuantity units"
     */
    @Override
    public String toString() {
        return String.format("%s costs %.2f CAD, there is %d units", name, price, availableQuantity);
    }

    /**
     * @return the animal name to check if is the same
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Food)){
            return false;
        }
        return ((Food)obj).name.equals(name);
    }

}