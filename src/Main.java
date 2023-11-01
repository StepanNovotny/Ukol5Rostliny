import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PlantException {
        SaveLoadPlantListManager saveLoadPlantListManager = new SaveLoadPlantListManager();

        Plant plant = new Plant("Fialka 1","Popis fialky - je fialová a hezká",LocalDate.of(2021,01,01), LocalDate.of(2021,05,12),3);
        Plant plant2 = new Plant("Vánoční hvězda bez poznamky", LocalDate.of(2021,05,01),4);
        Plant plant3 = new Plant("Bazalka v kuchyni",LocalDate.of(2021,04,01),3);
        Plant plant4 = new Plant("Pampeliška",LocalDate.of(2023,10,13),5);
        Plant plant5 = new Plant("Tulipán");

        List<Plant> plantList = saveLoadPlantListManager.createListOfPlants(plant,plant2,plant3);
        saveLoadPlantListManager.saveToFile("list1.txt",plantList);

        System.out.println("Načtení ze souboru");
        List<Plant> plantListLoaded = saveLoadPlantListManager.loadFromFile("list1.txt");
        System.out.println(plantListLoaded);
        System.out.println("\n");
        System.out.println("Květiny seřazené podle jména");
        Collections.sort(plantListLoaded, new Comparator<Plant>() {
            @Override
            public int compare(Plant p1, Plant p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        System.out.println(plantListLoaded);
        System.out.println("\n");
        System.out.println("Vypsané informace o květině");
        System.out.println(plant3.getWatteringInfo());
        System.out.println("\n");

        System.out.println("Květiny seřazené podle data zálivky");
        Collections.sort(plantListLoaded, new Comparator<Plant>() {
            @Override
            public int compare(Plant p1, Plant p2) {
                return p1.getLastWattered().compareTo(p2.getLastWattered());
            }
        });
        System.out.println(plantListLoaded);
        System.out.println("\n");
        System.out.println("Přidání květiny do seznamu");
        saveLoadPlantListManager.updatePlantList("list1.txt", plant4,plant5);
        System.out.println(saveLoadPlantListManager.loadFromFile("list1.txt"));

    }
}