package cn.shu.wechat.beans.pojo;

import lombok.Data;

/**
 * @作者 舒新胜
 * @项目 AutoWechat
 * @创建时间 7/1/2021 6:24 PM
 */
@Data
public class Status {
    private String name;

    /**
     * 防撤回状态1防撤回2关闭防撤回
     */
    private Short undoStatus;

    /**
     * 自动回复状态1自动回复2不自动回复
     */
    private Short autoStatus;
}