package com.ultisoft;

import com.ultisoft.controller.MainWindowController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Exporter {

    private static final String JQUERY_SRC = "window.jQuery || document.write('<script src=\"http://code.jquery.com/jquery-2.1.4.min.js\">\\x3C/script>')";
    private static final String INFINITE_DURATION = "2147483647";

    public static void exportTo(File file, URL url, String animationType, double duration, boolean repeat, double repeatPause) throws IOException {

        Document website = Jsoup
                .connect(url.toString())
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .get();

        relativeLinksToAbsolute(website);

        String animationScript = configureScript(animationType, duration * 1000, repeat, repeatPause * 1000);
        InjectScript(website, animationScript);
        CleanPage(website);

        saveDocument(file, website);
    }

    private static void CleanPage(Document website) {
        // remove cookie banner
        Element banner = website.getElementById("ca_banner");
        if (banner != null)
            banner.remove();
    }

    private static void InjectScript(Document website, String animationScript) {
        website.head().appendElement("script").text(JQUERY_SRC);
        website.body().appendElement("script").text(animationScript);
    }

    private static String configureScript(String animationType, Double duration, Boolean repeat, Double repeatPause) {
        Map<String, String> args = new HashMap<>();

        if (animationType.equals(MainWindowController.LINEAR_SCROLLING)) {
            args.put("pauseBeforeRepetition", repeat ? repeatPause.toString() : INFINITE_DURATION);
            args.put("duration", duration.toString());
            return loadScript("continuous_scroll.js", args);
        }

        if (animationType.equals(MainWindowController.PAGEWISE_SCROLLING)) {
            args.put("showPageDuration", duration.toString());
            args.put("showPageDurationSwitch", repeat ? duration.toString() : INFINITE_DURATION);
            return loadScript("pausing_scroll.js", args);
        }

        return null;
    }

    private static void relativeLinksToAbsolute(Document website) {
        Elements elements = website.select("img");

        for(Element element : elements) {
            String absUrl = element.absUrl("src");
            element.attr("src", absUrl);
        }

        elements = website.select("a");

        for(Element element : elements) {
            String absUrl = element.absUrl("href");
            element.attr("href", absUrl);
        }
    }

    private static void saveDocument(File file, Document website) throws FileNotFoundException, UnsupportedEncodingException {
        String html = website.outerHtml();

        PrintWriter out = new PrintWriter(file, "UTF-8");
        out.write(html);
        out.close();
    }

    private static String loadScript(String name, Map<String, String> args) {
        String script = new Scanner(Exporter.class.getClassLoader().getResourceAsStream("scripts" + File.separator + name), "UTF-8").useDelimiter("\\A").next();

        for (Map.Entry<String, String> arg: args.entrySet()){
            script = script.replace("<<" + arg.getKey() + ">>", arg.getValue());
        }

        return script;
    }
}
