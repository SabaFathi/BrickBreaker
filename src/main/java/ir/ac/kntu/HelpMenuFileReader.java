package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * this class read the text files to help the help-showing options.
 * this is a utility class so it has just static methods
 *
 * @author SFathi
 */
public final class HelpMenuFileReader {
    private static Path keysPath = null;
    private static Path menuPath = null;
    static{
        try{
            keysPath = Paths.get(HelpMenuFileReader.class.getClassLoader()
                    .getResource("help/keys.txt").toURI());
            menuPath = Paths.get(HelpMenuFileReader.class.getClassLoader()
                    .getResource("help/menu.txt").toURI());
        }catch(URISyntaxException e){
            e.printStackTrace();
            System.out.println("can not find keys.txt or menu.txt");
        }
    }
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
