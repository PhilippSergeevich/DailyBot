package org.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CourseParser {
    public String runParse() {
        StringBuilder result = new StringBuilder();

        try {
            Document doc = Jsoup.connect("https://www.cbr.ru/").get();
            var elements = doc.selectXpath("//*[@id=\"content\"]/div/div/div/div[1]/div[2]/div/div[5]/div/div[3]/div[2]");
            var elements2 = doc.selectXpath("//*[@id=\"content\"]/div/div/div/div[1]/div[2]/div/div[5]/div/div[3]/div[3]");

            // Добавляем текст элементов в строку
            result.append("Доллар США покупка : ").append(elements.text()).append("\n");
            result.append("Доллар США продажа : ").append(elements2.text()).append("\n");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при подключении к сайту: " + e.getMessage(), e);
        }

        return result.toString();
    }
}



