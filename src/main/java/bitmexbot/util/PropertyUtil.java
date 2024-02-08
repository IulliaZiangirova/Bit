package bitmexbot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertyUtil {
    private static final String PATH_TO_PROPERTIES = "src/main/resources/property.properties";
    private static final Properties prop = new Properties();


    private void getFile (){
        try(FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);){
            prop.load(fileInputStream);
        }catch (IOException e) {
            log.error("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружен");
            e.printStackTrace();
        }
    }

    public String get(String field){
        getFile();
        return prop.getProperty(field);
    }

}
