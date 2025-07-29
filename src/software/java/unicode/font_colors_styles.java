package unicode;

/**  ANSI escape codes
 * font colors, style, and background 
 * used for command line 
 */
public class Colors {
    // Reset
    public static final String RESET = "\u001B[0m";
    public static final String RESET_BACKGROUND = "\u001B[49m";
    public static final String RESET_UNDERLINE = "\u001B[24m";

    // styles
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
    public static final String REVERSE = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";
    public static final String STRIKETHROUGH = "\u001B[9m";

    //  Colors
    public static final String BLACK = "\u001B[30m";
    public static final String BLACK_BOLD = "\u001B[30;1m";
    public static final String BLACK_BACKGROUND = "\u001B[40m";

    public static final String RED = "\u001B[31m";
    public static final String RED_BOLD = "\u001B[31;1m";
    public static final String RED_BACKGROUND = "\u001B[41m";

    public static final String GREEN = "\u001B[32m";
    public static final String GREEN_BOLD = "\u001B[32;1m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";

    public static final String YELLOW = "\u001B[33m";
    public static final String YELLOW_BOLD = "\u001B[33;1m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";

    public static final String BLUE = "\u001B[34m";
    public static final String BLUE_BOLD = "\u001B[34;1m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";

    public static final String PURPLE = "\u001B[35m";
    public static final String PURPLE_BOLD = "\u001B[35;1m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";

    public static final String CYAN = "\u001B[36m";
    public static final String CYAN_BOLD = "\u001B[36;1m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";

    public static final String WHITE = "\u001B[37m";
    public static final String WHITE_BOLD = "\u001B[37;1m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Applies a color/style to the text
     * @param text string you want to add a style to
     * @return text with your desired styles/colors applied
     */
    public static String colorize(String text, String colorCode) {
        return colorCode + text + RESET;
    }

    /**
     * applies multiple styles to the string
     * @param text text you want to add multiple ANSI codes to
     * @param codes array of strings of ANSI codes you want to apply to a given text
     * @return your desired string formatted
     */
    public static String colorize(String text, String... codes) {
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            sb.append(code);
        }
        return sb + text + RESET;
    }

    /**
     * Create rainbow-colored text (cycles through a set of colors).
     * @param text string to make rainbow colored 
     * @return beautifully rainbow string (Red-Yellow-Green-Cyan-Blue-Purple)
     */
    public static String rainbowText(String text) {
        String[] rainbowColors = { RED, YELLOW, GREEN, CYAN, BLUE, PURPLE };
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(rainbowColors[i % rainbowColors.length])
                  .append(text.charAt(i));
        }
        return result + RESET;
    }

    /**
     * Apply a background color to the text.
     * Applies a background color to a text then resets
     */
    public static String withBackground(String text, String bkgdColor) {
        return bkgdColor + text + RESET;
    }

    /**
     * Creates bolded text then resets the color
     * @param text string to make bold
     * @return bolded text
     */
    public static String bold(String text) {
        return BOLD + text + RESET;
    }

    /**
     * Underlines the text then resets the style
     * @param text string to underline
     * @return underlined text
     */
    public static String underline(String text) {
        return UNDERLINE + text + RESET;
    }
}
