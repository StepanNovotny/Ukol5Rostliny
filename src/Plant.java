import java.time.LocalDate;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate lastWattered;
    private int frequencyOfWattering;

    public Plant(String name, String notes, LocalDate planted, LocalDate lastWattered, int frequencyOfWattering) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.lastWattered = lastWattered;
        this.frequencyOfWattering = frequencyOfWattering;
        if(lastWattered.isBefore(getPlanted())){
            throw new PlantException("Chyba data: Květina nemohla být naposledy zalita před zasazením.");
        }
    }

    public Plant(String name, LocalDate planted, int frequencyOfWattering) throws PlantException {
        this.name = name;
        this.notes = "";
        this.planted = planted;
        this.lastWattered = LocalDate.now();
        this.frequencyOfWattering = frequencyOfWattering;
        if(planted.isAfter(getLastWattered())){
            throw new PlantException("Chyba data: Květina nemohla být zasazena po posledním zalití.");
        }
    }

    public Plant(String name) {
        this.name = name;
        this.notes = "";
        this.planted = LocalDate.now();
        this.lastWattered = LocalDate.now();
        this.frequencyOfWattering = 7;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) throws PlantException {
        if(planted.isAfter(getLastWattered())){
            throw new PlantException("Chyba data: Květina nemohla být zasazena po posledním zalití.");
        }
        this.planted = planted;
    }

    public LocalDate getLastWattered() {
        return lastWattered;
    }

    public void setLastWattered(LocalDate lastWattered)throws PlantException {
        if(lastWattered.isBefore(getPlanted())){
            throw new PlantException("Chyba data: Květina nemohla být naposledy zalita před zasazením.");
        }
        this.lastWattered = lastWattered;
    }

    public int getFrequencyOfWattering() {
        return frequencyOfWattering;
    }

    public void setFrequencyOfWattering(int frequencyOfWattering) throws PlantException {
        if(frequencyOfWattering<=0){
            throw new PlantException("Frekvence zalévání nesmí být menší nebo rovna nule");
        }
        this.frequencyOfWattering = frequencyOfWattering;
    }

    @Override
    public String toString() {
        return  "\n" + name + "\t" + notes + "\t" + frequencyOfWattering +"\t"+ lastWattered+ "\t" + planted;
    }

    public String getWatteringInfo(){
        return "\nJméno květiny: "+getName() +
                "\nNaposledy zalito: "+getLastWattered() +
                "\nZnovu zalít: "+getLastWattered().plusDays(getFrequencyOfWattering());
    }
}
