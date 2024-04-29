import java.io.Serializable;

/**
 * Enum representing the species of a tree.
 */
enum TreeSpecies {
    BIRCH, MAPLE, FIR
}

/**
 * The Tree class represents a tree object with properties such as species, height, growth rate, and year of planting.
 * It provides methods to manipulate and retrieve the tree's properties.
 */
public class Tree implements Serializable {
    //Private variables
    private TreeSpecies species;
    private double height, growthRate;
    private int yearOfPlanting;

    /**
     * Constructs a new Tree object with the specified species, height, growth rate, and year of planting.
     *
     * @param species The species of the tree.
     * @param height The height of the tree.
     * @param growthRate The growth rate of the tree.
     * @param yearOfPlanting The year the tree was planted.
     */
    public Tree(TreeSpecies species, double height, double growthRate, int yearOfPlanting) {
        this.height = height;
        this.growthRate = growthRate;
        this.yearOfPlanting = yearOfPlanting;
        this.species = species;
    }

    /**
     * Simulates the growth of the tree for one year by updating its height based on the growth rate.
     */
    public void growthForOneYear(){
        height += height * growthRate;
    }

    /**
     * Sets the species of the tree.
     *
     * @param species The species to set.
     */
    public void setSpecies(TreeSpecies species){
        this.species = species;
    }

    /**
     * Sets the height of the tree.
     *
     * @param height The height to set.
     */
    public void setHeight(double height){
        this.height = height;
    }

    /**
     * Sets the growth rate of the tree.
     *
     * @param growthRate The growth rate to set.
     */
    public void setGrowthRate(double growthRate){
        this.growthRate = growthRate;
    }

    /**
     * Sets the year of planting of the tree.
     *
     * @param yearOfPlanting The year of planting to set.
     */
    public void setYearOfPlanting(int yearOfPlanting){
        this.yearOfPlanting = yearOfPlanting;
    }

    /**
     * Retrieves the species of the tree.
     *
     * @return The species of the tree.
     */
    public TreeSpecies getSpecies(){
        return this.species;
    }

    /**
     * Retrieves the height of the tree.
     *
     * @return The height of the tree.
     */
    public double getHeight(){
        return this.height;
    }

    /**
     * Retrieves the growth rate of the tree.
     *
     * @return The growth rate of the tree.
     */
    public double getGrowthRate(){
        return this.growthRate;
    }

    /**
     * Retrieves the year of planting of the tree.
     *
     * @return The year of planting of the tree.
     */
    public int getYearOfPlanting(){
        return this.yearOfPlanting;
    }

    /**
     * Returns a string representation of the tree object for the reaping method
     *
     * @return A string containing the tree's species, year of planting, height, and growth rate.
     */
    public String getReapingFormat(){
        return String.format("%s %d %.2f' %.1f%%",
                species, yearOfPlanting, height, growthRate * 100);
    }

    /**
     * Returns a string representation of the tree object.
     *
     * @return A string containing the tree's species, year of planting, height, and growth rate.
     */
    public String toString(){
        return String.format("Species: %s, Year of Planting: %d, Height: %.2f', Growth Rate: %.1f%%",
                species, yearOfPlanting, height, growthRate * 100);
    }

}
