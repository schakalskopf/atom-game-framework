package sg.atom.utils.pool;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils.monitor.MemoryTracer;

/**
 * get(int width, int height)�� ���´�. TextureGraphics������ ����Ѵ�. ���� �����
 * �ʿ䰡 ���.
 *
 * @author mulova
 *
 */
public class ImagePool extends AbstractPool<BufferedImageKey, BufferedImage> {

    private static final Logger log = LoggerFactory.getLogger(ImagePool.class);
    public static final AffineTransform TX_IDENTITY = new AffineTransform();
    public static ImagePool singleton;

    private ImagePool() {
        super();
    }

    public static ImagePool instance() {
        if (singleton == null) {
            synchronized (ImagePool.class) {
                if (singleton == null) {
                    singleton = new ImagePool();
                }
            }
        }
        return singleton;
    }

    public static BufferedImage getBufferedImage(int width, int height, boolean alpha) {
        return instance().getInstance(Integer.valueOf(width), Integer.valueOf(height), alpha);
    }

    @Override
    /**
     * ���� ImageGraphics���� mipmap���� �������� �ʱ� ������ pool���� �������
     * ��� mipmap�� �����Ͽ� ���� �� ���.
     *
     * @param name
     * @param width
     * @param height
     * @param mipmap
     * @param category
     * @return
     */
    protected BufferedImage create(Object... keys) {
        int width = nearestPowerOfTwo((Integer) keys[0]);
        int height = nearestPowerOfTwo((Integer) keys[1]);
        boolean alpha = (Boolean) keys[2];

        int imageType = alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(width, height, imageType);
        MemoryTracer.trace(image);
        log.info("Graphics2D {}x{} [Creation Count]{}", new Object[]{width, height, this.getCreationCount()});
        return image;
    }

    @Override
    protected BufferedImageKey getKey(Object... keys) {
        if (keys.length != 3) {
            throw new RuntimeException("Key should be {width, height}");
        }
        return new BufferedImageKey((Integer) keys[0], (Integer) keys[1], (Boolean) keys[2]);
    }

    @Override
    protected BufferedImageKey getKey(BufferedImage graphics) {
        return getKey(graphics.getWidth(), graphics.getHeight(), hasAlpha(graphics));
    }

    public static void releaseImage(BufferedImage instance) {
        instance().release(instance);
    }

    @Override
    protected void dispose(BufferedImageKey key, BufferedImage image) {
//		graphics.dispose();
    }

    @Override
    protected void initialize(BufferedImageKey key, BufferedImage instance) {
    }

    @Override
    protected void postRelease(BufferedImageKey key, BufferedImage instance) {
    }

    public static boolean hasAlpha(BufferedImage image) {
        if (image == null) {
            return true;
        }
        switch (image.getType()) {
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                return true;
            default:
                return false;
        }
    }

    /**
     * @param number
     * @return the closest power of two to the given number.
     */
    public static int nearestPowerOfTwo(final int number) {
        return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));
    }
}
