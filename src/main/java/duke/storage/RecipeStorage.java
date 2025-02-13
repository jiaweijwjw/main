package duke.storage;

import duke.model.list.recipelist.RecipeList;
import duke.model.task.recipetasks.Recipe;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Handles the ability to read and write to the storage location.
 */
public class RecipeStorage {

    private final LinkedHashMap<String, Recipe> LHMRecipeList = new LinkedHashMap<>();
    private final String filePathRecipes;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRecipes String containing the directory in which the tasks are to be stored
     */
    public RecipeStorage(String filePathRecipes) {
        this.filePathRecipes = filePathRecipes;
    }

    /**
     * Writing to file to save the task to file.
     *
     * @param recipeList contains the task list
     */
    public void saveFile(RecipeList recipeList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRecipes);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            recipeList.getRecipeList().forEach((String, Recipe) ->
            {
                try {
                    bufferedWriter.write(Recipe.toSaveString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     */

    public LinkedHashMap<String, Recipe> load() {
        try {
            FileReader fileReader = new FileReader(filePathRecipes);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                // can use a splitMethod() here for tidyness?
                String recipeTitle, prepTime, rating, prepSteps, requiredIngredients, feedback, remaining, remaining2, remaining3, remaining4;
                String[] split = content.split("\\|", 2);
                if (split.length == 2) {
                    recipeTitle = split[0].trim();
                    remaining = split[1].trim();
                    String[] split2 = remaining.split("\\|", 2);
                    if (split2.length == 2) {
                        prepTime = split2[0].trim();
                        remaining2 = split2[1].trim();
                        String[] split3 = remaining2.split( "\\|", 2);
                        if (split3.length == 2) {
                            rating = split3[0].trim();
                            remaining3 = split3[1].trim();
                            String[] split4 = remaining3.split("\\|", 2);
                            if (split4.length == 2) {
                                prepSteps = split4[0].trim();
                                remaining4 = split4[1].trim();
                                String[] split5 = remaining4.split("\\|", 2);
                                if (split5.length == 2) {
                                    requiredIngredients = split5[0].trim();
                                    feedback = split5[1].trim();
                                    Recipe recipe = new Recipe(recipeTitle, prepTime, rating, prepSteps, requiredIngredients, feedback);
                                    LHMRecipeList.put(recipeTitle, recipe);
                                }
                            }
                        }
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipes + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipes + "'");
        }
        return LHMRecipeList;
    }
}