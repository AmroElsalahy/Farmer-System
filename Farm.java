import java.util.ArrayList;

/**
 * Represents a Farm.
 * Farms have a list of animals, and products
 *
 * @author Mayra Barrera
 */
public class Farm {

    private int farmSize;
    private String farmName;

    private ArrayList<Animal> farmAnimals;
    private ArrayList<Food> farmFood;

    /**
     * Creates a new farm given its size a name.
     * The table is initialized with an empty list of Animals and Food
     * @param farmSize the maximum capacity of the farm (e.g. 4 means can hold up to 4 animals)
     * @param farmName the name of the farm
     */
    public Farm(int farmSize, String farmName){

        this.farmSize = farmSize;
        this.farmName = farmName;

        farmAnimals = new ArrayList<Animal>();
        farmFood = new ArrayList<Food>();
    }

    public int getFarmSize(){
        return farmSize;
    }

    public String getFarmName(){return farmName;}

    public ArrayList<Animal> getFarmAnimals(){return farmAnimals;}

    public ArrayList<Food> getFarmFood(){return farmFood;}

    /**
     * Adds an animal to the farm. If the animal already exists, it increases the count
     * @param animal the animal
     * @return true if there was room, false if the farm was already full
     *
     */
    public boolean addAnimal(Animal animal){

        if(farmAnimals.size() >= farmSize){
            return false;
        }

        //add count to existing animal - HashMap
        if(farmAnimals.contains(animal)){

            for (Animal a: farmAnimals) {

                if(a.getName().equals(animal.getName())){
                    a.setNumber(animal.getNumber());
                }

            }
        }
        else{
            farmAnimals.add(animal);
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

        //add count to existing animal - HashMap
        if(farmFood.contains(food)){

            for (Food f: farmFood) {

                if(f.getName().equals(food.getName())){
                    f.setAvailableQuantity(quantity);
                }
            }
        }
        else{
            food.setAvailableQuantity(quantity);
            farmFood.add(food);
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

        for (FoodQuantity foods: getNeededFood_list(getTime(time))) {

            for(Food f: farmFood){

                if(f.getName().equals(foods.getName())){

                    //get money
                    double neededMoney = f.costToBuyFood(foods.getQuantity());
                    double currentMoney = money - neededMoney;

                    if(currentMoney >= 0){
                        f.buyFood(foods.getQuantity(), neededMoney);
                        money = currentMoney;
                    }

                    else{
                        return false;
                    }
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

        for (FoodQuantity foods: getNeededFood_list(getTime(time))){

            for(Food f : farmFood) {

                if (f.getName().equals(foods.getName())) {

                    if (!first) {
                        result += "\n";
                    }

                    if (f.enoughFood(foods.getQuantity())) {
                        result += "there is enough " + f.getName() + " for a " + time;
                    } else {
                        result += "time to buy more " + f.getName();
                    }

                    first = false;
                }
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
        for (FoodQuantity foods: getNeededFood_list(getTime(time))) {

            for(Food food: farmFood){

                if (food.getName().equals(foods.getName())) {

                    totalCosts += foods.getQuantity() * food.getPrice();
                }
            }
        }
        return totalCosts;
    }

    public double calculateMonthlyCosts(String time){

        double totalCosts = 0;

        //get the price of each food item
        for (FoodQuantity foods: getAllRequireFood(getTime(time))) {

            for(Food food: farmFood){

                if (food.getName().equals(foods.getName())) {

                    totalCosts += foods.getQuantity() * food.getPrice();
                }
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

        for(Animal a : farmAnimals){

            if(!first){
                result += "\n";
            }

            result += a.toString();
            first = false;
        }

        for(Food a : farmFood){

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
    private ArrayList<FoodQuantity> getNeededFood_list (int numDays){

        //get the list of needed food - neededFood[name][quantity] (in string)
        ArrayList<FoodQuantity> neededFood = new ArrayList<>();

        for(Animal a : farmAnimals){

            String type = a.getFood();
            int quantity = a.getDailyFoodIntake() * numDays;
            FoodQuantity newFQ = new FoodQuantity(type,quantity);

            if(neededFood.contains(newFQ)){

                for (FoodQuantity fq: neededFood) {

                    if(fq.getName().equals(newFQ.getName())){
                        quantity += fq.getQuantity();
                        fq.setQuantity(quantity);
                    }
                }
            }
            else{
                neededFood.add(new FoodQuantity(type,quantity));
            }
        }

        for(Food foods : farmFood){

            for(FoodQuantity nFood : neededFood) {

                if(nFood.getName().equals(foods.getName())){

                    int reqValue = nFood.getQuantity() - foods.getAvailableQuantity();
                    nFood.setQuantity(reqValue);
                }
            }
        }

        return  neededFood;

    }

    private ArrayList<FoodQuantity> getAllRequireFood (int numDays){

        //get the list of needed food - neededFood[name][quantity] (in string)
        ArrayList<FoodQuantity> neededFood = new ArrayList<>();

        for(Animal a : farmAnimals){

            String type = a.getFood();
            int quantity = a.getDailyFoodIntake() * numDays;
            FoodQuantity newFQ = new FoodQuantity(type,quantity);

            if(neededFood.contains(newFQ)){

                for (FoodQuantity fq: neededFood) {

                    if(fq.getName().equals(newFQ.getName())){
                        quantity += fq.getQuantity();
                        fq.setQuantity(quantity);
                    }
                }
            }
            else{
                neededFood.add(new FoodQuantity(type,quantity));
            }
        }

        return  neededFood;
    }

}