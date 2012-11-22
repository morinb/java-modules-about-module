package org.bm.modules.about.frame;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.bm.modules.shared.IModule;

public class AboutModulesDatas {
   static final String LINE_SEPARATOR_PROPERTY_NAME = "line.separator";

   static final String JAVA_CLASS_PATH_PROPERTY_NAME = "java.class.path";

   static final String JAVA_LIBRARY_PATH_PROPERTY_NAME = "java.library.path";

   static final String SUN_BOOT_CLASS_PATH_PROPERTY_NAME = "sun.boot.class.path";

   public static void appentLoadedModules(final StringBuilder sb, final Collection<IModule> modulesList, final String indent) {

      StringBuilder loadedModules = new StringBuilder("Loaded modules :\n");
      StringBuilder unloadedModules = new StringBuilder("Unloaded modules :\n");
      for (IModule m : modulesList) {
         if (m.isActive()) {
            loadedModules.append(indent).append(m.getName()).append(" [").append(m.getVersion()).append("]\n");
         } else {
            unloadedModules.append(indent).append(m.getName()).append(" [").append(m.getVersion()).append("]\n");
         }
      }
      sb.append(loadedModules.toString());
      sb.append(unloadedModules.toString());
   }

   public static void appendSystemProperties(final StringBuilder stringBuffer, final Properties systemProperties,
      final String indent) {
      final Enumeration<?> propertyNames = systemProperties.propertyNames();
      final String pathIndent = indent + "  ";
      while (propertyNames.hasMoreElements()) {
         stringBuffer.append(indent);
         String name = (String) propertyNames.nextElement();
         if (name.toLowerCase().endsWith(".path")) {
            stringBuffer.append(name);
            stringBuffer.append(" = ");
            appendPath(stringBuffer, systemProperties.getProperty(name), pathIndent);
            // Don't append separator here because done in appendPath.
         } else if (LINE_SEPARATOR_PROPERTY_NAME.equals(name)) {
            final char[] separatorChars = systemProperties.getProperty(name).toCharArray();
            stringBuffer.append(LINE_SEPARATOR_PROPERTY_NAME);
            stringBuffer.append(" =");
            for (final char c : separatorChars) {
               stringBuffer.append(" 0x");
               // Thanx to  Jon A. Cruz for this:
               // (http://www.thescripts.com/forum/thread15875.html)
               stringBuffer.append(Integer.toHexString(0x100 | (0x0ff & c)).substring(1).toUpperCase());
            }
            stringBuffer.append("\n");
         } else {
            String value = systemProperties.getProperty(name);
            stringBuffer.append(name);
            stringBuffer.append(" = ");
            stringBuffer.append(value);
            stringBuffer.append("\n");
         }
      }
   }

   /**
    * Feeds a <code>StringBuffer</code> with one classpath entry per line.
    *
    * @param stringBuffer a non-null <code>StringBuffer</code>.
    * @param classpath    a string containing classpath as returned by
    *                     <code>System.getProperty( "java.class.path" )</code>.
    * @param indent       a non-null <code>String</code> containing
    *                     the prefix for indenting lines (four spaces characters are usually ok).
    */
   public static void appendPath(final StringBuilder stringBuffer, final String classpath, final String indent) {
      final StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
      final File[] classpathEntries = new File[tokenizer.countTokens()];
      int i = 0;
      while (tokenizer.hasMoreTokens()) {
         classpathEntries[i] = new File(tokenizer.nextToken());
         i++;
      }
      Arrays.sort(classpathEntries, new Comparator<File>() {
         @Override
         public int compare(File o1, File o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      for (File classpathEntry : classpathEntries) {
         stringBuffer.append("\n");
         stringBuffer.append(indent);
         stringBuffer.append(classpathEntry);
      }
      stringBuffer.append("\n");

   }

}
