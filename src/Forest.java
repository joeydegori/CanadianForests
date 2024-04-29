import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Forest implements Serializable {
    private String forestName;
    ArrayList<Tree> trees = new ArrayList<>();

    /**
     * Initializes the information for forest
     */
    public Forest(String forestName){
        this.forestName = forestName;
        this.trees = new ArrayList<>(); // Initialize the list of trees
    }

    /**
     * Getter method to get the name of the forest.
     *
     * @return The name of the forest.
     */
    public String getForestName(){
        return this.forestName;
    }

    /**
     * Displays the details of the forest and its trees.
     */
    public void displayForest() {
        double averageHeight;
        System.out.println("Forest name: " + this.forestName);

        //Iterate over each tree
        for (Tree tree: this.trees) {
            //Print details of each tree
            System.out.printf("     %-6s %d  %.2f'  %.1f%%\n",
                    tree.getSpecies().toString(),
                    tree.getYearOfPlanting(),
                    tree.getHeight(),
                    tree.getGrowthRate() * 100);
        }

        averageHeight = calculateAverageHeight();
        System.out.printf("There are %d trees, with an average height of %.2f\n", this.trees.size(), averageHeight);
    }//End of displayForest method

    /**
     * Calculates the average height of the trees in the forest.
     *
     * @return The average height of the trees, or 0 if the forest is empty.
     */
    private double calculateAverageHeight(){
        double totalHeight = 0;
        //If forest is empty
        if(this.trees.isEmpty()){
            return 0;
        }
        //Iterate over each tree in forest
        for(Tree tree: this.trees){
            //Add height of each tree to total
            totalHeight += tree.getHeight();
        }
        return totalHeight/this.trees.size();
    }//End of calculateAverageHeight method

    /**
     * Reads tree data from a CSV file and adds the trees to the forest.
     *
     * @param csvFile The CSV file containing tree data.
     */
    public void readTreesFromCSV(File csvFile){
        try {
            //Creates scanner object
            Scanner scanner = new Scanner(csvFile);

            //Loop through each line in file
            while (scanner.hasNextLine()){
                String line = scanner.nextLine(); //Reads nextLine from file
                String[] data = line.split(","); //Splits line by commas, get individual data

                //If line has four data fields
                if (data.length == 4){
                    //Converts (first field) species to TreeSpecies enum value (trim whitespace, convert to uppercase)
                    TreeSpecies species = TreeSpecies.valueOf(data[0].trim().toUpperCase());
                    //Parse (second field) yearOfPlanting as an int
                    int yearOfPlanting = Integer.parseInt(data[1].trim());
                    //Parse (third field) height as double
                    double height = Double.parseDouble(data[2].trim());
                    //Parse (fourth field) growth rate as double then to decimal
                    double growthRate = Double.parseDouble(data[3].trim()) / 100.0;

                    //New Tree object with parsed data
                    Tree tree = new Tree(species, height, growthRate, yearOfPlanting);

                    //Add new tree to tree ArrayList
                    trees.add(tree);
                } else {
                    System.out.println("Invalid data format in CSV file: " + line);
                }
            }//End of while loop
            scanner.close();

        } catch (FileNotFoundException e) {
            //If no CSV file is found
            System.out.println("Error reading CSV file: " + csvFile.getName());
            System.out.println(e.toString());
            e.printStackTrace(System.out);
        }
    }//End of readTreesFromCSV method

    /**
     * Cuts down a tree at the specified index in the forest.
     *
     * @param index The index of the tree to be cut down.
     */
    public void cutTreeByIndex(int index) {
        //Check if index is valid
        if (index >= 0 && index < trees.size()) {
            //Remove tree at specified index in Arraylist
            trees.remove(index);
        } else {
            System.out.println("Invalid tree index.");
        }
    } //End of cutTreeByIndex method

    /**
     * Adds a new randomly generated tree to the forest.
     */
    public void addRandomTree(){
        //Generate random species
        TreeSpecies[] species = TreeSpecies.values();
        TreeSpecies randomSpecies = species[new Random().nextInt(species.length)];

        //Generate random year of planting
        int currentYear = Year.now().getValue();
        int randomYear = new Random().nextInt(currentYear - 2000 + 1) + 2000;

        //Generate random height
        double randomHeight = new Random().nextDouble() * 10.0 + 10.0;

        //Generate random growth
        double randomGrowthRate = new Random().nextDouble() * 0.1 + 0.1;

        //New tree with the random values
        Tree newTree = new Tree(randomSpecies, randomHeight, randomGrowthRate, randomYear);

        //Add tree to the arraylist
        trees.add(newTree);

    }//End of addRandomTree method

    /**
     * Simulates a year's growth in the current forest.
     */
    public void simulateYearlyGrowth(){
        //Iterate over each tree in forest
        for (Tree tree : trees){
            //Calculate growth for current tree
            double growth = tree.getHeight() * tree.getGrowthRate();
            //Update height of tree by adding growth
            tree.setHeight(tree.getHeight() + growth);
        }
    }

    /**
     * Reaps the current forest of trees over a specified height and replaces the reaped trees with random new trees.
     *
     * @param height The height threshold for reaping trees.
     */
    public void reapForest(double height){
        int index;
        ArrayList<Tree> newTrees = new ArrayList<>();
        ArrayList<Integer> reapedIndexes = new ArrayList<>();

        //Iterate over each tree in forest and reap
        for (index = 0; index < trees.size(); index++) {
            Tree tree = trees.get(index);

            //Check if tree's height is greater than specified
            if (tree.getHeight() > height){
                System.out.println("Reaping the tall tree  " + tree.getReapingFormat());
                reapedIndexes.add(index);
                //Generate new random tree for each reaped tree and store in newTrees
                Tree newTree = generateRandomTree();
                newTrees.add(newTree);
                System.out.println("Replaced with new tree " + newTree.getReapingFormat());
            }
        }//End of for loop

        //Replace reaped tree with new random trees at same index
        for (int i = 0; i < reapedIndexes.size(); i++){
            int reapedIndex = reapedIndexes.get(i);
            //Remove old tree and add the new one at the same index
            trees.set(reapedIndex, newTrees.get(i));
        }
    }//End of reapForest method

    /**
     * Generates a new randomly configured tree.
     * Method creates a tree with randomly chosen species, height, growth rate, and year of planting.
     * @return A new Tree object with random parameters.
     */
    private Tree generateRandomTree(){
        Random random = new Random(); //Create a new instance of Random

        //Select random species from the enum
        TreeSpecies[] species = TreeSpecies.values();
        //Get a random species using random
        TreeSpecies randomSpecies = species[random.nextInt(species.length)];
        //Get randomYear
        int randomYear = Year.now().getValue() - random.nextInt(21);
        //Random initial height
        double randomHeight = 10.0 + random.nextDouble() * 10.0;
        //Random growth rate
        double randomGrowthRate = 0.1 + random.nextDouble() * 0.1;

        //Return a new tree object
        return new Tree(randomSpecies, randomHeight, randomGrowthRate, randomYear);
    }//End of generateRandomTree method


    /**
     * Saves the current forest to a file.
     */
    public void saveForest(){
        String fileName = forestName + ".db";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            // Write the current Forest object to the file
            outputStream.writeObject(this);
        } catch (IOException e){
            // Handle any IOException
            System.out.println("Error saving forest to file: " + e.getMessage());
        }
    }//End of saveForest

    /**
     * Loads a forest from a file.
     *
     * @return The loaded Forest object or null if an error occurred.
     */
    public static Forest loadForest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter forest name: ");
        String forestName = scanner.nextLine();

        String fileName = forestName + ".db";
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            // Read the Forest object from the file
            Forest forest = (Forest) inputStream.readObject();
            System.out.println("Forest loaded successfully from " + fileName);
            return forest;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading forest from file: " + e.getMessage());
            return null;
        }
    }//End of loadForest
}//End of Forest class












