package ir.ac.kntu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author SFathi
 */
public class JavaFXExecutable extends Application {

    private Racket racket = Racket.getInstance();
    private GameBoard gameBoard;
    private volatile boolean runningState = false;
    private int state = 0;
    private int rows = 0;
    private int cols = 0;

    private final static int LENGTH_OF_RACKET = 3;
    private final static int LENGTH_OF_CELLS = 40;
    private static ImageView mediaOffImgVu = null;
    private static ImageView mediaOnImgVu = null;
    private static Path mainMenuBackgroundPath;
    private static Path soundPath;
    private static Path soundOnImagePath;
    private static Path soundOffImagePath;
    static{
        try{
            ClassLoader thisClassLoader = JavaFXExecutable.class
                    .getClassLoader();
            mainMenuBackgroundPath = Paths.get(thisClassLoader.getResource
                    ("img/mainMenu.png").toURI());
            soundPath = Paths.get(thisClassLoader.getResource
                    ("sound/sound1.mp3").toURI());
            soundOnImagePath = Paths.get(thisClassLoader.getResource
                    ("img/soundOn.png").toURI());
            soundOffImagePath = Paths.get(thisClassLoader.getResource
                    ("img/soundOff.png").toURI());
        }catch(URISyntaxException e) {
            e.printStackTrace();
            System.out.println("can not find menu images(mainMenu.png or " +
                    "soundOn.png or soundOff.png) or sound1.mp3");
        }
    }

    /**
     *
     * @param primaryStage
     * @see Application#start(Stage)
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = mainMenu();

        primaryStage.setOnCloseRequest(event -> {
            exitAction();
        });

        primaryStage.setWidth(470);
        primaryStage.setHeight(315);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball And Brick");
        primaryStage.show();
    }

    private Scene mainMenu(){
        Stage newStage = new Stage();
        GridPane menuLayout = new GridPane();
        Scene menuScene = new Scene(menuLayout);
        menuLayout.setAlignment(Pos.CENTER_LEFT);
        menuLayout.setHgap(100);
        menuLayout.setVgap(20);
        try{
            FileInputStream menuImg;
            menuImg = new FileInputStream(mainMenuBackgroundPath.toFile());
            menuLayout.setBackground(new Background(
                    new BackgroundImage(new Image(menuImg),
                            BackgroundRepeat.SPACE, BackgroundRepeat.SPACE,
                    BackgroundPosition.CENTER,
                            new BackgroundSize(menuScene.getWidth(),
                                    menuScene.getHeight(), false, false,
                            false, true))));
        }catch(FileNotFoundException e){
            //run without background
        }

        Media media = new Media(soundPath.toFile().toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        setUpMediaProcess(menuLayout, mediaPlayer);

        Button newGameBtn = new Button("New Game");
        Button loadGameBtn = new Button("Load Game");
        Button helpBtn = new Button("Help");
        Button aboutBtn = new Button("About Us");
        Button exitBtn = new Button("Exit");

        menuLayout.add(newGameBtn, 1, 0);
        menuLayout.add(loadGameBtn, 1, 1);
        menuLayout.add(helpBtn, 1, 2);
        menuLayout.add(aboutBtn, 1, 3);
        menuLayout.add(exitBtn, 1, 4);

        setUpMenuButtonsActionEvent(newStage, newGameBtn, loadGameBtn,
                helpBtn, aboutBtn, exitBtn);

        return menuScene;
    }

    private void setUpMediaProcess(GridPane menuLayout,
                                   MediaPlayer mediaPlayer){
        try{
            mediaOnImgVu = new ImageView(new Image(new FileInputStream
                    (soundOnImagePath.toFile())));
            mediaOffImgVu = new ImageView(new Image(new FileInputStream
                    (soundOffImagePath.toFile())));
            final int size = 40;
            mediaOnImgVu.setFitHeight(size);
            mediaOnImgVu.setFitWidth(size);
            mediaOffImgVu.setFitHeight(size);
            mediaOffImgVu.setFitWidth(size);
        }catch(IOException e){
            //load without sound icon
        }
        if( mediaOnImgVu!=null && mediaOffImgVu!=null ){
            menuLayout.add(mediaOnImgVu, 0, 4);

            mediaOnImgVu.setOnMouseClicked(event -> {
                menuLayout.getChildren().remove(mediaOffImgVu);
                menuLayout.add(mediaOffImgVu, 0, 4);
                mediaPlayer.pause();
            });
            mediaOffImgVu.setOnMouseClicked(event -> {
                menuLayout.getChildren().remove(mediaOnImgVu);
                menuLayout.add(mediaOnImgVu, 0, 4);
                mediaPlayer.play();
            });
        }
    }

    private void setUpMenuButtonsActionEvent(Stage newStage, Button newGameBtn
            , Button loadGameBtn, Button helpBtn, Button aboutBtn
            , Button exitBtn){
        newGameBtn.setOnAction(event -> {
            Scene gameScene = newGameAction();
            if( gameScene==null ){
                return;
            }
            newStage.setScene(gameScene);
            newStage.showAndWait();
            newStage.hide();
            runningState = false;
        });
        loadGameBtn.setOnAction(event -> {
            Scene gameScene = loadGameAction();
            if( gameScene==null ){
                return;
            }
            newStage.setScene(gameScene);
            newStage.showAndWait();
            newStage.hide();
            runningState = false;
        });
        helpBtn.setOnAction(event -> {
            newStage.setScene(helpScene());
            newStage.showAndWait();
            newStage.hide();
        });
        aboutBtn.setOnAction(event -> {
            TextArea textArea = new TextArea();
            textArea.setText("Saba Fathi" + '\n' +
                    "https://github.com/SabaFathi/BrickBreaker" + '\n' + '\n'
                    + "KN2C university" + '\n' + '\n' + "summer 2018" + '\n' +
                    '\n' + "made in iran");
            newStage.setScene(new Scene(new Pane(textArea)));
            newStage.showAndWait();
            newStage.hide();
        });
        exitBtn.setOnAction(event -> {
            exitAction();
        });
    }

    private Scene newGameAction(){
        runningState = true;
        setGameBoard(null);
        return gameScene();
    }
    private Scene loadGameAction(){
        runningState = true;
        Path path = getPath();
        if( path==null ){
            JOptionPane.showMessageDialog(null,
                    "Your Option Can Not Be Applied. Play A New Game...");
        }
        setGameBoard(path);

        return gameScene();
    }
    private void exitAction(){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure You Want Exit The Game?",
                ButtonType.YES, ButtonType.CANCEL);
        exitAlert.showAndWait();
        if(exitAlert.getResult()==ButtonType.YES){
            JOptionPane.showMessageDialog(null, "Good Luck!");
            System.out.println("Program Closed Successfully");
            System.exit(0);
        }else{
            exitAlert.close();
        }
    }
    private void saveAction(){
        String fileName;
        while( (fileName = JOptionPane.showInputDialog
                ("Please Enter A Name To Save The Game With That:")).
                length()<1){
            //I want do nothing :|
            continue;
        }
        try{
            BoardLoader.saveInFile(fileName, gameBoard);
            JOptionPane.showMessageDialog(null, "the game saved " +
                    "successfully :)");
        }catch(RuntimeException e){
            JOptionPane.showMessageDialog(null, "sorry\n" + e.getMessage());
        }
    }

    private Scene gameScene(){
        Pane root = new Pane();
        HBox dataBoxes = new HBox(20);
        Scene scene = new Scene(root);

        dataBoxes.setLayoutY(10);
        if( gameBoard==null ){
            getInputs();
        }

        rows = gameBoard.getBoardWidth();
        cols = gameBoard.getBoardLength();
        racket.setY((rows+1)*LENGTH_OF_CELLS);
        racket.setXLimits(LENGTH_OF_CELLS, (cols-2)*LENGTH_OF_CELLS);

        Ball ball = gameBoard.getBall();

        Label scoreLabel = new Label();
        Label lifeLabel = new Label();
        Label levelLabel = new Label();
        dataBoxes.getChildren().addAll(scoreLabel, lifeLabel, levelLabel);

        Thread thread = gameThread(ball, root, dataBoxes, scoreLabel,
                lifeLabel, levelLabel);

        Alert startAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Ready To Start?", ButtonType.OK, ButtonType.CANCEL);
        startAlert.showAndWait();
        if( startAlert.getResult()==ButtonType.OK ){
            thread.start();
        }else{
            return null;
        }

        scene.setOnKeyPressed(event -> {
            KeyCode eventKeyCode = event.getCode();
            if( eventKeyCode==KeyCode.LEFT || eventKeyCode==KeyCode.RIGHT ){
                racket.setCoordinate(eventKeyCode, LENGTH_OF_CELLS);
                putRacket(root);
            }
            if( eventKeyCode==KeyCode.ESCAPE ){
                runningState = false;
                Dialog closeDialog = new Dialog();
                closeDialog.setHeaderText("You Choosed Exit Option!");
                Button saveAndCloseBtn = new Button("Save Then Exit");
                Button closeBtn = new Button("Exit Without Save");
                saveAndCloseBtn.setOnAction(closeEvent -> {
                    saveAction();
                    exitAction();
                });
                closeBtn.setOnAction(closeEvent -> {
                    exitAction();
                });
                HBox buttonsLayout = new HBox(30);
                buttonsLayout.getChildren().addAll(saveAndCloseBtn, closeBtn);
                closeDialog.getDialogPane().setContent(buttonsLayout);
                closeDialog.showAndWait();
            }
            if( eventKeyCode==KeyCode.SPACE ){
                runningState = !runningState;
            }
        });

        root.setMinSize((cols+2)*LENGTH_OF_CELLS, (rows+2)*LENGTH_OF_CELLS);
        return scene;
    }

    private Thread gameThread(Ball ball, Pane root, HBox dataBoxes
            , Label scoreLabel, Label lifeLabel, Label levelLabel){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while( true ) {
                    if( !runningState ){
                        continue;
                    }
                    if( gameBoard.isWon() ){
                        sayWin(gameBoard);
                    }
                    if( gameBoard.isLost() ){
                        sayLost();
                        break;
                    }
                    Cell[][] cells = gameBoard.nextStep();
                    Platform.runLater(()->{
                        scoreLabel.setText("Score : " + ball.getScore());
                        lifeLabel.setText("Life : " + ball.getLife());
                        levelLabel.setText("Level : " + gameBoard.getLevel());
                        updatePane(root, cells, dataBoxes);
                    });
                }

                JOptionPane.showMessageDialog(null,
                        "score : " + gameBoard.getBall().getScore() +
                                "\nPlease Close The Window");

            }
        });

        return thread;
    }

    private Scene helpScene(){
        VBox buttonsVBox = new VBox(20);
        buttonsVBox.setAlignment(Pos.TOP_CENTER);
        buttonsVBox.setMinSize(100, 100);
        ScrollPane descriptionsScrollPane = new ScrollPane();
        descriptionsScrollPane.setMinSize(400, 400);

        Button menuButton = new Button("About Menu");
        Button keysButton = new Button("Keys");
        Button componentsButton = new Button("Components");
        buttonsVBox.getChildren().addAll(menuButton, keysButton,
                componentsButton);
        menuButton.setOnAction(event -> {
            TextArea menuTextArea = new TextArea(HelpMenuFileReader.
                    getMenuDescriptions());
            descriptionsScrollPane.setContent(menuTextArea);
        });
        keysButton.setOnAction(event -> {
            TextArea keysTextArea = new TextArea(HelpMenuFileReader.
                    getKeyDescriptions());
            descriptionsScrollPane.setContent(keysTextArea);
        });
        componentsButton.setOnAction(event -> {
            GridPane componentsGridPane = new GridPane();
            componentsGridPane.setVgap(20);
            componentsGridPane.setHgap(50);

            String[] describes = ImagesLoader.getDescribes();
            String[] classNames = ImagesLoader.getClassNames();
            ImageView imageView;
            for(int i=0 ; i<ImagesLoader.getNumberOfComponents() ; i++){
                imageView = new ImageView(ImagesLoader.getImageOf
                        (classNames[i]));
                imageView.setFitHeight(LENGTH_OF_CELLS);
                imageView.setFitWidth(LENGTH_OF_CELLS);
                componentsGridPane.add(imageView, 0 , i);
                componentsGridPane.add(new Label(describes[i]), 1, i);
            }

            descriptionsScrollPane.setContent(componentsGridPane);
        });

        HBox root = new HBox(10, buttonsVBox, descriptionsScrollPane);
        Scene scene = new Scene(root);
        return scene;
    }

    private Path getPath(){
        VBox layout = new VBox(15);
        Scene scene = new Scene(layout);
        Stage selectionStage = new Stage();
        selectionStage.setTitle("Load...");
        selectionStage.setScene(scene);

        ToggleGroup group = new ToggleGroup();
        String[] fileNames = SavedGames.getFileNames();
        if( fileNames==null || fileNames.length<1 ){
            return null;
        }

        layout.getChildren().add(new Label("Please Choose A File:"));
        for(String fileName : fileNames){
            RadioButton radioButton = new RadioButton(fileName);
            radioButton.setUserData(fileName);
            radioButton.setToggleGroup(group);
            layout.getChildren().add(radioButton);
        }

        Button okButton = new Button("OK");
        layout.getChildren().add(okButton);
        okButton.setOnAction(event -> {
            selectionStage.close();
        });

        selectionStage.showAndWait();

        Toggle selected = group.getSelectedToggle();
        if( selected==null ){
            return null;
        }else{
            return SavedGames.getPath(selected.getUserData().toString());
        }
    }

    private void setGameBoard(Path path){
        if( path==null ){
            boolean correctInputs;
            do{
                try{
                    getInputs();
                    gameBoard = new GameBoard(state, rows, cols);
                    correctInputs = true;
                }catch(RuntimeException e){
                    correctInputs = false;
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }while( !correctInputs );
        }else{
            gameBoard = BoardLoader.loadGameBoard(path);
        }
    }

    /**
     *
     * @return length of the cells in GUI
     */
    public static int getLengthOfCells() {
        return LENGTH_OF_CELLS;
    }

    private void sayWin(GameBoard gameBoard){
        JOptionPane.showMessageDialog(null, "Congratulations!\n"
                + "You Complete Level " + gameBoard.getLevel() + "\n" +
                "Be Ready For Next Level...");
    }

    private void sayLost(){
        JOptionPane.showMessageDialog(null, "You lost the ball :( \n");
    }

    private void getInputs(){
        boolean isFalseInput;
        do{
            try {
                rows = Integer.parseInt(JOptionPane.showInputDialog
                        ("Please Enter The Number Of Rows In The Board:"));
                cols = Integer.parseInt(JOptionPane.showInputDialog
                        ("Please Enter The Number Of Columns " +
                                "In The Board:"));
                state = Integer.parseInt(JOptionPane.showInputDialog
                        ("Please Enter The Max Time That The Ball " +
                                "Is Alive:"));
                isFalseInput = false;
            }catch(NumberFormatException e){
                isFalseInput = true;
            }
        }while(isFalseInput);
    }

    private void putRacket(Pane pane){
        ImageView racket = Racket.getImageView();
        racket.setY(this.racket.getY());
        racket.setX(this.racket.getX());
        racket.setFitWidth(LENGTH_OF_CELLS*LENGTH_OF_RACKET);
        racket.setFitHeight(LENGTH_OF_CELLS);
        Platform.runLater(()->{
            pane.getChildren().remove(racket);
            pane.getChildren().add(Racket.getImageView());
        });
    }

    private void updatePane(Pane pane, Cell[][] cells, Node... nodes){
        pane.getChildren().clear();

        putRacket(pane);
        pane.getChildren().addAll(nodes);

        if( gameBoard.getBall().isShiningMode() ){
            Line shiningLine = new Line(0+LENGTH_OF_CELLS, racket.getY(),
                    0+(1+cols)*LENGTH_OF_CELLS, racket.getY());
            shiningLine.setStroke(Color.YELLOW);
            shiningLine.setStrokeWidth(10);
            pane.getChildren().add(shiningLine);
        }

        addGameBoardToPane(pane, cells);
    }

    private void addGameBoardToPane(Pane pane, Cell[][] cells){
        final Image nullImage = ImagesLoader.getImageOf("null");
        double x;
        double y = 0;
        for(Cell[] row : cells){
            y += LENGTH_OF_CELLS;
            x = 0;

            for(Cell cell : row){
                x += LENGTH_OF_CELLS;

                if( cell==null ){
                    //product temp Cell with null content :|
                    cell = new Cell();
                    cell.setContent(null);
                }
                if( cell.getContent()==null ){
                    //product temp Brick :|
                    cell.setContent(new Brick(0, 0 , null) {
                        @Override
                        public void hit() {}
                    });

                    cell.getContent().setImage(nullImage);
                }

                GameComponent gameComponent = cell.getContent();
                if( gameComponent.getImage() == null ){
                    gameComponent.setImage(ImagesLoader.getImageOf
                            (gameComponent.getClass().getSimpleName()));
                }

                ImageView imageView = gameComponent.getImageView();
                imageView.setX(x);
                imageView.setY(y);
                imageView.setFitHeight(LENGTH_OF_CELLS);
                imageView.setFitWidth(LENGTH_OF_CELLS);

                try{
                    pane.getChildren().add(imageView);
                }catch(Exception ex){
                    System.out.println("node trying to add again!");
                    continue;
                }
            }
        }
    }

    /**
     *
     * @return length of the racket
     */
    public static int getLengthOfRacket() {
        return LENGTH_OF_RACKET;
    }

    /**
     *
     * @param args input args which came from command line
     */
    public static void main(String[] args) {
        launch();
    }
}
