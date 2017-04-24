package com.hd.rxjavaandretrofit;

/**
 * Created by hudong on 2017/4/24.
 */
public class BeanPlugin {
    /**
     * id : 1000
     * packagename : com.zcckj.xscan
     * versioncode : 122
     * versionname : 1.2.2
     * downloadurl : http://60.205.213.50/study/XPDA_TMARKET_1.2.2_20170419.apk
     */

    private int id;
    private String packagename;
    private int versioncode;
    private String versionname;
    private String downloadurl;

    public void setId(int id) {
        this.id = id;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public int getId() {
        return id;
    }

    public String getPackagename() {
        return packagename;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public String getDownloadurl() {
        return downloadurl;
    }
}
