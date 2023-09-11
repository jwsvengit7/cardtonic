package com.cardmonix.cardmonix.algorithms;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ReadWeb {
    @SneakyThrows
    public String findSpan(String findText,String url){

            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements spanTags = document.select("p");
            for (Element span : spanTags) {
                String text = span.text();
                List<String> list = List.of(text.split(" "));
                if(list.contains(findText)){
                    return "Find it";
                }
            }
        return "cant find";


    }
    public static void main(String[] args) {
       ReadWeb readWeb = new ReadWeb();
        System.out.println(readWeb.findSpan("email","https://hostbeak.com/password/reset"));
    }
}
