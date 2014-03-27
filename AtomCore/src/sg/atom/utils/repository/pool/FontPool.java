package sg.atom.utils.repository.pool;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils._commons.ResourceUtils;

/**
 * Pool for fonts.
 *
 * @author mulova
 */
public class FontPool {

    private static final Logger log = LoggerFactory.getLogger(FontPool.class);
    private static final HashMap<String, Font> pool = new HashMap<String, Font>();
    private static Font DEFAULT = new Font("Dialog", Font.PLAIN, 12);
    public static final String KOREAN_DOTUM = "Dotum";
    public static final String KOREAN_DOTUMCHE = "DotumChe";							// fixed width
    public static final String KOREAN_GULIM = "Gulim";
    public static final String KOREAN_GULIMCHE = "GulimChe";							// fixed width
    public static final String KOREAN_BATANG = "Batang";
    public static final String KOREAN_BATANGCHE = "BatangChe";							// fixed width
    public static final String KOREAN_GUNGSUH = "Gungsuh";
    public static final String KOREAN_GUNGSUHCHE = "GungsuhChe";						// fixed width
    public static final String KOREAN_NEW_GULIM = "New Gulim";
    public static final String KOREAN_MALGUN_GOTHIC = "Malgun Gothic";

    public static Font getDefault() {
        return DEFAULT;
    }

    public static void setDefault(Font font) {
        DEFAULT = font;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font getBase(final String path) throws FontFormatException {
        Font instance = pool.get(path);
        if (instance == null) {
            synchronized (FontPool.class) {
                if (instance == null) {
                    // font file로 부터 읽는다.
                    final URL url = ResourceUtils.getClasspathResource(path);
                    if (url != null) {
                        try {
                            InputStream in = url.openStream();
                            instance = Font.createFont(Font.TRUETYPE_FONT, in);
                            in.close();
                        } catch (final IOException e1) {
                        }
                    }
                    if (instance == null) {
                        try {
                            instance = new Font(path, Font.PLAIN, 10);
                        } catch (Exception e) {
                            instance = DEFAULT;
                        }
                    }
                    pool.put(path, instance);
                }
            }
        }
        return instance;
    }

    public static Font getFont(final Font font, final float size, final int style) {
        final String name = font.getName();
        final String key = getKey(name, style, size);
        final Font instance = pool.get(key);
        if (instance != null) {
            return instance;
        }

        final Font derived = font.deriveFont(style, size);
        pool.put(key, derived);
        return derived;
    }

    public static Font getFont(final String path, final float size, final int style) {
        final String key = getKey(path, style, size);
        final Font instance = pool.get(key);
        if (instance != null) {
            return instance;
        }

        Font base = null;
        try {
            base = getBase(path);
        } catch (final Exception e) {
            log.warn("can't access " + path, e);
            base = DEFAULT;
        }
        final Font derived = base.deriveFont(style, size);
        pool.put(key, derived);
        return derived;
    }

    public static Font getFont(final String path, final float size) {
        return getFont(path, size, Font.PLAIN);
    }

    private static String getKey(final String path, final int style, final float size) {
        return path + style + "," + size;
    }
}
