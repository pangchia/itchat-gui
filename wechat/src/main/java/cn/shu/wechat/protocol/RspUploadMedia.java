package cn.shu.wechat.protocol;

import cn.shu.wechat.beans.sync.BaseResponse;

/**
 * @作者 舒新胜
 * @项目 weixin
 * @创建时间 2/24/2021 12:13 PM
 */
public class RspUploadMedia {
    public cn.shu.wechat.beans.sync.BaseResponse BaseResponse;
    public String MediaId;
    public long StartPos;
    public int CDNThumbImgHeight;
    public int CDNThumbImgWidth;

    public RspUploadMedia() {
    }
}
