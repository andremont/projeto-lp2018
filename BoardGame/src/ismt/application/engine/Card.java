package ismt.application.engine;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends ImageView {
	public int cardRank, cardPoints;
	public Suit cardSuit;
	public Image card_faceup_image;
	protected final String resourceFolder = new File("resource/playing_cards_images").toURI().toString();
	
	// face-up = card suit and rank are visible
	// face-down = card suit and rank are not visible
	// boolean this_card_is_face_up = false ;

	public static final int CARD_WIDTH = 150;
	public static final int CARD_HEIGHT = 215;

	public Card(){
		cardRank = 0;
		cardSuit = Suit.values()[new Random().nextInt(4)];
		cardPoints = 0;
	}
	
	public Card(int given_card_rank, String given_card_suit) {
		cardRank = given_card_rank;
		cardSuit = Suit.valueOf(given_card_suit);
		cardPoints = 0;
		// A reference to an Image object will be retrieved by using
		// a string as a key. For example, with the string "spades1" the
		// image of the Ace of Spades is found.
		ImageStore.card_face_images = new HashMap<String, Image>();
		Image temp = ImageStore.card_face_images.get(given_card_suit + cardRank);
		
		if (temp != null)
			card_faceup_image = temp;
		else
			card_faceup_image = ImageStore.card_back_image;
		
		setImage(ImageStore.card_back_image); // Initially the card is face-down
	}
	
	public Card(int given_card_rank, Suit given_card_suit) {
		cardRank = given_card_rank;
		cardSuit = given_card_suit;
		cardPoints = 0;
		// A reference to an Image object will be retrieved by using
		// a string as a key. For example, with the string "spades1" the
		// image of the Ace of Spades is found.
		Image temp = ImageStore.card_face_images.get(cardSuit.toString() + cardRank);
		
		if (temp != null)
			card_faceup_image = temp;
		else
			card_faceup_image = ImageStore.card_back_image;
		
		setImage(ImageStore.card_back_image); // Initially the card is face-down
	}
	
	public Card(int given_card_rank, int given_card_points, Suit given_card_suit) {
		cardRank = given_card_rank;
		cardSuit = given_card_suit;
		cardPoints = given_card_points;
		// A reference to an Image object will be retrieved by using
		// a string as a key. For example, with the string "spades1" the
		// image of the Ace of Spades is found.
		card_faceup_image = ImageStore.card_face_images.get(cardSuit.toString() + cardRank);
		
		setImage(ImageStore.card_back_image); // Initially the card is face-down
	}

	public int getRank() {
		return cardRank;
	}
	
	public void setRank(int rank) {
		this.cardRank = rank;
	}

	public Suit getSuit() {
		return cardSuit;
	}
	
	public void setSuit(Suit suit) {
		this.cardSuit = suit;
	}
	
	public int getPoints() {
		return cardPoints;
	}

	public void setPoints(int cardPoints) {
		this.cardPoints = cardPoints;
	}

	public void turn_card() {
		if (getImage() == card_faceup_image) {
			setImage(ImageStore.card_back_image);
		} else {
			setImage(card_faceup_image);
		}
	}

	public void turn_card_face_up() {
		setImage(card_faceup_image);
	}

	public void turn_card_face_down() {
		setImage(ImageStore.card_back_image);
	}

	public boolean card_is_face_up() {
		return (getImage() == card_faceup_image);
	}

	public boolean card_is_face_down() {
		return (getImage() != card_faceup_image);
	}

	public void set_card_position(double given_position_x, double given_position_y) {
		setX(given_position_x);
		setY(given_position_y);
	}

	public double get_card_position_x() {
		return getX();
	}

	public double get_card_position_y() {
		return getY();
	}

	// With the following methods it is possible to compare
	// "this" card to another card.

	// Making general comparisons between cards is somewhat
	// difficult as different card games value cards in
	// different ways. All the following methods are not
	// suitable for all card games. One known problem is that
	// an Ace is considered the smallest card by the methods.

	public boolean belongs_to_suit_of(Card another_card) {
		return (cardSuit == another_card.cardSuit);
	}

	public boolean does_not_belong_to_suit_of(Card another_card) {
		return (cardSuit != another_card.cardSuit);
	}

	public boolean is_smaller_than(Card another_card) {
		return (cardRank < another_card.cardRank);
	}

	public boolean is_greater_than(Card another_card) {
		return (cardRank > another_card.cardRank);
	}

	public boolean has_equal_rank_as(Card another_card) {
		return (cardRank == another_card.cardRank);
	}

	public boolean has_different_rank_as(Card another_card) {
		return (cardRank != another_card.cardRank);
	}

	public boolean contains_point(double given_point_x, double given_point_y) {
		// We'll think here that if a Card's position is ( 0, 0 )
		// then no real position has been set, i.e., the Card
		// has not yet been put to the 'table'.

		return (getX() != 0 && getY() != 0 && given_point_x >= getX() && given_point_x <= getX() + CARD_WIDTH
				&& given_point_y >= getY() && given_point_y <= getY() + CARD_HEIGHT);
	}
	
	public Node generateCardImage() {
        
        Rectangle bg = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        bg.setArcWidth(20);
        bg.setArcHeight(20);
        bg.setFill(Color.WHITE);

        Text text1 = new Text(cardRank + "");
        text1.setFont(Font.font(18));
        text1.setX(CARD_WIDTH - text1.getLayoutBounds().getWidth() - 10);
        text1.setY(text1.getLayoutBounds().getHeight());

        Text text2 = new Text(text1.getText());
        text2.setFont(Font.font(18));
        text2.setX(10);
        text2.setY(CARD_HEIGHT - 10);

        String cardFile = resourceFolder + "/" + cardSuit.name()  + ".png";
        Image cardSuitImage = new Image(cardFile, 32, 32, true, true);
        ImageView view = new ImageView(cardSuitImage);
        
        view.setRotate(180);
        view.setX(CARD_WIDTH - 32);
        view.setY(CARD_HEIGHT - 32);
        Group main_group_for_cards = new Group();
        main_group_for_cards.getChildren().addAll(bg, new ImageView(cardSuitImage), view, text1, text2);
        
        return main_group_for_cards;
    }
}
