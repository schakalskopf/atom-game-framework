/**
 * *****************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * ****************************************************************************
 */
package sg.atom.utils._commons;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import org.apache.commons.lang.StringUtils;
import sg.atom.utils.io.FileHandle;

/**
 * Provides standard access to the filesystem, classpath, Android SD card, and
 * Android assets directory.
 *
 * FIXME: Replace with FileUtils or Guava Files
 *
 * @author mzechner
 * @author Nathan Sweet
 */
@Deprecated
public interface Files {

    /**
     * Indicates how to resolve a path to a file.
     *
     * @author mzechner
     * @author Nathan Sweet
     */
    public enum FileType {

        /**
         * Path relative to the root of the classpath. Classpath files are
         * always readonly. Note that classpath files are not compatible with
         * some functionality on Android, such as
         * {@link Audio#newSound(FileHandle)} and
         * {@link Audio#newMusic(FileHandle)}.
         */
        Classpath,
        /**
         * Path relative to the asset directory on Android and to the
         * application's root directory on the desktop. On the desktop, if the
         * file is not found, then the classpath is checked. This enables files
         * to be found when using JWS or applets. Internal files are always
         * readonly.
         */
        Internal,
        /**
         * Path relative to the root of the SD card on Android and to the home
         * directory of the current user on the desktop.
         */
        External,
        /**
         * Path that is a fully qualified, absolute filesystem path. To ensure
         * portability across platforms use absolute files only when absolutely
         * (heh) necessary.
         */
        Absolute,
        All,
        /**
         * Path relative to the private files directory on Android and to the
         * application's root directory on the desktop.
         */
        Local;
    }

    public enum FileTypeFilters {

        MODEL_COLLADA(".dae"),
        MODEL_OGRE(".scene", ".mesh.xml"),
        MODEL_JME3(".j3o"),
        EXCEL(".xls"),
        MODEL(MODEL_COLLADA, MODEL_OGRE, MODEL_JME3),
        IMAGE(".dds", ".jpg", ".png", ".tga", ".bmp"),
        AUDIO(".ogg", ".wav", ".mp3"),
        SHADER(".vert", ".frag"),
        PROPERTIES(".properties"),
        XML(".xml"),
        ALL("");
        public final String[] EXT;

        private FileTypeFilters(FileTypeFilters... types) {
            LinkedList<String> list = new LinkedList<String>();
            for (FileTypeFilters t : types) {
                for (String ext : t.EXT) {
                    list.add(ext);
                }
            }
            EXT = list.toArray(new String[list.size()]);
        }

        private FileTypeFilters(String... ext) {
            EXT = ext;
        }

        public boolean isApplicable(URL file) {
            for (String ext : EXT) {
                if (StringUtils.endsWithIgnoreCase(file.getPath(), ext)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isApplicable(File file) {
            for (String ext : EXT) {
                if (StringUtils.endsWithIgnoreCase(file.getPath(), ext)) {
                    return true;
                }
            }
            return false;
        }

        public static FileTypeFilters valueOf(File file) {
            for (FileTypeFilters type : values()) {
                if (type.isApplicable(file)) {
                    return type;
                }
            }
            return ALL;
        }

        public static FileTypeFilters valueOf(URL file) {
            for (FileTypeFilters type : values()) {
                if (type.isApplicable(file)) {
                    return type;
                }
            }
            return ALL;
        }
    }

    /**
     * Returns a handle representing a file or directory.
     *
     * @param type Determines how the path is resolved.
     * @throws RuntimeException if the type is classpath or internal and the
     * file does not exist.
     * @see FileType
     */
    public FileHandle getFileHandle(String path, FileType type);

    /**
     * Convenience method that returns a {@link FileType#Classpath} file handle.
     */
    public FileHandle classpath(String path);

    /**
     * Convenience method that returns a {@link FileType#Internal} file handle.
     */
    public FileHandle internal(String path);

    /**
     * Convenience method that returns a {@link FileType#External} file handle.
     */
    public FileHandle external(String path);

    /**
     * Convenience method that returns a {@link FileType#Absolute} file handle.
     */
    public FileHandle absolute(String path);

    /**
     * Convenience method that returns a {@link FileType#Local} file handle.
     */
    public FileHandle local(String path);

    /**
     * Returns the external storage path directory. This is the SD card on
     * Android and the home directory of the current user on the desktop.
     */
    public String getExternalStoragePath();

    /**
     * Returns true if the external storage is ready for file IO. Eg, on
     * Android, the SD card is not available when mounted for use with a PC.
     */
    public boolean isExternalStorageAvailable();

    /**
     * Returns the local storage path directory. This is the private files
     * directory on Android and the directory of the jar on the desktop.
     */
    public String getLocalStoragePath();

    /**
     * Returns true if the local storage is ready for file IO.
     */
    public boolean isLocalStorageAvailable();
}
