import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MakeTest {

  public static void main(String[] args) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    List<String> cm = new ArrayList<String>();
    List<String> sm = new ArrayList<String>();
    List<String> tm = new ArrayList<String>();
    List<String> mm = new ArrayList<String>();
    List<String> hm = new ArrayList<String>();
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.contains("TestCliDriver")) {
        cm.add(line.substring(line.indexOf('_') + 1));
      } else if (line.contains("TestSparkCliDriver")) {
        sm.add(line.substring(line.indexOf('_') + 1));
      } else if (line.contains("TestMiniTezCliDriver")) {
        tm.add(line.substring(line.indexOf('_') + 1));
      } else if (line.contains("TestMinimrCliDriver")) {
        mm.add(line.substring(line.indexOf('_') + 1));
      } else if (line.contains("TestHBaseCliDriver")) {
        hm.add(line.substring(line.indexOf('_') + 1));
      } else {
        String target = "results/clientpositive/";
        int index1 = line.indexOf(target);
        int index2 = line.lastIndexOf(".q");
        if (index1 < 0 || index2 < 0) {
          continue;
        }
        String sub = line.substring(index1 + target.length(), index2);
        if (sub.startsWith("spark/")) {
          sm.add(sub.substring(6));
        } else if (sub.startsWith("tez/")) {
          tm.add(sub.substring(4));
        } else {
          cm.add(sub);
        }
      }
    }
    StringBuilder builder = new StringBuilder();
    appendTest("cm", cm, builder);
    appendTest("sm", sm, builder);
    appendTest("tm", tm, builder);
    appendTest("mm", mm, builder);
    appendTest("hm", hm, builder);

    System.out.println(builder.toString());
  }

  private static void appendTest(String name, List<String> tests, StringBuilder builder) {
    if (!tests.isEmpty()) {
      builder.append("hivetest2 ").append(name);
      for (String test : tests) {
        builder.append(' ').append(test);
      }
      builder.append(";");
    }
  }

}
