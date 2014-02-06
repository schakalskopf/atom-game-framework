package sg.atom.core.asset;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.asset.Files.FileTypeFilters;
import sg.atom.utils.StringUtil;

public class ResourceUtil {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtil.class);
    public static HashMap<URL, WeakReference<Image>> awtImageMap = new HashMap<URL, WeakReference<Image>>();
    private static String[] currentClasspath;

    /**
     * 즉 여러개의 classpath에 동일한 file들이 있을경우 먼저 기술된 classpath에 있는 file을 선택한다.
     *
     * @param classpaths 첫 키는 folder의 class path, 두번째 키는 file의 classpath, value가
     * 실제 file
     * @return
     * @author mulova
     */
    public static HashMap<String, HashMap<String, File>> listFiles(String... classpaths) {
        HashMap<String, HashMap<String, File>> map = new HashMap<String, HashMap<String, File>>();
        for (int i = classpaths.length - 1; i >= 0; i--) {
            final String classpath = StringUtil.toDirectory(classpaths[i]);
            List<File> files = listFiles(new File(classpath), FileTypeFilters.ALL, null);
            for (File file : files) {
                final File dir = file.getParentFile();
                String key1 = StringUtil.toDirectory(dir.getAbsolutePath());
                String key2 = StringUtil.changeFileSeparator(file.getAbsolutePath());
                key1 = StringUtil.detachPrefix(key1, classpath);
                key2 = "/" + StringUtil.detachPrefix(key2, classpath);
                HashMap<String, File> innerMap = map.get(key1);
                if (innerMap == null) {
                    innerMap = new HashMap<String, File>();
                    map.put(key1, innerMap);
                }
                innerMap.put(key2, file);
            }
        }
        return map;
    }

    public static final List<File> listFiles(final File file, FileTypeFilters fileType, List<File> store) {
        return listFiles(file, ExtFileFilter.valueOf(fileType), store);
    }

    public static final List<File> listFiles(final File[] file, FileTypeFilters filter, List<File> store) {
        for (File f : file) {
            store = listFiles(f, filter, store);
        }
        return store;
    }

    /**
     * @param file
     * @param filter null이면 filter 없이 모든 file을 포함한다.
     * @param store null이면 새로 생성하여 반환하고, null이 아니면 주어진 store에 추가해서 반환한다.
     * @return
     * @author mulova
     */
    public static final List<File> listFiles(final File file, FileFilter filter, List<File> store) {
        if (store == null) {
            store = new LinkedList<File>();
        }
        if (file != null) {
            if (file.isDirectory()) {
                final File[] childFiles = file.listFiles();
                for (final File file2 : childFiles) {
                    listFiles(file2, filter, store);
                }
            } else if (filter.accept(file)) {
                store.add(file);
            }
        }
        return store;
    }

    /**
     * ASCII �̿��� characterset�� �����ϴ� property file loading
     *
     * @param path
     * @param charset
     * @return
     * @throws IOException
     */
    public static final Properties loadProperties(final String path, final String charset) throws IOException {
        URL url = getClasspathResource(path);
        if (url == null) {
            return null;
        }
        final InputStream in = url.openStream();
        final Reader reader = new InputStreamReader(in, charset);
        final Properties prop = new Properties();
        prop.load(reader);
        return prop;
    }

    /**
     * ASCII 이외의 characterset을 지원하는 property file loading
     *
     * @param prop
     * @param path
     * @param charset
     * @throws IOException
     */
    public static final void saveProperties(final Properties prop, final String path, final String charset)
            throws IOException {
        final PrintWriter out = new PrintWriter(path, charset);
        prop.list(out);
        out.close();
    }

    public static final java.awt.Image loadAWTImage(final URL url) {
        if (url == null) {
            return null;
        }
        /*		java.awt.Image image = Toolkit.getDefaultToolkit().getImage(url);
         if (image != null) {
         image.getWidth(null);
         image.getHeight(null);
         return image;
         }*/
        WeakReference<Image> image = awtImageMap.get(url);
        if (image == null || image.get() == null) {
            final ImageIcon icon = new ImageIcon(url);
            log.debug("Loading AWT image: ", url.getPath());
            image = new WeakReference<Image>(icon.getImage());
            awtImageMap.put(url, image);
        }
        return image.get();

    }

    public static final java.awt.Image loadAWTImage(final String path) {
        return ResourceUtil.loadAWTImage(getClasspathResource(path));

    }

    public static final void releaseAWTImage(final String path) {
        URL url = getClasspathResource(path);
        if (url == null) {
            return;
        }
        Toolkit.getDefaultToolkit().getImage(url).flush();
        awtImageMap.remove(getClasspathResource(path));
    }

    public static final void clearAWTImages() {
        for (WeakReference<Image> ref : awtImageMap.values()) {
            Image imagge = ref.get();
            if (imagge != null) {
                imagge.flush();
            }
        }
        awtImageMap.clear();
    }

    public static final boolean hasAlphaChannel(String file) {
        if (file.endsWith(".png")) {
            return true;
        }
        return false;
    }

    /**
     * @param bgImagePath
     * @param replaces	[0] 은 regular expression, [1]은 replaced string
     * @return
     * @throws IOException
     */
    public static final InputStream getSubstringReplacedInputStream(InputStream in, String[][] replaces) throws IOException {
        StringBuilder strBuf = new StringBuilder(1024 * 1024 * 10);
        InputStreamReader reader = new InputStreamReader(in);
        int count = 0;
        final char[] charBuf = new char[1024];
        while ((count = reader.read(charBuf)) > 0) {
            strBuf.append(charBuf, 0, count);
        }
        String str = strBuf.toString();
        for (String[] replace : replaces) {
            str = str.replace(replace[0], replace[1]);
        }

        return new ByteArrayInputStream(str.getBytes("utf-8"));
    }

    /**
     * @param bgImagePath
     * @param replaces	[0] 은 original string, [1]은 replaced string
     * @return
     * @throws IOException
     */
    public static final InputStream getSubstringReplacedInputStream(URL url, String[][] replaces) throws IOException {
        return getSubstringReplacedInputStream(url.openStream(), replaces);
    }

    /**
     * Resource 내용을 읽어 String으로 변환한다.
     *
     * @param url
     * @return
     */
    public static final String loadText(URL url) {
        if (url == null) {
            return null;
        }
        return loadText(url, "UTF-8");
    }

    /**
     * @param url
     * @return
     */
    public static final String loadText(URL url, String encoding) {
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            StringBuilder buf = new StringBuilder();
            while (r.ready()) {
                buf.append(r.readLine()).append('\n');
            }
            r.close();
            return buf.toString();
        } catch (IOException e) {
            log.error("Can't access ", url.getPath(), e);
            return null;
        }
    }

    /**
     * Resource 내용을 읽어 String으로 변환한다. Resource가 없을 경우에는 path를 그대로 반환한다.
     *
     * @param url
     * @return
     */
    public static final String loadText(String path) {
        if (path == null) {
            return "";
        }
        URL url = getClasspathResource(path);
        if (url == null) {
            return path;
        }
        return loadText(url);
    }

    /**
     * file path에서 class path를 추출한다.
     *
     * @param imageLocation
     * @param classpaths
     * @return
     * @author mulova
     */
    public static final String findResource(String imageLocation, String... classpaths) {
        String filename = StringUtil.getFileName(imageLocation);
        for (String cp : classpaths) {
            String path = StringUtil.concatPath(cp, filename);
            URL url = Thread.currentThread().getContextClassLoader().getResource(path);
            if (url != null) {
                return path;
            }
        }
        return null;
    }

    /**
     * pathPrefix 부분이 존재하면 그 이후 부분을 반환한다.<br> classpath 부분을 없애고 resource url을
     * 얻어올 때 사용한다.<br>
     *
     * @param absolutePath
     * @param rootPath
     * @return
     */
    public static final String removePrefix(String absolutePath, String... rootPath) {
        for (String prefix : rootPath) {
            int index = absolutePath.indexOf(prefix);
            if (index >= 0) {
                return absolutePath.substring(index + prefix.length());
            }
        }
        return null;
    }

    /**
     * @param path
     * @param rootPaths 추가적인 classpath. 없을경우 현재 vm의 classpath만을 참고한다.
     * @return
     * @author mulova
     */
    public static final String toRelativePath(String path, String... rootPaths) {
        if (currentClasspath == null) {
            String cp = System.getProperty("java.class.path");
            currentClasspath = cp.split(File.pathSeparator);
            for (int i = 0; i < currentClasspath.length; i++) {
                currentClasspath[i] = StringUtil.toDirectory(currentClasspath[i]);
            }
        }
        for (int i = 0; i < rootPaths.length; i++) {
            rootPaths[i] = StringUtil.toDirectory(rootPaths[i]);
        }

        path = StringUtil.changeFileSeparator(path);
        String location = null;
        if (rootPaths.length > 0) {
            location = removePrefix(path, rootPaths);
        }
        if (location == null) {
            location = removePrefix(path, currentClasspath);
        }
        if (location != null) {
            if (!location.startsWith("/")) {
                location = "/" + location;
            }
        }
        return location;
    }

    public static final File getClasspathResourceAsFile(String path) {
        URL url = getClasspathResource(path);
        if (url == null) {
            return null;
        }
        return new File(url.getFile());
    }

    public static final URL getClasspathResource(String path) {
        if (path == null) {
            return null;
        }
        URL url = null;
        try {
            url = Thread.currentThread().getContextClassLoader().getResource(path);
            if (url != null) {
                return url;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * resource의 url을 반환한다.<br> 먼저 ClassPath에서 찾아보고 없으면 File경로로 찾는다.<br>
     *
     * @param path ClassLoader path ex) /a/b/c<br> File path ex) C:/a/b/c<br>
     * URL format ex) file:/C:/a/b/c<br> 모두 가능하다.
     * @return null if not found
     */
    public static final URL getAnyResource(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }
        URL url = null;
        try {
            url = Thread.currentThread().getContextClassLoader().getResource(path);
            if (url != null) {
                return url;
            }
        } catch (Exception e) {
        }
        File file = new File(path);
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e2) {
            }
        }
        String dir = System.getProperty("user.dir");
        file = new File(StringUtil.concatPath(dir, path));
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e2) {
            }
        }
        return null;
    }

    public static final void copyFile(File src, File dest) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;

        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(dest);
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fcout.close();
            } catch (IOException ioe) {
            }
            try {
                fcin.close();
            } catch (IOException ioe) {
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
        }
    }

    public static String getUserDir(String relativePath) {
        String base = System.getProperty("user.dir");
        return StringUtil.concatPath(base, relativePath);
    }

    public static void AddClasspath(URL url) {
        ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader childClassLoader = new URLClassLoader(new URL[]{url}, parentClassLoader);
        Thread.currentThread().setContextClassLoader(childClassLoader);
    }
}