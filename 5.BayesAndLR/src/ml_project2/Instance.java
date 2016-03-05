package ml_project2;

import java.util.ArrayList;

public class Instance {
    public int label;
    public ArrayList<String> words;

    public Instance(int label, ArrayList<String> list) {
        this.label = label;
        this.words = list;
    }

    public int getLabel() {
        return label;
    }

    public ArrayList<String> getX() {
        return words;
    }
}