import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.image.*;
import java.io.*;
import java.lang.*;



public class View extends Application {

		public Rectangle[][] board;
		public int BOARD_HEIGHT = 18;
		public int BOARD_WIDTH = 18;
		public int[][] obstacles =
			 {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				};
		public Character hero;
		public Character badguy;
		public int SIZE_RECTANGLE = 50;
		public Node[][] graph = new Node [BOARD_WIDTH][BOARD_HEIGHT];
		public Image map = new Image(new File("../sprites/map.png").toURI().toString());


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A*");

				GridPane pane = new GridPane();

				board = new Rectangle[BOARD_WIDTH][BOARD_HEIGHT];
				for (int i = 0; i < BOARD_WIDTH; i++){
					for (int j = 0; j < BOARD_HEIGHT; j++){
						pane.add(board[i][j] = new Rectangle(SIZE_RECTANGLE*i, SIZE_RECTANGLE*j, SIZE_RECTANGLE, SIZE_RECTANGLE), i, j);
						board[i][j].setFill(Color.BLUE);
						board[i][j].setOpacity(0);
					}
				}

				for(int i=0; i<20; i++){
					int randx = (int)(Math.random()*BOARD_HEIGHT-1);
					int randy = (int)(Math.random()*BOARD_WIDTH-1);
					if(obstacles[randx][randy] == 0){
						Character	food = new Character( randy, randx , Charac.food, this);
						food.current.setTranslateX(food.x*SIZE_RECTANGLE);
						food.current.setTranslateY(food.y*SIZE_RECTANGLE - SIZE_RECTANGLE/2 );
						pane.add(food.current, 0, 0);
					}
				}

				hero = new Character(8, 3, Charac.hero, this);
				pane.add(hero.current, 0, 0);
				hero.startMoving();
				hero.current.setTranslateX(hero.x*SIZE_RECTANGLE);
		    hero.current.setTranslateY(hero.y*SIZE_RECTANGLE - SIZE_RECTANGLE/2 );


				badguy = new Character(2, 2, Charac.badguy, this);
				pane.add(badguy.current, 0, 0);
				badguy.current.setTranslateX(badguy.x*SIZE_RECTANGLE);
				badguy.current.setTranslateY(badguy.y*SIZE_RECTANGLE - SIZE_RECTANGLE);



        BorderPane root = new BorderPane();
				BackgroundImage bgi= new BackgroundImage(map,
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						new BackgroundSize(map.getWidth(), map.getHeight(), true, true, true , true));
				Scene scene = new Scene(root, 900, 900);
				root.setBackground(new Background(bgi));

				root.setCenter(pane);

				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
					 @Override
					 public void handle(KeyEvent event) {
						KeyCode code = event.getCode();
						switch (code){
							case Z:
								 hero.dir = Direction.Up;
								 break;
							case S:
								hero.dir = Direction.Down;
								break;
							case D:
								hero.dir = Direction.Right;
								break;
							case Q:
								hero.dir = Direction.Left;
								break;
						}
					}
				});


        primaryStage.setScene(scene);
        primaryStage.show();
				astar();
    }


		public void astar(){
	    KeyFrame kf= new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>(){
	      @Override
	      public void handle(ActionEvent t) {

					graph = new Node[BOARD_WIDTH][BOARD_HEIGHT];
					for(int i=0; i<BOARD_WIDTH; i++){
							for(int j=0; j<BOARD_HEIGHT; j++){
									if(obstacles[i][j] == 0)
										graph[i][j]=new Node(i,j);
							}
					}

					// Creates the neighbours
					for(int i=0; i<BOARD_WIDTH; i++){
							for(int j=0; j<BOARD_HEIGHT; j++){
									if(graph[i][j] != null){
										if(i>0 && graph[i-1][j] != null)
												graph[i][j].addNeighbour(graph[i-1][j], 1);
										if(i<BOARD_WIDTH-1 && graph[i+1][j] != null)
												graph[i][j].addNeighbour(graph[i+1][j], 1);
										if(j>0 && graph[i][j-1] != null)
												graph[i][j].addNeighbour(graph[i][j-1], 1);
										if(j<BOARD_HEIGHT-1 && graph[i][j+1] != null)
												graph[i][j].addNeighbour(graph[i][j+1], 1);
									}
							}
					}


					Node start = graph[badguy.y][badguy.x];
					Node goal = graph[hero.y][hero.x];

					clearGraph();

					Astar.calculateShortestPath(graph, start, goal, board);
					try{
						Node next = goal.path.get(1);
						badguy.moveTo(next.y, next.x);
					}catch(Exception exception){
						System.out.println("Aïe !");
					}

					




					//goal.printPath(board);
	      }
	    });
	    Timeline timeline =new Timeline(kf);
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();

		}

		void clearGraph(){
			for(int i=0; i< BOARD_WIDTH; i++){
				for(int j=0; j< BOARD_WIDTH; j++){
					board[i][j].setOpacity(0);
				}
			}
		}
}

enum Direction{
	Up,
	Down,
	Right,
	Left;
}

enum Charac{
	hero,
	badguy,
	food;
}
