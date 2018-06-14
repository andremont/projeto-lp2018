package ismt.application.scene;


import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;

import ismt.application.engine.Player;
import ismt.application.engine.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayersScene {
	
	final static int APP_WIDTH = 800;
	final static int APP_HEIGHT = 600;
	final static int BUTTON_SIZE = 100;
	final static int GAP_SIZE = 10;
	ArrayList<Player> playersData = new ArrayList<Player>();
	
	public PlayersScene() {
		
	}
	
	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {

		try {
			playersData = Utils.getAllPlayers();
			
			/*for(Player player : playersData)
			{
				player.setPoints(Utils.GetUser(player.getName()).getPoints());
			}*/
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//constução da tabale
		TableView<Player>tablePlayer = new TableView<Player>();
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn rankCol = new TableColumn("Rank");
		rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
		TableColumn suitCol = new TableColumn("Suit");
		suitCol.setCellValueFactory(new PropertyValueFactory<>("suit"));
		TableColumn pointsCol = new TableColumn("Points");
		pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
		TableColumn moneyCol = new TableColumn("Money");
		moneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));
		
		//carregar dados
		ObservableList<Player> items = FXCollections.observableArrayList(playersData);
		tablePlayer.setItems(items);
		tablePlayer.getColumns().addAll(nameCol, rankCol, suitCol, pointsCol,moneyCol);
		
		
		EventHandler<ActionEvent> buttonBackhandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(sceneMain);
			}
		};
		
		
		Button btnBack = new Button("Back");
		btnBack.setOnAction(buttonBackhandler);
		VBox vbBtnBack = new VBox(BUTTON_SIZE);
		vbBtnBack.setSpacing(5);
		vbBtnBack.setAlignment(Pos.CENTER);
		vbBtnBack.setTranslateY(5);
		
		GridPane grid = new GridPane();
		grid.add(vbBtnBack, 1, 4);
		Scene tempScene = new Scene(grid, APP_WIDTH, APP_HEIGHT);
		grid.setStyle("-fx-background-color: rgba(111, 111, 111, 1)");
		//tempScene.getStylesheets().add("/BoardGame/resource/style.css");
		
		
//-----------------Criação dos botoes-----------------------------------------------------------------------------------
		Button btnListaPlayers = new Button("Visualizar Jogadores");
		btnListaPlayers.setTooltip(new Tooltip("Listar todos os resultados na tabela resumo dos jogadores."));
		
		Button btnOrdenarNamePlayers = new Button("Ordenar por Nome");
		btnOrdenarNamePlayers.setTooltip(new Tooltip("Ordenar por Nome."));
		
		Button btnPlayersPontosOrdenar = new Button("Ordenar por Pontos");
		btnPlayersPontosOrdenar.setTooltip(new Tooltip("Ordenar jogadores com por pontuação."));
		
		Button btnMoneyOrdenar = new Button("Ordenar por Dinheiro");
		btnMoneyOrdenar.setTooltip(new Tooltip("Ordenar por Money"));
		
		Button btnLimparPlayer = new Button("Limpar Pesquisa");
		btnLimparPlayer.setTooltip(new Tooltip("Limpar Pesquisa"));
		
		Button btnFindPlayers = new Button("Pesquisar Jogadores");
		btnFindPlayers.setTooltip(new Tooltip("Utilize a caixe de texto para pesquisar jogadores por nome."));
		
		btnBack.setTooltip(new Tooltip("Voltar ao Menu Principal."));
		tablePlayer.setTooltip(new Tooltip("Tabela resumo dos jogadores"));
		
//----------------------------------------------------------------------------------------------------------------------------		
		TextField txtFindPlayers = new TextField();
		txtFindPlayers.setPromptText("Pesquisar...");
		txtFindPlayers.setPrefColumnCount(10);
		txtFindPlayers.getText();
		txtFindPlayers.setTooltip(new Tooltip("Pesquisar jogadores por nome ou por pontuação."));
		//Scene tempScene = new Scene(createContent(buttonBackhandler));
		
		TextField txtName = new TextField();
		txtName.setPromptText("Name");
		//TextField txtRank = new TextField();
		//txtRank.setPromptText("Rank");
		//TextField txtSuit = new TextField();
		//txtSuit.setPromptText("Suit");
		TextField txtPoints = new TextField();
		txtPoints.setPromptText("Points");
		TextField txtMoney = new TextField();
		txtMoney.setPromptText("Money");
		

//--------------- botao Pesquisa----------------------------------------------------------------------------------------------------
		btnFindPlayers.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					tablePlayer.refresh();
					btnFindPlayers.setDisable(false);
					vbBtnBack.getChildren().addAll(btnLimparPlayer);
					ArrayList<Player> pesquisa = new ArrayList<Player>();
					for (Player p : playersData)
						if (p.getName().toLowerCase().contains(txtFindPlayers.getText()))
							pesquisa.add(p);
					// btnFindPlayers.setDisable(true);
					ObservableList<Player> items = FXCollections.observableArrayList(pesquisa);
					tablePlayer.setItems(items);
					txtFindPlayers.requestFocus();
				}
			}
		});
		
//------------------botao Ordenar por nome-------------------------------------------------------------------------------------------		 
		btnOrdenarNamePlayers.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					ObservableList<Player> items = FXCollections.observableArrayList(playersData);
					tablePlayer.getColumns().removeAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.setItems(items);
					tablePlayer.getColumns().addAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.getSortOrder().add(nameCol);
				}
			}
		});
		
		
//----------------------botao Ordenar por pontos--------------------------------------------------------------------------------------------		
		btnPlayersPontosOrdenar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					ObservableList<Player> items = FXCollections.observableArrayList(playersData);
					tablePlayer.getColumns().removeAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.setItems(items);
					tablePlayer.getColumns().addAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.getSortOrder().add(pointsCol);
				}
			}
		});
		
		
//---------------------Botao Ordenar Por Dinheiro--------------------------------------------------------------------------------------------
		btnMoneyOrdenar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					ObservableList<Player> items = FXCollections.observableArrayList(playersData);
					tablePlayer.getColumns().removeAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.setItems(items);
					tablePlayer.getColumns().addAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.getSortOrder().add(moneyCol);
				}
			}
		});
				
				
//---------------------Botao Limpar---------------------------------------------------------------------------------------------------------------------		
		btnLimparPlayer.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					ObservableList<Player> items = FXCollections.observableArrayList(playersData);
					tablePlayer.getColumns().removeAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.setItems(items);
					tablePlayer.getColumns().addAll(nameCol, rankCol, suitCol, pointsCol, moneyCol);
					tablePlayer.getSortOrder().add(nameCol);
					vbBtnBack.getChildren().removeAll(btnLimparPlayer);
					txtFindPlayers.setText(null);
				}
			}
		});

//---------------------Tabela individual------------------------------------------------------------------------------------------------------------------------------------
				
		/*tablePlayer.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					txtName.setText("Name: " + tablePlayer.getSelectionModel().getSelectedItem().getName());
					txtName.setTooltip(new Tooltip("Name: " + tablePlayer.getSelectionModel().getSelectedItem().getName()));
					//txtRank.setText("Rank: " + tablePlayer.getSelectionModel().getSelectedItem().getRank());
					//txtRank.setTooltip(new Tooltip("Rank: " + tablePlayer.getSelectionModel().getSelectedItem().getRank()));
					//txtSuit.setText("Suit: " + tablePlayer.getSelectionModel().getSelectedItem().getSuit());
				//	txtSuit.setTooltip(new Tooltip("Suit: " + tablePlayer.getSelectionModel().getSelectedItem().getSuit()));
					txtPoints.setText("Points: " + tablePlayer.getSelectionModel().getSelectedItem().getPoints());
					txtPoints.setTooltip(new Tooltip("Points: " + tablePlayer.getSelectionModel().getSelectedItem().getPoints()));
					txtMoney.setText("Money: " + tablePlayer.getSelectionModel().getSelectedItem().getMoney());
					txtMoney.setTooltip(new Tooltip("Money: " + tablePlayer.getSelectionModel().getSelectedItem().getMoney()));
					tablePlayer.setItems(items);
				}
			}
		});		*/
		
		
		
//-------------- Adicionar elementos interface --------------------------------------------------------------------------------------------------------------------
		
		vbBtnBack.getChildren().addAll(tablePlayer, txtFindPlayers);
		vbBtnBack.getChildren().addAll(btnFindPlayers, btnOrdenarNamePlayers, btnMoneyOrdenar, btnPlayersPontosOrdenar, btnBack);	
		
		return tempScene;
	}
		


	
}

