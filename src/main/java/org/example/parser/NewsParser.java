package org.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class NewsParser {

    private static final String URL_TO_HTML = "https://www.rbc.ru/";
    public String runParse() {
        StringBuilder result = new StringBuilder();

        try {
            Document doc = Jsoup.connect("https://www.rbc.ru/politics/?utm_source=topline").get();
            // Выбираем только первый элемент с классом news-feed__item__title
            Element firstElement = doc.selectFirst("span.normal-wrap");

            if (firstElement != null) {
                String firstElementText = firstElement.text();
                result.append(firstElementText);
            } else {
                result.append("Элемент с заданным классом не найден.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
