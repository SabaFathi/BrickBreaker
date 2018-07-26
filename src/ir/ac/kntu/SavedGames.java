package ir.ac.kntu;

import java.io.File;
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
    private final static Path PATH =Paths.get("savedGames").toAbsolutePath();

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
