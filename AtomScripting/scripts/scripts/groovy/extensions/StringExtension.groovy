package groovyex

/**
 * An extension class for the String class.
 * 
 * @author cuong.nguyenmanh2
 */
public class StringExtension {
   public static String reverseToUpperCase(String self) {
      StringBuilder sb = new StringBuilder(self);
      sb.reverse();
      return sb.toString().toUpperCase();
   }
}

