package ismt.application.scene;



import java.io.IOException;
import java.util.ArrayList;

import ismt.application.engine.Card;
import ismt.application.engine.Player;
import ismt.application.engine.PlayerStats;
import ismt.application.engine.Utils;
import ismt.application.engine.Stats;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;


public class StatsScene {

	ArrayList<Player> listaPlayers;

	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {

		EventHandler<ActionEvent> buttonBackhandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(sceneMain);
			}
		};
		Scene tempScene = new Scene(createContent(buttonBackhandler));

		return tempScene;
	}

	private ObservableList<PlayerStats> getUserList() {
		
		ObservableList<PlayerStats> list = FXCollections.observableArrayList();
		try {
	
			this.listaPlayers = Utils.getAllPlayers();
			for(Player player: this.listaPlayers) {


				String playerName  = player.getName();
				Stats stats = Utils.GetUserRecords(playerName);
				player.setStats(stats);

				
				int playerVictories = player.getStats().getVictories();
				int playerDraws = player.getStats().getDraws();
				int playerLosses = player.getStats().getLosses();
				int playerMoney = player.getMoney();
				int playerPoints = player.getPoints();
				
				PlayerStats playerStats = new PlayerStats(
						playerName, playerVictories, playerDraws,
						playerLosses, playerMoney, playerPoints);
				
				playerStats.setName(playerName);
				playerStats.setVictories(playerVictories);
				playerStats.setDraws(playerDraws);
				playerStats.setLosses(playerLosses);
				
				list.add(playerStats);
				// debug print to console 
				System.out.println("Player: "+ playerName +
						" vitorias: "+ playerVictories + " empates: "+ playerDraws + 
						" derrotas: "+ playerLosses + " money: " + playerMoney +
						" pontuação: " + playerPoints);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return list;

	}

	private Parent createContent(EventHandler<ActionEvent> buttonBackhandler) {


		TableView<PlayerStats> tablePlayer = new TableView<PlayerStats>();

		TableColumn<PlayerStats, String> nameCol //
		= new TableColumn<PlayerStats, String>("Username");
		nameCol.setCellValueFactory(
				new PropertyValueFactory<>("name"));
		nameCol.setSortType(TableColumn.SortType.DESCENDING);
		
		TableColumn<PlayerStats, Integer> victoriesCol //
		= new TableColumn<PlayerStats, Integer>("Vitórias");
		victoriesCol.setCellValueFactory(
				new PropertyValueFactory<>("victories"));
		
		TableColumn<PlayerStats, Integer> drawsCol //
		= new TableColumn<PlayerStats, Integer>("Empates");
		drawsCol.setCellValueFactory(
				new PropertyValueFactory<>("draws"));
		
		TableColumn<PlayerStats, Integer> lossesCol //
		= new TableColumn<PlayerStats, Integer>("Derrotas");
		lossesCol.setCellValueFactory(
				new PropertyValueFactory<>("losses"));

		TableColumn<PlayerStats, Integer> moneyCol //
		= new TableColumn<PlayerStats, Integer>("Money");
		moneyCol.setCellValueFactory(
				new PropertyValueFactory<>("money"));
		
		TableColumn<PlayerStats, Integer> pointsCol //
		= new TableColumn<PlayerStats, Integer>("Points");
		pointsCol.setCellValueFactory(
				new PropertyValueFactory<>("points"));
		
		tablePlayer.getColumns().addAll(
				nameCol ,victoriesCol, drawsCol, 
				lossesCol, moneyCol, pointsCol);
		
		tablePlayer.setItems(getUserList());

		
		Pane root = new Pane();
		root.setPrefSize(800, 600);

		Region background = new Region();
		background.setPrefSize(800, 600);
		background.setStyle("-fx-background-color: rgba(111, 111, 111, 1)");

		Button buttonBack = new Button("Back");
		buttonBack.setOnAction(buttonBackhandler);
		buttonBack.setLayoutX(440);
		buttonBack.setLayoutY(400);

		root.getChildren().addAll(tablePlayer, buttonBack);
	

		return root;
	}

}