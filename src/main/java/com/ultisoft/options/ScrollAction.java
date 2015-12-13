package com.ultisoft.options;


public enum ScrollAction {


    JUMP_TOP("Oben fortsetzen"),
    REVERSE("Umkehren");

    private String label;

    ScrollAction(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
