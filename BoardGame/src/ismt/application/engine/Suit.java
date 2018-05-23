package ismt.application.engine;

import java.io.File;
import javafx.scene.image.Image;

public enum Suit {
	 hearts, diamonds, spades, clubs;
	
	protected final String resourceFolder = new File("resource/playing_cards_images").toURI().toString();
    final Image image;

    Suit() {
    	String cardFile = resourceFolder + "/" + name().toLowerCase() + ".png";
        this.image = new Image(cardFile, 32, 32, true, true);
    }
}
