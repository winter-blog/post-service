package com.devwinter.postservice.application.markdown;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MarkdownAnalyze {
    public List<String> getImages(String markdown) {
        List<String> imageTags = new ArrayList<>();
        String imagePattern = "!\\[[^\\]]*\\]\\([^)]+\\)";
        Pattern pattern = Pattern.compile(imagePattern);
        Matcher matcher = pattern.matcher(markdown);

        while (matcher.find()) {
            imageTags.add(extractContentInsideParentheses(matcher.group()));
        }

        return imageTags;
    }

    public String extractImageUrl(String input) {
        String pattern = "!\\[[^\\]]*\\]\\(([^)]+)\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    public String getPlainContents(String markdown) {
        String patternImageTags = "!\\[[^\\]]*\\]\\([^)]+\\)";
        String noImageTags = markdown.replaceAll(patternImageTags, "");

        String patternSpecialCharacters = "[^\\p{L}\\p{N}\\p{Z}]+";
        return noImageTags.replaceAll(patternSpecialCharacters, "").replaceAll("\\s", "");
    }

    private String extractContentInsideParentheses(String input) {
        String content = "";
        String pattern = "\\(([^)]+)\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (matcher.find()) {
            content = matcher.group(1);
        }

        return content;
    }
}
