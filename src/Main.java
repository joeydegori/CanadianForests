//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;
import java.util.*;

/**
 * The Main class represents the start of the Forestry Simulation program.
 * It handles user interaction, menu options, and manages the simulation of forest growth and pruning.
 *
 * @author Joey DeGori
 * @version 1.0
 */
public class Main {
    private static Scanner keyboard = new Scanner(System.in);

    /**
     * The main method of the Forestry Simulation program.
     * It reads command-line arguments, initializes forests, and presents a menu for user interaction.
     *
     * @param args The command-line arguments representing the names of the forests to simulate.
     */
    public static void main(String[] args) {
        //if statement to check if there is any command line data
        if (args.length == 0){
            System.out.println("No forest names provided");
            return;
        } //End of if statement

        // HashMap to store the forest objects
        LinkedHashMap<String, Forest> forests = new LinkedHashMap<>();

        //For loop to check for csv files
        for (String forestName : args){
            //Create a new forest object
            Forest forest = new Forest(forestName);

            //File object
            File csvFile  = new File(forestName + ".csv");
            if (csvFile.exists()){
                forest.readTreesFromCSV(csvFile);
            }

            //Store the forest in hashmap
            forests.put(forestName, forest);
        }// End of for loop

        //Check if there are forests in Hashmap
        if (!forests.isEmpty()){
            //Get first forest
            Forest currentForest = forests.values().iterator().next();

            //User input loop
            while (true){
                System.out.print("\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it: ");
                String choice = keyboard.nextLine().toUpperCase();

                switch (choice) {
                    case "P":
                        currentForest.displayForest();
                        break;
                    case "A":
                        currentForest.addRandomTree();
                        break;
                    case "C":
                        while(true){
                            System.out.print("Tree number to cut down: ");
                            String input = keyboard.nextLine();
                            try {
                                int indexToCut = Integer.parseInt(input);
                                if (indexToCut >= 0 && indexToCut < currentForest.trees.size()){
                                    currentForest.cutTreeByIndex(indexToCut);
                                    break;
                                } else {
                                    System.out.println("Tree number " + indexToCut + " does not exist");
                                    break;
                                }
                            } catch (NumberFormatException e){
                                System.out.println("Invalid input. Please enter a valid number.");
                            }
                        }
                        break;
                    case "G":
                        currentForest.simulateYearlyGrowth();
                        break;
                    case "R":
                        while(true){
                            System.out.print("Height to reap from: ");
                            String input = keyboard.nextLine();

                            try {
                                double heightToReap = Double.parseDouble(input);
                                if (heightToReap >= 0){
                                    currentForest.reapForest(heightToReap);
                                    break;
                                } else {
                                    System.out.println("Invalid height. Please enter a positive number.");
                                }
                            } catch (NumberFormatException e){
                                System.out.println("Invalid input. Please enter a valid number. ");
                            }
                        }
                        break;
                    case "S":
                        currentForest.saveForest();
                        break;
                    case "L":
                        System.out.print("Enter forest name: ");
                        String forestToLoad = keyboard.nextLine();
                        if (forests.containsKey(forestToLoad)) {
                            currentForest = forests.get(forestToLoad);
                        } else {
                            System.out.println("Error opening/reading " + forestToLoad + ".db");
                            System.out.println("Old forest retained");
                        }
                        break;
                    case "N":
                        String currentForestName = currentForest.getForestName();
                        String nextForestName = getNextForestName(currentForestName, args);

                       if (nextForestName != null) {
                            System.out.println("Moving to the next forest");
                            System.out.println("Initializing from " + nextForestName);

                            File csvFile = new File(nextForestName + ".csv");
                            if (csvFile.exists()){
                                currentForest = forests.get(nextForestName);
                            } else {
                                System.out.println("Error opening/reading " + nextForestName + ".csv");

                                while (!csvFile.exists()){
                                    nextForestName = getNextForestName(nextForestName, args);
                                    if (nextForestName == null) {
                                        System.out.println("No more forests to process.");
                                        break;
                                    }
                                    csvFile = new File(nextForestName + ".csv");
                                }

                                if (nextForestName != null ) {
                                    System.out.println("Initializing from " + nextForestName);
                                    currentForest = forests.get(nextForestName);
                                }
                            }
                       } else {
                            System.out.println("No more forests to process.");
                        }
                        break;
                    case "X":
                        System.out.println("Exiting the Forestry Simulation");
                        System.exit(0);
                    default:
                        System.out.println("Invalid menu option, try again");
                }// End of switch
            }// End of if statement
        } else {
            System.out.println("No valid forests found.");
            return;
        }
    }//End of main method

    /**
     * Retrieves the name of the next forest in the set of forest names.
     *
     * @param currentForestName The name of the current forest.
     * @param forestNames The set of all forest names.
     * @return The name of the next forest, or null if there are no more forests.
     */
    private static String getNextForestName(String currentForestName, String[] forestNames) {
        // Find the index of the current forest name
        int currentIndex = Arrays.asList(forestNames).indexOf(currentForestName);

        // Checks if the current forest is not the last forest
        if (currentIndex != -1) {
            // Return the next forest name (wrap around if necessary)
            return forestNames[(currentIndex + 1) % forestNames.length];
        }
        return null;
    }

}
