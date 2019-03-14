package org.gsep.play;

public enum NoteShield {
    LEFT(
            "/play/leftBlackShield.png",
            "/play/leftWhiteShield.png"
    ),
    MIDDLE(
            "/play/middleBlackShield.png",
            "/play/middleWhiteShield.png"
    ),
    RIGHT(
            "/play/rightBlackShield.png",
            "/play/rightWhiteShield.png"
    );

    private final String blackImageSource;
    private final String whiteImageSource;

    NoteShield(String blackImageSource, String whiteImageSource) {
        this.blackImageSource = blackImageSource;
        this.whiteImageSource = whiteImageSource;
    }

    public String getBlackImageSource() {
        return blackImageSource;
    }

    public String getWhiteImageSource() {
        return whiteImageSource;
    }
}
