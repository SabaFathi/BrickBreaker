package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * this class read the text files to help the help-showing options.
 * this is a utility class so it has just static methods
 *
 * @author SFathi
 */
public final class HelpMenuFileReader {
    private static Path keysPath = Paths.get("resources\\help\\keys" +
            ".txt");
    private static Path menuPath = Paths.get("resources\\help\\menu" +
            ".txt");
    private HelpMenuFileReader(){}//constructor of the utility class

    /**
     *
     * @return content of file "keys.txt"
     */
    public static String getKeyDescriptions(){
        return getText( getReader(keysPath) );
    }

    /**
     *
     * @return content of file "menu.txt"
     */
    public static String getMenuDescriptions(){
        return getText( getReader(menuPath) );
    }

    private static BufferedReader getReader(Path path){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(path.toFile()));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        if( reader==null ){
            System.out.println("reader(help menu reader) is null!");
            System.out.println();
        }

        return reader;
    }

    private static String getText(BufferedReader reader){
        StringBuffer descriptions = new StringBuffer();

        try{
            String str;
            while( (str = reader.readLine()) != null ){
                descriptions.append(str);
                descriptions.append('\n');
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return descriptions.toString();
    }
}
