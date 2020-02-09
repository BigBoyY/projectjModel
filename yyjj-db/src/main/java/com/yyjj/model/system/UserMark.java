package com.yyjj.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户收藏表
 * </p>
 *
 * @author yml
 * @since 2019-12-26
 */
public class UserMark implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户收藏id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 项目id
     */
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "UserMark{" +
            "id=" + id +
            ", userId=" + userId +
            ", projectId=" + projectId +
        "}";
    }
}
