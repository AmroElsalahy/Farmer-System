/**
 * This class represents the animals available that live in the farm
 *
 * @author Mayra Barrera
 *
 */
public class Animal {

    private String name;
    private String food;
    private int number;
    private int dailyFoodIntake;

    /**
     * Creates a new animal given its name, food type and dailyFoodIntake
     * @param name the animal's name
     * @param food the animal's food type
     * @param dailyFoodIntake the animal's weekly food intake (in units)
     */
    public Animal(String name,  String food, int dailyFoodIntake){
        this.name = name;
        this.food = food;
        this.dailyFoodIntake = dailyFoodIntake;

        //you start with 1 animal
        number = 1;
    }

    public String getName() {
        return name;
    }

    public String getFood(){
        return food;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number){
        this.number += number;
    }

    /**
     * @return Gets the daily food intake for all the animals of this type
     */
    public int getDailyFoodIntake(){
        return dailyFoodIntake * number;
    }

    /**
     * @return a string representation of the Animal as "name eats dailyFoodIntake of food each week"
     */
    @Override
    public String toString() {
        return String.format("animal: %s eats %d of %s each week.", name, dailyFoodIntake, food);
    }

    /**
     * @return the animal name to check if is the same
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Animal)){
            return false;
        }
        return ((Animal)obj).name.equals(name);
    }
}