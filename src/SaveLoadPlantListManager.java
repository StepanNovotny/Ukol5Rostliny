import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveLoadPlantListManager {
    private List<Plant> plantList;

    public List<Plant> loadFromFile(String filename) throws PlantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            plantList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                plantList.add(parseLine(line));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Nepodařilo se nalézt soubor "+filename+": "+e.getLocalizedMessage());
        }
        return plantList;
    }

    public Plant parseLine(String line) throws PlantException {

        String[] parameterArray = line.split("\t");
        LocalDate wrongDate;
        Integer wrongNumber;
        try{
            wrongDate = LocalDate.parse(parameterArray[4]);
        }catch(DateTimeParseException wrongPlanted){
            throw new PlantException("Chybně zadané datum zasazení: " + wrongPlanted);
        }
        try{
            wrongDate = LocalDate.parse(parameterArray[3]);
        }catch(DateTimeParseException wrongPlanted){
            throw new PlantException("Chybně zadané datum posledního zalití: " + wrongPlanted);
        }
        try{
            wrongNumber = Integer.parseInt(parameterArray[2]);
        }catch(NumberFormatException wrongWateringFrequency){
            throw new PlantException("Chybně zadané datum posledního zalití: " + wrongWateringFrequency);
        }

        if(parameterArray.length==5){


            Plant loadedPlant = new Plant(parameterArray[0], parameterArray[1], LocalDate.parse(parameterArray[4]), LocalDate.parse(parameterArray[3]), Integer.parseInt(parameterArray[2]));
            return loadedPlant;
        }else {
            throw new PlantException("Linka v souboru má špatný počet parametrů");
        }
    }

    public void saveToFile(String filename,List<Plant> listOfPlant) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Plant plant : listOfPlant) {
                writer.println(plant.getName()+"\t"+plant.getNotes()+"\t"+plant.getFrequencyOfWattering()+"\t"+plant.getLastWattered()+"\t"+plant.getPlanted());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePlantList(String filname,Plant...newPlants) throws PlantException {
        List<Plant> plantList = loadFromFile(filname);
        plantList = addPlantToAList(plantList,newPlants);
        saveToFile(filname,plantList);
    }
    public List<Plant> addPlantToAList(List<Plant> listOfPlants,Plant...plants){
        for (Plant plant:plants) {
            listOfPlants.add(plant);
        }
        return listOfPlants;
    }

    public List<Plant> createListOfPlants(Plant...plants){
        plantList = new ArrayList<>();
        for (Plant plant:plants) {
            plantList.add(plant);
        }
        return plantList;
    }

    public Plant createPlant(String...parameters) throws PlantException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        if(parameters.length==5){
             Plant plant = new Plant(parameters[0],parameters[1], LocalDate.parse(parameters[2],dateFormatter),LocalDate.parse(parameters[3],dateFormatter),Integer.parseInt(parameters[4]));
             return plant;
        }else if(parameters.length==3){
            Plant plant = new Plant(parameters[0], LocalDate.parse(parameters[1],dateFormatter),Integer.parseInt(parameters[2]));
            return plant;
        }else if(parameters.length==1){
            Plant plant = new Plant(parameters[0]);
            return plant;
        }
        throw new PlantException("Zadal jsi špatný počet parametrů");
    }


}
