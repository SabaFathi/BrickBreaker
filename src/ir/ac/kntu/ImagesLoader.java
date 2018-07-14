package ir.ac.kntu;


import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * shapes are load from the path which describe in related field
 * in the class<br/>
 * these shapes used to show game's components in GUI<br/>
 * <br/>
 *
 * each component defined in "components.txt" file, should exactly define in
 * this pattern:<br/>
 *
 * "address of the image\n"
 * "class name\n"
 * "describe\n"
 * "\n"
 *
 * <br/><br/>
 * this is a utility class so it has just static methods<br/>
 *
 * @author SFathi
 */
public final class ImagesLoader {
    private static final int NUMBER_OF_BRICKS = 8;
    private static final int NUMBER_OF_OTHER_COMPONENTS = 6;
    private static final int NUMBER_OF_COMPONENTS =
            NUMBER_OF_BRICKS + NUMBER_OF_OTHER_COMPONENTS;
    private static final Path COMPONENTS_PATH = Paths.get
            ("resources\\help\\components.txt");
    private static Map<String, Image> images = new HashMap<>();
    private static String[] classNames = new String[NUMBER_OF_COMPONENTS];
    private static String[] describes = new String[NUMBER_OF_COMPONENTS];
    private ImagesLoader(){}//constructor of the utility class

    /**
     *
     * @return all describes loaded
     */
    public static String[] getDescribes(){
        boolean haveNull = false;
        for(int i=0 ; i<NUMBER_OF_COMPONENTS ; i++){
            if( !notNull(i) ){
                haveNull = true;
                break;
            }
        }
        if( haveNull ){
            readShapes();
        }
        return Arrays.copyOf(describes, describes.length);
    }

    public static String[] getClassNames() {
        return Arrays.copyOf(classNames, classNames.length);
    }

    public static Image getImageOf(String className){
        boolean haveNull = false;
        for(int i=0 ; i<NUMBER_OF_COMPONENTS ; i++){
            if( !notNull(i) ){
                haveNull = true;
                break;
            }
        }
        if( haveNull ){
            readShapes();
        }
        return images.get(className);
    }

    private static void readShapes(){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(
                    COMPONENTS_PATH.toFile()));
        }catch(IOException e){
            e.printStackTrace();
        }
        if( reader==null ){
            System.out.println("reader is null!");
            System.out.println(COMPONENTS_PATH.toAbsolutePath());
            System.exit(1);
        }

        try{
            String className;
            Image image;
            for(int i=0 ; i<NUMBER_OF_COMPONENTS ; i++){
                image = new Image(new FileInputStream(reader.readLine()));
                classNames[i] = reader.readLine();
                images.put(classNames[i], image);
                describes[i] = reader.readLine();
                reader.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return number of all components
     */
    public static int getNumberOfComponents() {
        return NUMBER_OF_COMPONENTS;
    }

    /**
     *
     * @return number of bricks
     */
    public static int getNumberOfBricks() {
        return NUMBER_OF_BRICKS;
    }

    private static boolean notNull(int index){
        if( classNames[index]==null ){
            return false;
        }else{
            return true;
        }
    }
}
