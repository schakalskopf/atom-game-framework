package sg.atom.utils.io;

import java.io.File;
import java.io.FilenameFilter;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.lang.StringUtils;
import sg.atom.utils._commons.Files.FileTypeFilters;

/**
 * FIXME: repplace with FileUtils of Common IO
 *
 * @author CuongNguyen
 */
@Deprecated
public class ExtFileFilter extends FileFilter implements java.io.FileFilter, FilenameFilter {

    private static final ExtFileFilter[] filters = new ExtFileFilter[FileTypeFilters.values().length];

    static {
        for (FileTypeFilters type : FileTypeFilters.values()) {
            filters[type.ordinal()] = create(type.EXT, type.name().toLowerCase());
        }
    }

    public static ExtFileFilter valueOf(FileTypeFilters type) {
        if (type == null) {
            return null;
        }
        return filters[type.ordinal()];
    }

    public static ExtFileFilter[] getAllFilters() {
        return filters;
    }

    public static ExtFileFilter create(String[] ext, String description) {
        final ExtFileFilter filter = new ExtFileFilter(ext);
        filter.setCaseSensitive(false);
        filter.setDescription(description);
        return filter;
    }
    private final String[] extensions;
    private String[] extensionsCase;
    private String description;
    private boolean caseSensitive;

    public ExtFileFilter(final String... extensions0) {
        this.extensions = extensions0;
        this.description = "";
        setCaseSensitive(true);
    }

    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        return accept(f.getParentFile(), f.getName());

    }

    @Override
    public boolean accept(File dir, String name) {
        for (String ext : this.extensionsCase) {
            if (!this.caseSensitive) {
                name = name.toLowerCase();
            }
            if (StringUtils.endsWithIgnoreCase(name, ext)) {
                return true;
            }
        }
        return false;
    }

    //The description of this filter
    @Override
    public String getDescription() {
        return this.description;
    }

    public void setCaseSensitive(final boolean caseSensitive0) {
        this.caseSensitive = caseSensitive0;
        if (caseSensitive0) {
            extensionsCase = extensions;
        } else {
            extensionsCase = new String[extensions.length];
            for (int i = 0; i < extensions.length; i++) {
                extensionsCase[i] = extensions[i].toLowerCase();
            }
        }
    }

    public void setDescription(final String desc) {
        this.description = desc;
    }
}
