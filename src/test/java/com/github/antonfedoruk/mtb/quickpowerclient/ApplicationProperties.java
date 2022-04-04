package com.github.antonfedoruk.mtb.quickpowerclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//класс, который будет читать записанные в файл «application.properties» значения:
public class ApplicationProperties {
    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;
    static {
        try {
            //указание пути до файла с настройками
            fileInputStream = new FileInputStream("src/test/resources/application.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            //обработка возможного исключения (нет файла и т.п.)
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace(); } } }
    /**
     * метод для возврата строки со значением из файла с настройками
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key); }
}
