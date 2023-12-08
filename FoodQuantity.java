public class FoodQuantity {

    private String name;
    private int quantity;

    public FoodQuantity(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName(){return name;}

    public int getQuantity(){return  quantity;}

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * @return the animal name to check if is the same
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FoodQuantity)){
            return false;
        }
        return ((FoodQuantity)obj).name.equals(name);
    }


}
