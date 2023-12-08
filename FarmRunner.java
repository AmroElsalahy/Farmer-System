import java.util.ArrayList;
import java.util.Scanner;

public class FarmRunner {
    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);

        //0-HashMaps , 1-ArrayList
        int dataType = kb.nextInt();
        kb.nextLine();

        if(dataType==1) {
            //create small farms
            Farm smallFarm = new Farm(2, "Small Farm");
            smallFarm.addAnimal(new Animal("cow", "grass", 5));
            smallFarm.addAnimal(new Animal("sheep", "lemon", 3));
            smallFarm.buyFood(new Food("grass", 5.0), 0);
            smallFarm.buyFood(new Food("lemon", 4.2), 0);

            //Cool, lets if we have enough food for a month
            String timeFrame = "month";
            System.out.println(smallFarm.availableFood(timeFrame));

            //Buy the missing food for a month
            double neededMoney = smallFarm.calculateCosts(timeFrame);
            System.out.printf("%.2f\n",neededMoney);

            if (smallFarm.buyFood("day", neededMoney)) {
                System.out.println("food bought");
            } else {
                System.out.println("not enough money");
            }

            //Print the farm monthly costs and earnings
            String province = kb.nextLine();
            double sales = kb.nextDouble();

            smallFarm.printFarmInvoice(timeFrame, province, sales);

            //start second part
            kb.nextLine();

            Farm largeFarm = new Farm(4, "Large Farm");

            //First we add animals to the farm + the needed food
            for (int i = 0; i < largeFarm.getFarmSize(); i++) {

                //get rid of that pesky \n
                System.out.println("Enter animal: ");
                String name = kb.nextLine();
                System.out.println("Enter food type: ");
                String foodType = kb.nextLine();
                System.out.println("Enter food intake: ");
                int foodIntake = kb.nextInt();
                System.out.println("Enter food price: ");
                double foodPrice = kb.nextDouble();

                //Add new Animal to the Farm
                Animal newAnimal = new Animal(name, foodType, foodIntake);
                largeFarm.addAnimal(newAnimal);

                //Add new food to the farm
                Food newFood = new Food(foodType, foodPrice);
                largeFarm.buyFood(newFood, foodIntake);

                kb.nextLine();
            }

            System.out.println(largeFarm.getFarmInventory());
        }

        else{
            //create small farms
            FarmHash smallFarm = new FarmHash(2, "Small Farm");
            smallFarm.addAnimal(new Animal("cow", "grass", 5));
            smallFarm.addAnimal(new Animal("sheep", "lemon", 3));
            smallFarm.buyFood(new Food("grass", 5.0), 0);
            smallFarm.buyFood(new Food("lemon", 4.2), 0);

            //Cool, lets if we have enough food for a month
            String timeFrame = "month";
            System.out.println(smallFarm.availableFood(timeFrame));

            //Buy the missing food for a month
            double neededMoney = smallFarm.calculateCosts(timeFrame);
            System.out.printf("%.2f\n",neededMoney);

            if (smallFarm.buyFood("day", neededMoney)) {
                System.out.println("food bought");
            } else {
                System.out.println("not enough money");
            }

            //Print the farm monthly costs and earnings
            String province = kb.nextLine();
            double sales = kb.nextDouble();

            smallFarm.printFarmInvoice(timeFrame, province, sales);

            //start second part
            kb.nextLine();

            FarmHash largeFarm = new FarmHash(4, "Large Farm");

            //First we add animals to the farm + the needed food
            for (int i = 0; i < largeFarm.getFarmSize(); i++) {

                //get rid of that pesky \n
                System.out.println("Enter animal: ");
                String name = kb.nextLine();
                System.out.println("Enter food type: ");
                String foodType = kb.nextLine();
                System.out.println("Enter food intake: ");
                int foodIntake = kb.nextInt();
                System.out.println("Enter food price: ");
                double foodPrice = kb.nextDouble();

                //Add new Animal to the Farm
                Animal newAnimal = new Animal(name, foodType, foodIntake);
                largeFarm.addAnimal(newAnimal);

                //Add new food to the farm
                Food newFood = new Food(foodType, foodPrice);
                largeFarm.buyFood(newFood, foodIntake);

                kb.nextLine();
            }

            System.out.println(largeFarm.getFarmInventory());
        }
    }
}