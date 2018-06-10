package patterns.builder;

public class StringBuilderDemo {
  public static void main(String[] args) {
    String hello = "hello";
    System.out.println("<p>" + hello + "</p>");

    /* without string builder */
    String[] words = {"hello", "world"};
    System.out.println(
        "<ul>\n"
            + "<li>" + words[0] + "</li>\n"
            + "<li>" + words[1] + "</li>\n"
            + "</ul>"
    );

    /* with string builder */
    StringBuilder sb = new StringBuilder();
    // build through several function calls (build piecewise)
    sb.append("<ul>\n");
    for (String word : words) {
      sb.append(String.format("  <li>%s</li>\n", word));
    }
    sb.append("</ul>");
    System.out.println(sb);
  }
}
