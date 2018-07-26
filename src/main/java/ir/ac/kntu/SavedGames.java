package ir.ac.kntu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * this class help to read from and write in files to load ar save a game.
 * this is a utility class so it has just static methods
 *
 * @author SFathi
 */
public final class SavedGames {
    private static final String FOLDER_NAME = "savedGames";
    private /*final*/ static Path PATH =Paths.get(FOLDER_NAME)
            .toAbsolutePath();
    static{
        Path path = Paths.get(FOLDER_NAME);
        if (Files.exists(path)) {
            PATH = path.toAbsolutePath();
        }else{
            path = new File(System.getProperty("user.dir") + File.separator
                    + FOLDER_NAME).toPath();
            try{
                Files.createDirectory(path);
                PATH = path;
            }catch(IOException e){
                System.out.println("exception thrown!");
                System.out.println("directory creation failed, path = " +
                        PATH.toString());
                throw new RuntimeException("directory for saving games, can " +
                        "not create!");
            }
        }
    }

    /**
     *
     * @return all file's name existing in the path
     */
    static String[] getFileNames(){
        return PATH.toFile().list();
    }

    /**
     *
     * @param fileName saved game file's name
     * @return the path of the file if exist, otherwise null.
     */
    static Path getPath(String fileName){
        String address = PATH.toString() + File.separator + fileName;
        if( Files.exists(Paths.get(address)) ){
            return Paths.get(address);
        }else{
            return null;
        }
    }

    /**
     *
     * @return default path of directory of saved games
     */
    static Path getDefaultPath() {
        return PATH;
    }
}
