package ismt.application.scene;

import java.util.HashMap;
import ismt.application.engine.Card;
import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.engine.ImageStore;
import ismt.application.engine.Suit;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MemorizeScene extends CardGame{

	final String imageFolder = resourceFolder + "/playing_cards_images/";
	StackPane root_stack = new StackPane();
	Text initial_instructions = new Text("Click the cards after dealing");
	Group row_of_cards = new Group(), group_for_lonesome_card = new Group();
	Card lonesome_card, selected_card;

	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {
		
		ImageStore.card_back_image = new Image(imageFolder + "card_back.png");
		ImageStore.card_face_images = new HashMap<String, Image>();

		for (int suit_index = 0; suit_index < Suit.values().length; suit_index++) {
			for (int card_rank = 1; card_rank < 14; card_rank++) {
				String image_file_name = imageFolder + Suit.values()[suit_index] + card_rank + ".png";
				Image card_faceup_image = new Image(image_file_name);
				String key_for_image = Suit.values()[suit_index].toString() + card_rank;
				ImageStore.card_face_images.put(key_for_image, card_faceup_image);
			}
		}
		// Now, that we have loaded the card images, we can create the deck.
		card_deck = new CardDeck();

		// Next, we'll create two buttons and specify the actions that will be performed when the buttons are pressed.
		Button button_to_deal_cards = new Button("Deal");
		Button button_to_shuffle_deck = new Button("Shuffle");
		Button button_to_back = new Button("Back");
		
		button_to_deal_cards.setOnAction((ActionEvent event) -> {
			deal();
		});

		button_to_shuffle_deck.setOnAction((ActionEvent event) -> {
			shuffle();
		});
		
		button_to_back.setOnAction((ActionEvent event) -> {
				primaryStage.setScene(sceneMain);
		});

		HBox pane_for_buttons = new HBox(16); // space between buttons is 16
		pane_for_buttons.getChildren().addAll(button_to_deal_cards, button_to_shuffle_deck, button_to_back);
		pane_for_buttons.setAlignment(Pos.CENTER); // The Box is centered
		// With an Insets object we can specify empty space around the HBox.
		// There will be 20 pixels padding below the HBox.
		pane_for_buttons.setPadding(new Insets(0, 0, 20, 0));

		BorderPane border_pane = new BorderPane();
		border_pane.setBottom(pane_for_buttons);
		Group main_group_for_cards = new Group();

		// With the following statement we disable the automatic layout
		// management of the Card objects.
		main_group_for_cards.setManaged(false);
		main_group_for_cards.getChildren().addAll(row_of_cards, group_for_lonesome_card);
		initial_instructions.setFont(new Font(24));
		root_stack.getChildren().addAll(border_pane, main_group_for_cards, initial_instructions);
		
		Scene tempScene = new Scene(root_stack, APP_WIDTH, APP_HEIGHT);
		tempScene.setOnMouseClicked((MouseEvent event) -> {
			double clicked_point_x = event.getSceneX();
			double clicked_point_y = event.getSceneY();

			if (row_of_cards.getChildren().size() == 5) {
				for (Node card_as_node : row_of_cards.getChildren()) {
					Card card_in_row = (Card) card_as_node;

					if (card_in_row.contains_point(clicked_point_x, clicked_point_y)) {
						card_in_row.turn_card();

						// selected_card will point to the clicked card.
						// In this program, however, selected_card is not
						// used for any purpose.
						selected_card = card_in_row;
					}
				}
				if (lonesome_card != null && lonesome_card.contains_point(clicked_point_x, clicked_point_y)) {
					lonesome_card.turn_card();
				}
			}
		});
		// By eliminating the background specifications of the StackPane, we can make the fill of the Scene visible.
		root_stack.setBackground(null);
		tempScene.getStylesheets().add(resourceFolder + "/play.css");

		return tempScene;
	}

	@Override
	public void deal() {
		// Before the fist dealing of cards we'll remove the Text from the screen.
		if (initial_instructions != null) {
			root_stack.getChildren().remove(initial_instructions);
			initial_instructions = null;
		}
		// We'll first empty the list 'inside' the Group
		row_of_cards.getChildren().clear();

		for (int card_index = 0; card_index < 5; card_index++) {
			Card new_card = card_deck.draw_card();
			double card_position_x = 40 + (Card.CARD_WIDTH + 20) * card_index;
			double card_position_y = 50;

			new_card.set_card_position(card_position_x, card_position_y);
			row_of_cards.getChildren().add(new_card);
		}
		lonesome_card = card_deck.draw_card();
		lonesome_card.set_card_position(188, 300);

		group_for_lonesome_card.getChildren().clear();
		group_for_lonesome_card.getChildren().add(lonesome_card);
	}

	@Override
	public void shuffle() {
		card_deck.shuffle();
		deal();
	}

	
}
