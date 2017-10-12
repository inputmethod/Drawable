package com.typany.keyboard.sound;

/**
 * Created by yangfeng on 2017/9/22.
 */
public class SoundItem {
    private int reportId = 0;
    private boolean isMore = false;
    private String folder = null;
    private boolean selected = false;

    private boolean isRemote = false;
//    private int remoteId; // reuse reportId
//    private String remoteName; // reuse folder
    private long remoteFileSize;
    private String remoteUrl;
    private String previewUrl;

    public long getRemoteFileSize() {
        return remoteFileSize;
    }

    public void setRemoteFileSize(long remoteFileSize) {
        this.remoteFileSize = remoteFileSize;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public SoundItem(int id, String folder, boolean isMore) {
        this.reportId = id;
        this.isMore = isMore;
        this.folder = folder;
    }

    public boolean isMore() {
        return isMore;
    }

    public String getFolder() {
        return folder;
    }

    public int getReportId() {
        return reportId;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected() {
        return selected;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }
}
