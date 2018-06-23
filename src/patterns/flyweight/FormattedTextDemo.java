package patterns.flyweight;

import java.util.ArrayList;
import java.util.List;

class FormattedText { // bad
  private String plainText;
  private boolean[] isCapitalized;

  public FormattedText(String plainText) {
    this.plainText = plainText;
    this.isCapitalized = new boolean[plainText.length()];
    /* isBold */
    /* isItalic */
  }

  public void capitalize(int start, int end) {
    for (int i = start; i <= end; i++) {
      // each char has its own isCapitalized state
      isCapitalized[i] = true;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < plainText.length(); i++) {
      char c = plainText.charAt(i);
      sb.append(isCapitalized[i] ? Character.toUpperCase(c) : c);
    }
    return sb.toString();
  }
}

class BetterFormattedText {
  private String plainText;
  private List<TextRange> formatting = new ArrayList<>();

  public BetterFormattedText(String plainText) {
    this.plainText = plainText;
  }

  // flyweight pattern
  // making a range of text share the same format,
  // instead of making each char have its own format
  public TextRange getRange(int start, int end) {
    TextRange range = new TextRange(start, end);
    formatting.add(range);
    return range;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < plainText.length(); i++) {
      char c = plainText.charAt(i);
      for (TextRange range : formatting) {
        if (range.covers(i)) {
          if (range.isCapitalized) {
            c = Character.toUpperCase(c);
          }
          /* if isBold... */
          /* if isItalic... */
        }
      }
      sb.append(c);
    }
    return sb.toString();
  }

  public class TextRange {
    public int start, end;
    public boolean isCapitalized; // can also easily extend: isBold, isItalic

    public TextRange(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public boolean covers(int position) {
      return position >= start && position <= end;
    }
  }
}

public class FormattedTextDemo {
  public static void main(String[] args) {
    BetterFormattedText formattedText = new BetterFormattedText("Hello world!");
    formattedText.getRange(6, 10).isCapitalized = true;
    System.out.println(formattedText.toString());
  }
}
