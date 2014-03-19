package sg.atom.core.asset;

import java.io.IOException;
import sg.atom.core.monitor.IProgress;

public interface IPreload {

    /**
     * preload asset.
     *
     * @throws IOException
     */
    public void preload() throws IOException;

    /**
     * @param progress to handle progress bar for monitoring.
     */
    public void setProgress(IProgress progress);

    public String getName();
}
