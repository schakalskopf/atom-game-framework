package sg.atom.utils;

import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils.io.EncodingType;
import sg.atom.utils.pool.StringBuilderPool;

public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    //private static LinkedList<IName> namers = new LinkedList<IName>();
    private static final String REGEX_DOUBLE = "[ \t]*[1-9]*[0-9]+\\.?[0-9]*(E[+-][1-9]*[0-9])?";
    private static Pattern PATTERN_DOUBLE = Pattern.compile(REGEX_DOUBLE);

    public static String concat(final Object... args) {
        if (args == null) {
            return "";
        }
        if (args.length == 1) {
            return args[0].toString();
        }
        // compute string length
        int size = 1;
        String[] str = new String[args.length];
        for (int i = 0; i < str.length; i++) {
            if (args[i] == null) {
                continue;
            }
            str[i] = args[i].toString();
            size += str[i].length();
        }
        final StringBuilder buf = StringBuilderPool.fetchInstance();
        buf.ensureCapacity(size);
        concat(buf, args);
        String ret = buf.toString();
        StringBuilderPool.releaseInstance(buf);
        return ret;
    }

    public static void concat(final StringBuilder buf, final Object... args) {
        for (final Object msg : args) {
            if (msg == null) {
                continue;
            }
            buf.append(msg);
        }
    }

    /**
     * 'x'로 구분된 형식의 resolution string에서 width, height를 얻어온다. 예) 1024x768
     *
     * @param str
     * @return index 0: width, index 1: height. 형식에 맞지 않으면 null을 반환한다.
     */
    public static int[] parseResolutionStr(final String str) {
        if (str == null) {
            return null;
        }
        final int[] res = new int[2];
        final int sepIndex = str.indexOf('x');
        if (sepIndex <= 0) {
            return null;
        }
        res[0] = Integer.parseInt(str.substring(0, sepIndex).trim());
        res[1] = Integer.parseInt(str.substring(sepIndex + 1).trim());
        return res;
    }

    public static String extractNumber(CharSequence str) {
        int start = -1;
        int end = str.length();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (start < 0 && (Character.isDigit(c) || c == '.')) {
                start = i;
            } else if (start >= 0 && end == str.length() && !(Character.isDigit(c) || c == '.')) {
                end = i;
                break;
            }
        }
        if (start == 0 && end == str.length()) {
            return str.toString();
        }
        return str.subSequence(start, end).toString();
    }

    public static int[] parseVersion(String version) {
        if (version == null) {
            return new int[]{0};
        }
        String[] subversion = extractNumber(version).split("\\.");
        int[] parsed = new int[subversion.length];
        for (int i = 0; i < parsed.length; i++) {
            parsed[i] = Integer.parseInt(subversion[i]);
        }
        return parsed;
    }

    /**
     * @param str '(x, y, z)' 형식으로 이루어진 string에서 Vector3f 추출
     * @param from str에서 '(' 가 시작되는 index
     * @return
     */
    public static double[] parseVector3(String str, int from) {
        int xStart = str.indexOf("(", from) + 1;
        int xEnd = str.indexOf(",", xStart);
        int yStart = xEnd + 1;
        int yEnd = str.indexOf(",", yStart);
        int zStart = yEnd + 1;
        int zEnd = str.indexOf(")", zStart);
        double x = getFloat(str, xStart, xEnd);
        double y = getFloat(str, yStart, yEnd);
        double z = getFloat(str, zStart, zEnd);
        return new double[]{x, y, z};
    }

    private static float getFloat(CharSequence str, int begin, int end) {
        String floatStr = str.subSequence(begin, end).toString();
        floatStr.trim();
        if (floatStr.endsWith("f")) {
            floatStr = floatStr.substring(0, floatStr.length() - 1);
        }
        try {
            return Float.valueOf(floatStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param command
     * @return
     */
    public static double getDouble(final CharSequence text, int beginIndex) {
        if (beginIndex >= text.length()) {
            return Double.NaN;
        }
        Pattern pattern = null;
        int groupIndex = 0;
        if (beginIndex == 0) {
            pattern = PATTERN_DOUBLE;
        } else {
            String PATTERN = StringUtil.concat("(.{", beginIndex, "})(", REGEX_DOUBLE + ")");
            pattern = Pattern.compile(PATTERN);
            groupIndex = 2;
        }
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(groupIndex));
        }
        return Double.NaN;
    }

    /**
     * path중 file명을 제외한 앞쪽 directory (separator '/' 포함)
     *
     * @param path
     * @return
     */
    public static String getDirectory(final String path) {
        if (path == null) {
            return null;
        }
        final int index1 = path.lastIndexOf('/');
        final int index2 = path.lastIndexOf('\\');
        final int index = Math.max(index1, index2);
        if (index < 0 || index + 1 >= path.length()) {
            return null;
        }
        return path.substring(0, index + 1);
    }

    /**
     * path를 제외한 file이름(확장자 포함)
     *
     * @param path
     * @return
     */
    public static String getFileName(final String path) {
        if (path == null) {
            return null;
        }
        final int index1 = path.lastIndexOf('/');
        final int index2 = path.lastIndexOf('\\');
        final int index = Math.max(index1, index2);
        if (index < 0 || index + 1 >= path.length()) {
            return path;
        }
        return path.substring(index + 1);
    }

    public static String getFileNameWithoutExt(final String path) {
        String filename = getFileName(path);
        if (filename == null) {
            return null;
        }
        final int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(0, index);
        }
        return filename;
    }

    public static String removeExt(final String path) {
        if (path == null) {
            return null;
        }
        final int index = path.lastIndexOf('.');
        if (index < 0) {
            return path;
        }
        return path.substring(0, index);
    }

    public static String toDirectory(String path) {
        if (path == null) {
            return null;
        }
        path = changeFileSeparator(path);
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }

    public static String[] toClassPath(String classpathRoot, String... path) {
        classpathRoot = toDirectory(classpathRoot);
        String[] ret = new String[path.length];
        for (int i = 0; i < path.length; i++) {
            String clsPath = path[i];
            clsPath = clsPath.replace('\\', '/');
            int index = clsPath.indexOf(classpathRoot);
            if (index >= 0) {
                clsPath = clsPath.substring(index + classpathRoot.length());
            }
            if (clsPath.indexOf(":") >= 0) {
                throw new RuntimeException("Can't remove base path " + classpathRoot + " from " + clsPath);
            }
            if (!clsPath.startsWith("/")) {
                clsPath = "/" + clsPath;
            }
            ret[i] = clsPath;
        }
        return ret;
    }

    /**
     * @param paths 동일한 classpath root에 포함된 파일만을 대상으로 한다. 다른 classpath root일 경우는
     * subsequent call을 수행하여야 한다.
     * @return null if they can't be transformed
     * @author mulova
     */
    public static String[] toClassPath(String... paths) {
        String classpathRoots = System.getProperty("java.class.path");
        String[] classpath = classpathRoots.split(File.pathSeparator);
        for (String entry : classpath) {
            if (containsPath(paths[0], entry)) {
                return toClassPath(entry, paths);
            }
        }
        return null;
    }

    /**
     * @param path	path
     * @param subPath directory path
     * @return true if str has the directory path of 'subPath'
     * @author mulova
     */
    public static boolean containsPath(CharSequence path, CharSequence subPath) {
        if (path.length() < subPath.length()) {
            return false;
        }
        for (int i = 0; i < subPath.length(); i++) {
            char c1 = path.charAt(i);
            char c2 = path.charAt(i);
            if (c1 != c2 && (!isPathSeparator(c1) || !isPathSeparator(c2))) {
                return false;
            }
        }
        if (path.length() == subPath.length()) {
            return true;
        }
        char lastChar = subPath.charAt(subPath.length() - 1);
        return isPathSeparator(lastChar) || isPathSeparator(path.charAt(subPath.length()));
    }

    public static boolean isPathSeparator(char c) {
        return c == '/' || c == '\\';
    }

    public static String changeFileSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.indexOf('\\') >= 0) {
            path = path.replace('\\', '/');
        }
        return path;
    }

    /**
     * 2진수, 16진수 등의 숫자를 공백을 제거하고 parsing한다.
     *
     * @param number
     */
    public static int parseByRadix(final CharSequence number, final int radix) {
        final StringBuilder buf = StringBuilderPool.fetchInstance();
        // ��� ����
        for (int i = 0; i < number.length(); i++) {
            final char c = number.charAt(i);
            if (c != ' ') {
                buf.append(c);
            }
        }
        String str = buf.toString();
        StringBuilderPool.releaseInstance(buf);
        return Integer.parseInt(str, radix);
    }

    public static void replace(StringBuilder buf, String from, String to) {
        int index = buf.indexOf(from);
        while (index >= 0) {
            buf.replace(index, index + from.length(), to);
            index = buf.indexOf(from);
        }
    }

    /**
     * path에서 확장자를 추출한다.
     *
     * @param path
     * @return 없으면 null을 반환한다.
     */
    public static String getExtension(final String path) {
        if (path == null) {
            return null;
        }
        final int index = path.lastIndexOf('.');
        if (index < 0 || index >= path.length()) {
            return null;
        }
        return path.substring(index, path.length());
    }

    public static String concatPath(String... paths) {
        String concat = concatPath('/', paths);
        return changeFileSeparator(concat);
    }

    /**
     * Directory와 File을 결합하여 File Path를 만든다.
     *
     * @param dir
     * @param file
     * @return
     */
    public static String concatPath(char separator, String... paths) {
        if (paths == null || paths.length == 0) {
            return null;
        }
        if (paths.length == 1) {
            return paths[0];
        }
        StringBuilder builder = StringBuilderPool.fetchInstance();
        builder.append(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            if (builder.length() > 0) {
                char end = builder.charAt(builder.length() - 1);
                if (!isSeparatorChar(end)) {
                    builder.append(separator);
                }
            }
            String path = paths[i];
            if (path == null || path.isEmpty()) {
                continue;
            }
            char start = path.charAt(0);
            if (isSeparatorChar(start)) {
                path = path.substring(1);
            }
            builder.append(path);

        }
        String result = builder.toString();
        StringBuilderPool.releaseInstance(builder);
        return result;
    }

    private static boolean isSeparatorChar(char c) {
        return c == '/' || c == File.separatorChar;
    }

    /**
     * @param fileName
     * @param count 두자리로 가정한다.
     * @return
     */
    public static String addFilePostfix(String fileName, int count) {
        String file = removeExt(fileName);
        String ext = getExtension(fileName);
        String countStr = count < 10 ? "_0" + count : "_" + count;
        if (ext != null) {
            return file + countStr + ext;
        }
        return file + countStr;
    }

    /**
     * @param fileName
     * @param count 두자리로 가정한다.
     * @return
     */
    public static boolean isSameBasePath(String fileName1, String fileName2) {
        if (fileName1 == null || fileName2 == null) {
            return false;
        }
        int i = 0;
        int j = 0;

        // skip base
        final int length1 = fileName1.length();
        final int length2 = fileName2.length();
        char c1 = 0;
        char c2 = 0;
        while (c1 == c2) {
            if (i >= length1 || j >= length2) {
                return length1 == length2; // ������ ���ڿ�
            }
            c1 = fileName1.charAt(i);
            c2 = fileName2.charAt(j);
            i++;
            j++;
        }
        if (c1 == '_') {
            i++;
            if (i < length1) {
                c1 = fileName1.charAt(i);
            }
        }
        if (c2 == '_') {
            j++;
            if (j < length2) {
                c2 = fileName2.charAt(j);
            }
        }

        while (Character.isDigit(c1) && i < length1) {
            c1 = fileName1.charAt(i);
            i++;
        }
        while (Character.isDigit(c2) && j < length2) {
            c2 = fileName2.charAt(j);
            j++;
        }

        if (i == length1 && j == length2) {
            return true; // ���� ��� Ȯ���� ���� ���
        }
        if (i == length1 || j == length2) {
            return false; // ���ʸ� Ȯ���� ���� ���
        }
        if (c1 != c2 || c1 != '.') {
            return false;
        }

        while (c1 == c2) {
            if (i == length1 && j == length2) {
                return true;
            }
            if (i == length1 || j == length2) {
                return false;
            }
            c1 = fileName1.charAt(i);
            c2 = fileName2.charAt(j);
            i++;
            j++;
        }
        return false;
    }

    public static String detachPrefix(String name, String prefix) {
        if (name == null) {
            return null;
        }
        if (prefix == null) {
            return name;
        }
        int i = name.indexOf(prefix);
        if (i >= 0) {
            return name.substring(i + prefix.length());
        }
        return name;
    }

    /**
     * 뒷쪽의 숫자를 뺀다.
     *
     * @param name
     * @return
     */
    public static String detachPostfix(String name) {
        if (name == null) {
            return null;
        }
        int index = name.length() - 1;
        if (index < 0 || !Character.isDigit(name.charAt(index))) {
            return name;
        }
        for (int i = index - 1; i >= 0; i--) {
            char c = name.charAt(i);
            if (!Character.isDigit(c) && c != '_') {
                return name.substring(0, i + 1);
            }
        }
        return null;

    }

    /**
     * Postfix가 숫자일 경우 그 값을 반환한다.
     *
     * @param name
     * @return 없으면 0을 반환한다.
     */
    public static int getPostfix(String name) {
        if (name == null) {
            return 0;
        }
        int index = name.length() - 1;
        int digit = 1;
        int postfix = 0;
        // Ȯ���� ���� ����ģ��.
        while (!Character.isDigit(name.charAt(index))) {
            index--;
            if (index < 0) {
                return 0;
            }
        }
        while (Character.isDigit(name.charAt(index)) && index >= 0) {
            postfix += (name.charAt(index) - '0') * digit;
            index--;
            digit *= 10;
        }
        return postfix;
    }

    /**
     * 끝에 숫자가 있으면 _(underscore)를 추가하고 숫자를 붙인다.
     *
     * @param str
     * @param no
     * @return
     * @author mulova
     */
    public static String attachPostfix(String str, int no, int digit) {
        if (no < 0) {
            throw new IllegalArgumentException(String.valueOf(no));
        }
        StringBuilder buf = StringBuilderPool.fetchInstance();
        StringBuilder postfix = StringBuilderPool.fetchInstance();
        buf.append(str);

        final int numberDigit = no == 0 ? 1 : (int) Math.log10(no) + 1;
        for (int i = numberDigit; i < digit; i++) {
            postfix.append('0');
        }
        postfix.append(no);
        attachPostfix(buf, postfix.toString());
        String ret = buf.toString();
        StringBuilderPool.releaseInstance(postfix);
        StringBuilderPool.releaseInstance(buf);
        return ret;
    }

    /**
     * 끝에 숫자가 있으면 _(underscore)를 추가하고 숫자를 붙인다.
     *
     * @param str
     * @param no
     * @return
     * @author mulova
     */
    public static String attachPostfix(String str, int no) {
        return attachPostfix(str, no, 0);
    }

    /**
     * 끝에 숫자가 있으면 _(underscore)를 추가하고 숫자를 붙인다.
     *
     * @param playerName
     * @return
     */
    public static void attachPostfix(StringBuilder buf, Object postfix) {
        int last = buf.length() - 1;
        char lastChar = buf.charAt(last);
        if (Character.isDigit(lastChar)) {
            buf.append('_');
        }
        buf.append(postfix);
    }

    /**
     * 확장자를 고려해서 _(underscore)를 추가하고 postfix를 붙인다.
     *
     * @param underscore
     * @param playerName
     * @return
     */
    public static String attachPostfix(String str, String postfix, boolean underscore) {
        StringBuilder buf = StringBuilderPool.fetchInstance();
        buf.append(str);
        attachPostfix(buf, postfix, underscore);
        String ret = buf.toString();
        StringBuilderPool.releaseInstance(buf);
        return ret;
    }

    /**
     * 확장자를 고려해서 _(underscore)를 추가하고 postfix를 붙인다.
     *
     * @param underscore
     * @param playerName
     * @return
     */
    public static void attachPostfix(StringBuilder buf, String postfix, boolean underscore) {
        int dotIndex = buf.lastIndexOf(".");
        if (dotIndex > 0) {
            if (underscore && postfix.charAt(0) != '_') {
                buf.insert(dotIndex, "_");
                buf.insert(dotIndex + 1, postfix);
            } else {
                buf.insert(dotIndex, postfix);
            }
        } else {
            if (underscore) {
                buf.append('_');
            }
            buf.append(postfix);
        }
    }

    /**
     * 현재 BinaryExporter에서는 한글에 문제가 있다
     *
     * @param str
     * @return
     */
    public static String encodeKorean(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new String(str.getBytes("iso-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            log.warn("Encoding error", e);
        }
        return null;
    }

    public static String encode(String in, EncodingType encodeFrom, EncodingType encodeTo) {
        return new String(in.getBytes(encodeFrom.getCharset()), encodeTo.getCharset());
    }

    public static boolean encode(InputStream in, OutputStream out, EncodingType encodeFrom, EncodingType encodeTo) throws IOException {
        InputStreamReader reader = new InputStreamReader(in, encodeFrom.getCharset());
        OutputStreamWriter writer = new OutputStreamWriter(out, encodeTo.getCharset());

        for (int ch; (ch = reader.read()) != -1;) {
            writer.write(ch);
            if (ch == 0xfffd) {
                // --- 오류 : 유니코드로 표현할 수 없는 문자임
                reader.close();
                writer.close();
                return false;
            }
        }
        writer.close();
        reader.close();
        return true;
    }

    // http://www.java2s.com/Code/Java/2D-Graphics-GUI/WrapstringaccordingtoFontMetrics.htm
    /**
     * Returns an array of strings, one for each line in the string after it has
     * been wrapped to fit lines of <var>maxWidth</var>. Lines end with any of
     * cr, lf, or cr lf. A line ending at the end of the string will not output
     * a further, empty string. <p> This code assumes <var>str</var> is not
     * <code>null</code>.
     *
     * @param str the string to split
     * @param fm needed for string width calculations
     * @param maxWidth the max line width, in points
     * @return a non-empty list of strings
     */
    public static List wrap(String str, FontMetrics fm, int maxWidth) {
        List lines = splitIntoLines(str);
        if (lines.size() == 0) {
            return lines;
        }

        ArrayList strings = new ArrayList();
        for (Iterator iter = lines.iterator(); iter.hasNext();) {
            wrapLineInto((String) iter.next(), strings, fm, maxWidth);
        }
        return strings;
    }

    /**
     * Given a line of text and font metrics information, wrap the line and add
     * the new line(s) to <var>list</var>.
     *
     * @param line a line of text
     * @param list an output list of strings
     * @param fm font metrics
     * @param maxWidth maximum width of the line(s)
     */
    public static void wrapLineInto(String line, List list, FontMetrics fm, int maxWidth) {
        int len = line.length();
        int width;
        while (len > 0 && (width = fm.stringWidth(line)) > maxWidth) {
            // Guess where to split the line. Look for the next space before
            // or after the guess.
            int guess = len * maxWidth / width;
            String before = line.substring(0, guess).trim();

            width = fm.stringWidth(before);
            int pos;
            if (width > maxWidth) // Too long
            {
                pos = findBreakBefore(line, guess);
            } else { // Too short or possibly just right
                pos = findBreakAfter(line, guess);
                if (pos != -1) { // Make sure this doesn't make us too long
                    before = line.substring(0, pos).trim();
                    if (fm.stringWidth(before) > maxWidth) {
                        pos = findBreakBefore(line, guess);
                    }
                }
            }
            if (pos == -1) {
                pos = guess; // Split in the middle of the word
            }
            list.add(line.substring(0, pos).trim());
            line = line.substring(pos).trim();
            len = line.length();
        }
        if (len > 0) {
            list.add(line);
        }
    }

    /**
     * Returns the index of the first whitespace character or '-' in
     * <var>line</var> that is at or before <var>start</var>. Returns -1 if no
     * such character is found.
     *
     * @param line a string
     * @param start where to star looking
     */
    public static int findBreakBefore(String line, int start) {
        for (int i = start; i >= 0; --i) {
            char c = line.charAt(i);
            if (Character.isWhitespace(c) || c == '-') {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first whitespace character or '-' in
     * <var>line</var> that is at or after <var>start</var>. Returns -1 if no
     * such character is found.
     *
     * @param line a string
     * @param start where to star looking
     */
    public static int findBreakAfter(String line, int start) {
        int len = line.length();
        for (int i = start; i < len; ++i) {
            char c = line.charAt(i);
            if (Character.isWhitespace(c) || c == '-') {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns an array of strings, one for each line in the string. Lines end
     * with any of cr, lf, or cr lf. A line ending at the end of the string will
     * not output a further, empty string. <p> This code assumes <var>str</var>
     * is not
     * <code>null</code>.
     *
     * @param str the string to split
     * @return a non-empty list of strings
     */
    public static List splitIntoLines(String str) {
        ArrayList strings = new ArrayList();

        int len = str.length();
        if (len == 0) {
            strings.add("");
            return strings;
        }

        int lineStart = 0;

        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (c == '\r') {
                int newlineLength = 1;
                if ((i + 1) < len && str.charAt(i + 1) == '\n') {
                    newlineLength = 2;
                }
                strings.add(str.substring(lineStart, i));
                lineStart = i + newlineLength;
                if (newlineLength == 2) // skip \n next time through loop
                {
                    ++i;
                }
            } else if (c == '\n') {
                strings.add(str.substring(lineStart, i));
                lineStart = i + 1;
            }
        }
        if (lineStart < len) {
            strings.add(str.substring(lineStart));
        }

        return strings;
    }

    public static boolean isVersionGreaterOrEqual(String ver1, String ver2) {
        if (ver1 == null || ver2 == null || ver1.length() == 0 || ver2.length() == 0) {
            return false;
        }
        int[] version1 = StringUtil.parseVersion(ver1);
        int[] version2 = StringUtil.parseVersion(ver2);
        for (int i = 0; i < Math.min(version1.length, version2.length); i++) {
            if (version1[i] == version2[i]) {
                continue;
            }
            return version1[i] > version2[i];
        }
        return version1.length >= version2.length;
    }

    /**
     * @param command
     * @return null string이거나 숫자가 없을 경우 length 0 인 배열을 반환한다.
     */
    public static int[] parseIntegers(final String text) {
        if (text == null) {
            return new int[0];
        }
        final String[] tokens = text.split("[ ,]");
        final int[] ret = new int[tokens.length];
        int length = 0;
        for (int i = 0; i < ret.length; i++) {
            if (tokens[i].length() == 0) {
                continue;
            }
            try {
                ret[length] = Integer.parseInt(tokens[i]);
                length++;
            } catch (NumberFormatException e) {
            }
        }
        return Arrays.copyOf(ret, length);
    }

    /**
     * substring이 단어인지 여부를 판단한다.
     *
     * @param string
     * @param index
     * @param length
     * @return substring이 단어이면 true
     * @author mulova
     */
    public static boolean isWord(String string, int index, int length) {
        if (string.length() < index + length || index < 0) {
            return false;
        }
        final int index2 = index + length;
        boolean beforeLetter = index > 0 && Character.isLetter(string.charAt(index - 1));
        boolean afterLetter = index2 < string.length() && Character.isLetter(string.charAt(index2));
        if (beforeLetter || afterLetter) {
            if (Character.isLowerCase(string.charAt(index))) { // 첫글자가 대문자가 아니면
                return false;
            }
            for (int i = 1; i < length; i++) {
                if (Character.isUpperCase(string.charAt(index + i))) {
                    return false;
                }
            }
            if (index2 < string.length() && Character.isLowerCase(string.charAt(index2))) { // 다음 글자가 소문자이면
                return false;
            }
        }
        return true;
    }

    /**
     * 해당 단어를 포함하고 있는지를 판단한다.
     *
     * @param string
     * @param word
     * @return
     * @author mulova
     */
    public static boolean hasWord(String string, String word) {
        int index = string.indexOf(word);
        while (index >= 0) {
            int length = word.length();
            if (StringUtil.isWord(string, index, length)) {
                return true;
            }
            index = string.indexOf(word, index + length);
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
/*
    public static void addNamer(IName namer) {
        namers.add(namer);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        for (IName n : namers) {
            String name = n.getName(obj);
            if (name != null) {
                return name;
            }
        }
        return obj.toString();
    }
    */ 
}
