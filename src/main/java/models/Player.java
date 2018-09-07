package models;

public class Player {
    private long id;
    private String name;
    private String rivalName;
    private long money;
    private int casinoChips;
    private long timePlayedInMillis;
    private Item[] bag;
    private Item[] pcItemBox;
    private Pokemon[] partyPokemon;
    private Pokemon[] pcPokemon;

    public Player() {
        // empty
    }

    public Player(long id, String name, String rivalName, long money, int casinoChips, long timePlayedInMillis, Item[] bag, Item[] pcItemBox, Pokemon[] partyPokemon, Pokemon[] pcPokemon) {
        this.id = id;
        this.name = name;
        this.rivalName = rivalName;
        this.money = money;
        this.casinoChips = casinoChips;
        this.timePlayedInMillis = timePlayedInMillis;
        this.bag = bag;
        this.pcItemBox = pcItemBox;
        this.partyPokemon = partyPokemon;
        this.pcPokemon = pcPokemon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRivalName() {
        return rivalName;
    }

    public void setRivalName(String rivalName) {
        this.rivalName = rivalName;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getCasinoChips() {
        return casinoChips;
    }

    public void setCasinoChips(int casinoChips) {
        this.casinoChips = casinoChips;
    }

    public long getTimePlayedInMillis() {
        return timePlayedInMillis;
    }

    public void setTimePlayedInMillis(long timePlayedInMillis) {
        this.timePlayedInMillis = timePlayedInMillis;
    }

    public Item[] getBag() {
        return bag;
    }

    public void setBag(Item[] bag) {
        this.bag = bag;
    }

    public Item[] getPcItemBox() {
        return pcItemBox;
    }

    public void setPcItemBox(Item[] pcItemBox) {
        this.pcItemBox = pcItemBox;
    }

    public Pokemon[] getPartyPokemon() {
        return partyPokemon;
    }

    public void setPartyPokemon(Pokemon[] partyPokemon) {
        this.partyPokemon = partyPokemon;
    }

    public Pokemon[] getPcPokemon() {
        return pcPokemon;
    }

    public void setPcPokemon(Pokemon[] pcPokemon) {
        this.pcPokemon = pcPokemon;
    }
}
