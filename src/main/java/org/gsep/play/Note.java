package org.gsep.play;

public enum Note{
    OPEN(null),
    BLACK("/play/black.png"),
    WHITE("/play/white.png");

    private final String imageSource;

    Note(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageSource() {
        return imageSource;
    }
}