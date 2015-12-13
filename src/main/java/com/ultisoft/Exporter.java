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
import java.util.Objects;
import java.util.Scanner;

public class Exporter {

    private static final String JQUERY_SRC = "http://code.jquery.com/jquery-2.1.4.min.js";
    private static final String INFINITE_DURATION = "2147483647";

    public static void exportTo(File file, URL url, String animationType, double duration, String bottomReachedAction) throws IOException {

        Document website = Jsoup
                .connect(url.toString())
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .get();

        relativeLinksToAbsolute(website);

        String scrollActionsScript = loadScript("scroll_actions.js", null);
        String animationScript = configureScript(animationType, duration * 1000, bottomReachedAction);

        website.head().prependElement("script").attr("src", JQUERY_SRC);

        InjectScripts(website, scrollActionsScript, animationScript);
        CleanPage(website);

        saveDocument(file, website);
    }

    private static void CleanPage(Document website) {
        // remove cookie banner
        Element banner = website.getElementById("ca_banner");
        if (banner != null)
            banner.remove();
    }

    private static void InjectScripts(Document website, String... scripts) {
        for (String script : scripts)
            website.body().appendElement("script").text(script);
    }

    private static String configureScript(String animationType, Double duration, String bottomReachedAction) {
        Map<String, String> args = new HashMap<>();
        String script = "";

        args.put("duration", duration.toString());

        if (bottomReachedAction.equals(MainWindowController.BOTTOM_JUMP_TOP)) {
            args.put("bottomReachedAction", "scrollTop");
        }

        if (animationType.equals(MainWindowController.LINEAR_SCROLLING)) {
            script = "linear_scroll.js";

            if (bottomReachedAction.equals(MainWindowController.BOTTOM_REVERSE)) {
                args.put("bottomReachedAction", "scrollUp");
            }
        }

        if (animationType.equals(MainWindowController.PAGEWISE_SCROLLING)) {
            script = "pausing_scroll.js";

            if (bottomReachedAction.equals(MainWindowController.BOTTOM_REVERSE)) {
                args.put("bottomReachedAction", "scrollUp");
            }
        }

        return loadScript(script, args);
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

        if (args == null)
            return script;

        for (Map.Entry<String, String> arg: args.entrySet()){
            script = script.replace("<<" + arg.getKey() + ">>", arg.getValue());
        }

        return script;
    }
}
