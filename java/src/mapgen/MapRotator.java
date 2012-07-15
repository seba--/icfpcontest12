package mapgen;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import game.Board;
import game.State;
import game.StaticConfig;
import util.FileCommands;
import util.Pair;

public class MapRotator {
  public static void main(String[] args) throws IOException {
    if (args.length > 0) {
      LinkedList<File> files = new LinkedList<File>();
      files.add(new File(args[0]));
      
      while (!files.isEmpty()) {
        File file = files.poll();
        if (file.isDirectory())
          for (File other : file.listFiles())
            files.add(other);
        else
          main(file, FileCommands.readFileAsString(file.getAbsolutePath()));
      }
    } else {
      System.out.println("Please tell the map rotator which files to process.");
      System.exit(-1);
    }
  }
  
  public static void main(File file, String text) throws IOException {
    Pair<StaticConfig, State> orig = State.parse(text);
    String tmpName = FileCommands.dropExtension(file.getName());

    System.out.println("parsing " + tmpName);
    for (int i = 0; i < 5; i++) {
      String rotFileName = tmpName + ".r" + i + ".map";
      Pair<StaticConfig, State> processed = rotateMap(i, orig);
      System.out.println("writing " + rotFileName);
      String s = file.getAbsolutePath();
      FileCommands.writeToFile(s.substring(0, s.length() - file.getName().length()) + rotFileName, processed.b.board.toString());
    }
    
  }

  public static Pair<StaticConfig, State> rotateMap(int orientation, Pair<StaticConfig, State> p) {
    // orientation: 0 - rotate -90, 1 - rotate +90, 2 - rotate 180, 3 - flip horiz, 4 - flip vert
    
    Board b = p.b.board;
    Board rb = null;
    
    if (orientation == 3) { // flip horiz
      rb = new Board(b.width, b.height);
      for (int x = 0; x < rb.width; x++) {
        for (int y = 0; y < rb.height; y++) {
          rb.set((b.width-1) - x, y, b.get(x, y));
        }
      }
    } else if (orientation == 4) { // flip vert
      rb = new Board(b.width, b.height);
      for (int x = 0; x < rb.width; x++) {
        for (int y = 0; y < rb.height; y++) {
          rb.set(x, (b.height-1)-y, b.get(x, y));
        }
      }
    } else if (orientation == 2) { // rotate 180
      return rotateMap(4, rotateMap(3, p));
    } else if (orientation == 1) { // rotate +90
      rb = new Board(b.height, b.width);

      for (int i = 0; i < b.width; i++) {
        for (int j = 0; j < b.height; j++) {
          rb.set(j, (b.width - 1) - i, b.get(i, j));
        }
      }
    } else if (orientation == 0) { // rotate -90
      return rotateMap(1, rotateMap(1, rotateMap(1, p)));
    }
    
    Pair<StaticConfig, State> ret = new Pair<StaticConfig, State>(p.a, new State(rb));
    return ret;
  }
  
  
}
