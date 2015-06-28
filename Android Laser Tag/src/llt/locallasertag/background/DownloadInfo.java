package llt.locallasertag.background;

import android.util.Log;
import android.widget.ProgressBar;


public class DownloadInfo {
  private final static String TAG = DownloadInfo.class.getSimpleName();
  public enum DownloadState {
    NOT_STARTED,
    QUEUED,
    DOWNLOADING,
    COMPLETE
  }
  private volatile DownloadState mDownloadState = DownloadState.NOT_STARTED;
  private String mFilename;
  private int avatar;
  private volatile Integer mProgress;
  private final Integer mFileSize;
  private volatile ProgressBar mProgressBar;
  
  public DownloadInfo(String filename, Integer size, int avaNum) {
    mFilename = filename;
    mProgress = 100;
    mFileSize = size;
    avatar = avaNum;
    mProgressBar = null;
  }
  
  public ProgressBar getProgressBar() {
    return mProgressBar;
  }
  public void setProgressBar(ProgressBar progressBar) {
   // Log.d(TAG, "setProgressBar " + mFilename + " to " + progressBar);
    mProgressBar = progressBar;
  }
  
  public void setDownloadState(DownloadState state) {
    mDownloadState = state;
  }
  public DownloadState getDownloadState() {
    return mDownloadState;
  }
  
  public Integer getProgress() {
    return mProgress;
  }

  public void setProgress(Integer progress) {
    this.mProgress = progress;
  }

  public Integer getFileSize() {
    return mFileSize;
  }
  public int getAvatar()
  {
	  return avatar;
  }
  public void setAvatar(int num)
  {
	  this.avatar = num;
  }
  public String getFilename() {
    return mFilename; //file name is player name
  }
  public void setFilename(String name) {
	    this.mFilename= name;
  }
}
