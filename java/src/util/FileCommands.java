package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;

/**
 * Provides methods for doing stuff with files.
 * 
 * @author Sebastian Erdweg
 */
public class FileCommands {

  public final static boolean DO_DELETE = true;

  public final static String TMP_DIR;
  static {
    try {
      TMP_DIR = File.createTempFile("tmp", "").getParent();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static String newTempFile(String suffix) throws IOException {
    File f =
        File.createTempFile(
            "MZ",
            suffix == null || suffix.isEmpty() ? suffix : "." + suffix);
    return f.getAbsolutePath();
  }

  public static void deleteTempFiles(String file) throws IOException {
    if (file == null)
      return;
    
    String parent = new File(file).getParent();
    
    if (parent == null)
      return;
    else if (parent.equals(TMP_DIR))
      delete(file);
    else 
      deleteTempFiles(parent);
  }
  
  public static void delete(String file) throws IOException {
    if (file == null)
      return;
    
    if (new File(file).listFiles() != null)
      for (File f : new File(file).listFiles())
        FileCommands.delete(f.getAbsolutePath());
    
    new File(file).delete();
  }

  public static void copyFile(String from, String to) throws IOException {
    FileInputStream fis = new FileInputStream(new File(from));
    createFile(to);
    FileOutputStream fos = new FileOutputStream(new File(to));
    copyFile(fis, fos);
    fis.close();
    fos.close();
  }
  
  public static void copyFile(InputStream in, OutputStream out) throws IOException {
    int len;
    byte[] b = new byte[1024];

    while ((len = in.read(b)) > 0)
      out.write(b, 0, len);
  }

  /**
   * Beware: one must not rename SDF files since the filename and
   * the module name needs to coincide. Instead generate a new
   * file which imports the other SDF file.
   * 
   * @param file
   * @param content
   * @throws IOException
   */
  public static void writeToFile(String file, String content)
      throws IOException {
    FileCommands.createFile(file);
    FileOutputStream fos = new FileOutputStream(new File(file));
    fos.write(content.getBytes());
    fos.close();
  }

  public static void appendToFile(String file, String content)
      throws IOException {
    createFile(file);
    FileOutputStream fos = new FileOutputStream(new File(file), true);
    fos.write(content.getBytes());
    fos.close();
  }
  
  public static String readFileAsString(String fileString) throws IOException {
    return readAsString(new FileReader(fileString));
  }

  // from http://snippets.dzone.com/posts/show/1335
  // Author: http://snippets.dzone.com/user/daph2001
  public static String readAsString(Reader reader) throws IOException {
    StringBuilder fileData = new StringBuilder(1000);
    BufferedReader bufferedreader = new BufferedReader(reader);
    char[] buf = new char[1024];
    int numRead = 0;
    while ((numRead = bufferedreader.read(buf)) != -1)
      fileData.append(buf, 0, numRead);

    bufferedreader.close();
    return fileData.toString();
  }

  public static String newTempDir() throws IOException {
    final File f = File.createTempFile("SugarJ", "");
    // need to delete the file, but want to reuse the filename
    f.delete();
    f.mkdir();
    return f.getAbsolutePath();
  }

  public static void prependToFile(String file, String head) throws IOException {
    File tmp = new File(newTempFile(""));
    new File(file).renameTo(tmp);

    FileInputStream in = new FileInputStream(tmp);
    FileOutputStream out = new FileOutputStream(new File(file));

    out.write(head.getBytes());

    int len;
    byte[] b = new byte[1024];

    while ((len = in.read(b)) > 0)
      out.write(b, 0, len);

    in.close();
    out.close();
    delete(tmp.getAbsolutePath());
  }

  public static void createFile(String file) throws IOException {
    File f = new File(file);
    if (f.getParentFile().exists() || f.getParentFile().mkdirs())
      f.createNewFile();
  }

  /**
   * Create file with name deduced from hash in dir.
   * 
   * @param dir
   * @param hash
   * @return
   * @throws IOException
   */
  public static String createFile(String dir, int hash) throws IOException {
    String p = dir + "/" + hashFileName("MZ", hash);
    createFile(p);
    return p;
  }

  public static void createDir(String dir) throws IOException {
    new File(dir).mkdirs();
  }

  /**
   * Create directory with name deduced from hash in dir.
   * 
   * @param dir
   * @param hash
   * @return
   * @throws IOException
   */
  public static String createDir(String dir, int hash) throws IOException {
    String p = dir + "/" + hashFileName("MZ", hash);
    createDir(p);
    return p;
  }

  /**
   * Ensures that a path is suitable for a cygwin command line.
   */
  public static String toCygwinString(String filepath) {
    // XXX hacky

    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      filepath = filepath.replace("\\", "/");
      filepath = filepath.replace("/C:/", "/cygdrive/C/");
      filepath = filepath.replace("C:/", "/cygdrive/C/");
    }

    return filepath;
  }

  /**
   * Ensure that a path is suitable for a windows command line
   */
  public static String toWindowsString(String filepath) {
    // XXX hacky

    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      filepath = filepath.replace("/cygdrive/C", "C:");
      filepath = filepath.replace("/C:", "C:");
      filepath = filepath.replace("/", "\\");
    }

    return filepath;
  }

  public static boolean exists(String file) {
    return file != null && new File(file).exists();
  }
  
  public static boolean exists(URI file) {
    return new File(file).exists();
  }

  public static String hashFileName(String prefix, int hash) {
    return prefix + (hash < 0 ? "1" + Math.abs(hash) : "0" + hash);
  }

  public static String hashFileName(String prefix, Object o) {
    return hashFileName(prefix, o.hashCode());
  }

  public static String getExtension(File infile) {
    return getExtension(infile.getName());
  }
  
  public static String getExtension(String infile) {
    int i = infile.lastIndexOf('.');
    
    if (i > 0)
      return infile.substring(i+1, infile.length());
    
    return null;
  }
  
  public static String dropExtension(String file) {
    int i = file.lastIndexOf('.');
    int sep = file.lastIndexOf('/');
    
    if (i > 0 && i > sep)
      return file.substring(0, i);
    
    return file;
  }

  public static String dropFilename(String file) {
	  int i = file.lastIndexOf("/");
	  if (i > 0) 
		  return file.substring(0,i);
	  
	  return "";
  }

  public static int fileHash(String file) throws IOException {
    if (exists(file))
      return readFileAsString(file).hashCode();
    
    return 0;
  }
}
