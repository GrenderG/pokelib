package models;

public class Specie {
    private int index;
    private int sprite;
    private String name;

    public Specie() {
        // empty
    }

    public Specie(int index, int sprite, String name) {
        this.index = index;
        this.sprite = sprite;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }
}
