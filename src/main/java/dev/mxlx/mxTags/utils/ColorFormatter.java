package dev.mxlx.mxTags.utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFormatter {
    private String hexCodeRegex = "<#[0-9a-fA-F]{3}(?:[0-9a-fA-F]{3})?>";

    public String formatHexColors(String input) {
        Pattern pattern = Pattern.compile("<#[0-9a-fA-F]{3}(?:[0-9a-fA-F]{3})?>");
        Matcher matcher = pattern.matcher(input);

        StringBuffer output = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group().substring(2, matcher.group().length() - 1);

            String chatColorCode = getHexCodeAsMcColor(hexCode);

            matcher.appendReplacement(output, chatColorCode);
        }

        matcher.appendTail(output);
        
        String finalOutput = ChatColor.translateAlternateColorCodes('&', output.toString());
        return finalOutput;
    }

    private String getHexCodeAsMcColor(String hexCode) {
        if (hexCode.length() == 3) {
            hexCode = "" + hexCode.charAt(0) + hexCode.charAt(0) + hexCode.charAt(1) + hexCode.charAt(1) + hexCode.charAt(2) + hexCode.charAt(2);
        }
        StringBuilder output = new StringBuilder("&x");
        for (char c : hexCode.toCharArray()) {
            output.append("&").append(c);
        }
        return output.toString();
    }
}