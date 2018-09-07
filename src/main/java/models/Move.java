package models;

public class Move {
    private int index;
    private String name;
    private int pp;

    public Move() {
        // empty
    }

    public Move(int index, String name, int pp) {
        this.index = index;
        this.name = name;
        this.pp = pp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
