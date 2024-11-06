package org.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherParser {
    private static final String URL_TO_HTML = "https://www.google.com/search?q=weather+Moscow";
    public String runParse() {
        StringBuilder result = new StringBuilder();

            try {
                Document doc = Jsoup.connect("https://www.google.com/search?q=weather+Moscow").get();
                var elements = doc.select("span.wob_t.q8U8x");
                String currentWeather =  elements.text();
                if (Integer.parseInt(currentWeather) > 0) {
                    result = result.append("+ ");
                }
                result.append(elements.text());
                result.append(" °C");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result.toString();
    }

}
