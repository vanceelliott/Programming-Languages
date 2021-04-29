package com.elliott;

import java.util.ArrayList;

public class Environment {
    // ------------ Instance Variables ------------
    private Environment parent;

    ArrayList<Lexeme> identifiers;
    ArrayList<Lexeme> values;

    // --------------- Constructor ----------------
    public Environment(Environment parent) {
        this.parent = parent;
        this.identifiers = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    // -------- Public Environment Methods --------
    public void insert(Lexeme identifier, Lexeme value) {
        identifiers.add(identifier);
        values.add(value);
    }

    public void update(Lexeme target, Lexeme newValue) {
        for (int i = 0; i < identifiers.size(); i++) {
            if (identifiers.get(i).equals(target)) {
                values.set(i, newValue);
                return;
            }
        }
        if (parent != null) parent.update(target, newValue);
        else
            Chivalry.error(target, "Variable " + target.getStringValue() + "is undefined and therefore cannot be updated.");

    }

    public Lexeme retrieve(Lexeme target) {
        Lexeme value = softRetrieve(target);
        if (value != null) return value;
        Chivalry.error(target, "Variable " + target.toString() + " is undefined.");
        return null;
    }

    // ------------- Helper Methods ---------------
    public Lexeme softRetrieve(Lexeme target) {
        for (int i = 0; i < identifiers.size(); i++) {
            if (identifiers.get(i).equals(target))
                return values.get(i);
        }
        if (parent != null) return parent.softRetrieve(target);
        else return null;
    }

    // ---------------- toString ------------------
    public String toString() {
        System.out.print("Environment hashcode: " + hashCode());
        if (parent == null) {
            System.out.println(", no parent.");
        } else {
            System.out.println(", Parent hashcode: " + parent.hashCode());
        }
        for (int i = 0; i < identifiers.size(); i++) {
            System.out.print(identifiers.get(i) + " - ");
            System.out.println(values.get(i));
        }
        return "";
    }

    public Lexeme killSquire() {
        int index = (int) (Math.random() * identifiers.size());
        if (index == identifiers.size()) {
            index = 0;
        }
        Lexeme returnVal = identifiers.get(index);
        identifiers.remove(index);
        values.remove(index);
        return returnVal;
    }

    public boolean checkIdents() {
        if (identifiers.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
