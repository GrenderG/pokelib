package models;

public class Item {
    private int index;
    private String name;
    private int quantity;

    public Item() {
        // empty
    }

    public Item(int index, String name, int quantity) {
        this.index = index;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
