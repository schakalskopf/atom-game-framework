package sg.atom.core.asset;

import java.io.IOException;
import sg.atom.utils.monitor.IProgress;


public interface IPreload {
	/**
	 * preload를 시작한다.
	 * @throws IOException
	 */
	public void preload() throws IOException;
	/**
	 * @param progress 진행상태를 알려줄 progress bar
	 */
	public void setProgress(IProgress progress);
        
	public String getName();
}
