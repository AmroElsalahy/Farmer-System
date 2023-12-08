import java.util.*;

/**
 * Represents a Farm.
 * Farms have a list of animals, and products
 *
 * @author Mayra Barrera
 */
public class FarmHash {

    private int farmSize;
    private String farmName;
    private Map<String,Animal> farmAnimals;
    private Map<String,Food> farmFood;

    /**
     * Creates a new farm given its size a name.
     * The table is initialized with an empty list of Animals and Food
     * @param farmSize the maximum capacity of the farm (e.g. 4 means can hold up to 4 animals)
     * @param farmName the name of the farm
     */
    public FarmHash(int farmSize, String farmName){

        this.farmSize = farmSize;
        this.farmName = farmName;

        farmAnimals = new HashMap<>(this.farmSize);
        farmFood = new HashMap<>();
    }

    public int getFarmSize(){
        return farmSize;
    }

    public String getFarmName(){return farmName;}

    public Map<String,Animal> getHashFarmAnimals(){ return  farmAnimals;}

    public Map<String,Food> getHashFarmFood(){ return farmFood;}

    /**
     * Adds an animal to the farm. If the animal already exists, it increases the count
     * @param animal the animal
     * @return true if there was room, false if the farm was already full
     *
     */
    public boolean addAnimal(Animal animal){

        //Farm is full - HashMap
        if(farmAnimals.size() >= farmSize){
            return false;
        }

        //add count to existing animal - HashMap
        if(farmAnimals.containsKey(animal.getName())){
            farmAnimals.get(animal.getName()).setNumber(animal.getNumber());
        }
        else{
            farmAnimals.put(animal.getName(), animal);
        }

        return true;
    }

    /**
     * Adds new food to the farm. If the food already exists, it increases the count
     * @param food the food type
     * @param quantity how much food to buy
     * @return boolean food was bought or not
     */
    public boolean buyFood(Food food, int quantity){

        //add food to existing foodProduct - Hash
        if(farmFood.containsKey(food.getName())){
            farmFood.get(food.getName()).setAvailableQuantity(quantity);
        }
        else{
            food.setAvailableQuantity(quantity);
            farmFood.put(food.getName(), food);
        }

        return true;
    }

    /**
     * Adds needed food to the farm based on time
     * if the money is not enough return false,
     *
     * @param time the amount of time we want the buy food for
     * @param money how much money we have
     * @return boolean if the food was bought or not
     */
    public boolean buyFood(String time, double money){

        //hashmap buyFood
        for(Map.Entry<String, Integer> foods : getNeededFood(getTime(time)).entrySet()){

            //add food to existing foodProduct
            if(farmFood.containsKey(foods.getKey())){

                //get money
                double neededMoney = farmFood.get(foods.getKey()).costToBuyFood(foods.getValue());
                double currentMoney = money - neededMoney;

                if(currentMoney >= 0){
                    farmFood.get(foods.getKey()).buyFood(foods.getValue(), neededMoney);
                    money = currentMoney;
                }

                else{
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method checks that every animal has enough food based on their food they eat
     * @param time provide if the food is for a day, a week or a month
     * @return string saying print more food, or everything is good
     */
    public String availableFood(String time){

        //check if enough food is available, create string with result
        String result = "";
        boolean first = true;

        for(Map.Entry<String, Integer> food : getNeededFood(getTime(time)).entrySet()){

            if(farmFood.containsKey(food.getKey())){

                if(!first){
                    result += "\n";
                }

                if(farmFood.get(food.getKey()).enoughFood(food.getValue())){
                    result += "there is enough " + food.getKey() + " for a " + time;
                }
                else{
                    result += "time to buy more " + food.getKey();
                }

                first = false;
            }
        }

        return result;
    }

    /**
     * Calculate the cost to run the farm by a specific set of time
     * @param time the time the costs are for
     * @return a double representing the total cost of the farm products
     */
    public double calculateCosts(String time){

        double totalCosts = 0;

        //get the price of each food item
        for(Map.Entry<String, Integer> food : getNeededFood(getTime(time)).entrySet()){

            if(farmFood.containsKey(food.getKey())){
                totalCosts += food.getValue() * farmFood.get(food.getKey()).getPrice();
            }
        }
        return totalCosts;
    }

    public double calculateMonthlyCosts(String time){

        double totalCosts = 0;

        //get the price of each food item
        for(Map.Entry<String, Integer> food : getAllRequireFood(getTime(time)).entrySet()){

            if(farmFood.containsKey(food.getKey())){
                totalCosts += food.getValue() * farmFood.get(food.getKey()).getPrice();
            }
        }
        return totalCosts;
    }

    /**
     * Returns the earnings of the farm by time
     * @param time the time frame for the costs
     * @param province the province to calculate costs
     * @param sales the income of the farm
     * @return a double representing the total dish value ordered at the table.
     */
    public double calculateEarnings(String time, String province, double sales){

        double totalCosts = calculateMonthlyCosts(time);
        double salesTax = sales * taxRate(province);

        double monthlyEarnings = sales - (salesTax + totalCosts);
        return monthlyEarnings;
    }

    /**
     * Returns all available products in the farm
     * @return a string
     */
    public String getFarmInventory(){

        String result = "";
        boolean first = true;

        for(Animal a : farmAnimals.values()){

            if(!first){
                result += "\n";
            }

            result += a.toString();
            first = false;
        }

        for(Food a : farmFood.values()){

            if(!first){
                result += "\n";
            }

            result += a.toString();
        }

        return result;
    }

    /**
     * Returns all available products in the farm
     * @return a string
     */
    public void printFarmInvoice(String time, String province, double sales){

        double monthlyEarnings = calculateEarnings(time,province,sales);
        double neededMoney = calculateMonthlyCosts(time);
        double salesTax = sales * taxRate(province);

        System.out.printf("For the farm called %s.\n", farmName);
        System.out.printf("The cost to feed %d farm animals each week is: %.2f CAD.\n", farmSize, neededMoney/4);
        System.out.printf("The monthly costs are: %.2f CAD.\n", neededMoney);
        System.out.printf("The monthly sale tax was: %.2f CAD.\n", salesTax);
        System.out.printf("The monthly sales were: %.2f CAD.\n", sales);
        System.out.printf("The monthly earnings were: %.2f CAD.\n", monthlyEarnings);
    }


    /**
     * Calcultes the num of days based on a specific time
     * @param time represents the time 
     * @return int representing the time
     */
    private int getTime(String time){

        int numDays = 0;

        switch(time) {
            case "week":
                numDays = 7;
                break;
            case "month":
                numDays = 28;
                break;
            default:
                numDays = 1;
                break;
        }

        return numDays;
    }

    /**
     * Calculates the tax rate based on a province
     * @param province represents the time
     * @return a double representing the tax rate
     */
    private double taxRate(String province){

        double taxRate = 0;

        if(province.equals("AB") || province.equals("NT") || province.equals("NU") || province.equals("YT")){
            taxRate = 0.05;
        }
        else if(province.equals("SK")){
            taxRate = 0.11;
        }
        else if(province.equals("BC") || province.equals("MB")){
            taxRate = 0.12;
        }
        else if (province.equals("ON")){
            taxRate = 0.13;
        }
        else if(province.equals("QC")){
            taxRate = 0.1498;
        }
        else {
            taxRate = 0.15;
        }

        return taxRate;
    }

    /**
     * get the needed food produce and quantities for the farm animals
     * @param numDays the time period you calculate the costs
     * @return a HashMap of name and quantities.
     */
    private Map<String, Integer> getNeededFood (int numDays){

        //get the list of needed food
        Map<String, Integer> neededFood = new HashMap<>();

        for(Animal a : farmAnimals.values()){

            String type = a.getFood();
            int quantity = a.getDailyFoodIntake() * numDays;

            if(neededFood.containsKey(type)){
                quantity += neededFood.get(type);
                neededFood.replace(type,quantity);
            }
            else{
                neededFood.put(type,quantity);
            }
        }

        for(Map.Entry<String, Food> foods : farmFood.entrySet()){

            for(Map.Entry<String, Integer> nFood : neededFood.entrySet()) {

                    if(nFood.getKey().equals(foods.getKey())){

                        int reqValue = nFood.getValue() - foods.getValue().getAvailableQuantity();
                        neededFood.put(nFood.getKey(),reqValue);
                    }
            }
        }

        return neededFood;
    }

    private Map<String, Integer> getAllRequireFood (int numDays) {

        //get the list of needed food
        Map<String, Integer> neededFood = new HashMap<>();

        for (Animal a : farmAnimals.values()) {

            String type = a.getFood();
            int quantity = a.getDailyFoodIntake() * numDays;

            if (neededFood.containsKey(type)) {
                quantity += neededFood.get(type);
                neededFood.replace(type, quantity);
            } else {
                neededFood.put(type, quantity);
            }
        }

        return neededFood;
    }
}