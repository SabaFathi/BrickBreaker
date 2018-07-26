package ir.ac.kntu;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * this class works with files; read a file or write in a file
 * to load or save the game board.
 *
 * this is a utility class, so it has just static methods.
 *
 * @author SFathi
 */
public final class BoardLoader {
    private static final String FILE_EXTENSION = ".bin";
    private BoardLoader(){}//constructor of utility class

    /**
     *
     * @param path path of the file which request to load
     * @return the GameBoard saved in the file.
     */
    public static GameBoard loadGameBoard(Path path){
        FileInputStream inputStream = null;
        ObjectInputStream reader = null;
        try{
            inputStream = new FileInputStream(path.toFile());
            reader = new ObjectInputStream(inputStream);
        }catch(IOException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("input stream or reader is null...");
            System.exit(1);
        }

        GameBoard gameBoard = null;
        try {
            gameBoard = (GameBoard)reader.readObject();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
            reader.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

        return gameBoard;
    }

    /**
     *
     * @param fileName file's name which should write data in it.
     * @param gameBoard the game board which requesting to save.
     */
    public static synchronized void saveInFile(String fileName,
                                               GameBoard gameBoard){
        Path savedGameFolderPath;
        try{
            savedGameFolderPath = SavedGames.getDefaultPath();
        }catch(RuntimeException e){
            throw e;
        }
        Path path = Paths.get(savedGameFolderPath.toString() +
                File.separator + fileName + FILE_EXTENSION);
        FileOutputStream outputStream = null;
        ObjectOutputStream writer = null;
        try{
            outputStream = new FileOutputStream(path.toFile());
            writer = new ObjectOutputStream(outputStream);

            writer.writeObject(gameBoard);
        }catch(NullPointerException e){
            throw new RuntimeException("outputStream or writer is null...");
        } catch(IOException e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
            writer.close();
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
        }
    }
}
