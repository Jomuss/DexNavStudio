package com.joemoss.dexnavstudio;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class RetroPokemon {

    @SerializedName("moves")
     private ArrayList< JsonObject > moves;

    @SerializedName("sprites")
    private  JsonObject  sprites;

    @SerializedName("types")
    private ArrayList< JsonObject >  types;

    @SerializedName("weight")
    private int weight;

    @SerializedName("id")
    private int entryNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("abilities")
    private ArrayList<JsonObject> abilities;

    private static HashMap<String, Integer> typesTable;

    RetroPokemon(ArrayList< JsonObject > moves,  JsonObject sprites,
     ArrayList< JsonObject> types, int weight, int entryNumber, String name, ArrayList<JsonObject> abilities){
        this.entryNumber = entryNumber;
        this.moves = moves;
        this.name = name;
        this.sprites = sprites;
        this.types = types;
        this.weight = weight;
        this.abilities = abilities;
    }

    ArrayList< JsonObject >  getMoves() {
        return moves;
    }

    public void setMoves(ArrayList< JsonObject>  moves) {
        this.moves = moves;
    }

    JsonObject getSprites() {
        return sprites;
    }

    public void setSprites(JsonObject sprites) {
        this.sprites = sprites;
    }

    ArrayList< JsonObject > getTypes() {
        return types;
    }

    public void setTypes(ArrayList< JsonObject > types) {
        this.types = types;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    static String capitalize(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private static Comparator<JsonObject> moveLevelComparator = new Comparator<JsonObject>() {
        @Override
        public int compare(JsonObject o1, JsonObject o2) {
            return o1.get("levelLearned").getAsInt()-o2.get("levelLearned").getAsInt();
        }
    };


    ArrayList<JsonObject> getOrderedLevelUpMoves(){
        ArrayList<JsonObject> levelUpMoves = new ArrayList<>();
        int count = 0;
        for(int i=0; i<this.getMoves().size(); i++){
            for(int j=0; j< this.getMoves().get(i).get("version_group_details").getAsJsonArray().size(); j++){
                if(this.getMoves().get(i).get("version_group_details").getAsJsonArray().get(j).getAsJsonObject()
                 .get("version_group").getAsJsonObject().get("name").getAsString().equals("ultra-sun-ultra-moon")){
                    if(this.getMoves().get(i).get("version_group_details").getAsJsonArray().get(j).getAsJsonObject().get("move_learn_method")
                     .getAsJsonObject().get("name").getAsString().equals("level-up")
                     && (count == 0 || !(levelUpMoves).get(count - 1).get("name").getAsString().equals(this.getMoves().get(i).get("move").getAsJsonObject().get("name").getAsString()))){
                        JsonObject move = new JsonObject();
                        move.addProperty("name", this.getMoves().get(i).get("move").getAsJsonObject().get("name").getAsString());
                        move.addProperty("levelLearned", this.getMoves().get(i).get("version_group_details").getAsJsonArray()
                                .get(j).getAsJsonObject().get("level_learned_at").getAsString());
                        levelUpMoves.add(move);
//                        System.out.println(this.getMoves().get(i).get("version_group_details").getAsJsonArray().get(j));
                        levelUpMoves.get(count).addProperty("version_number", j);

                        count++;

                    }
                }

            }
        }

        Collections.sort(levelUpMoves, moveLevelComparator);
        return levelUpMoves;
    }

    int getTypeColor(String type){
        return typesTable.get(type);
    }

    static void initializeTypesTable(){
        if(typesTable == null){
            typesTable = new HashMap<>(18);
            typesTable.put("normal", 0xFFA8A77A);
            typesTable.put("fire", 0xFFEE8130);
            typesTable.put("water", 0xFF6390F0);
            typesTable.put("electric", 0xFFF7D02C);
            typesTable.put("grass", 0xFF7AC74C);
            typesTable.put("ice", 0xFF96D9D6);
            typesTable.put("fighting", 0xFFC22E28);
            typesTable.put("poison", 0xFFA33EA1);
            typesTable.put("ground", 0xFFE2BF65);
            typesTable.put("flying", 0xFFA98FF3);
            typesTable.put("psychic", 0xFFF95587);
            typesTable.put("bug", 0xFFA6B91A);
            typesTable.put("rock", 0xFFB6A136);
            typesTable.put("ghost", 0xFF735797);
            typesTable.put("dragon", 0xFF6F35FC);
            typesTable.put("dark", 0xFF705746);
            typesTable.put("steel", 0xFFB7B7CE);
            typesTable.put("fairy", 0xFFD685AD);
        }
    }


    ArrayList<JsonObject> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<JsonObject> abilities) {
        this.abilities = abilities;
    }
}




