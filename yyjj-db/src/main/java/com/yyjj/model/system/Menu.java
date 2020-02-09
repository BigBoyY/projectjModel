package com.yyjj.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author yml
 * @since 2019-12-26
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单编号
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 是否启用
     */
    private Integer enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", menuCode=" + menuCode +
            ", menuName=" + menuName +
            ", parentId=" + parentId +
            ", sort=" + sort +
            ", remark=" + remark +
            ", method=" + method +
            ", url=" + url +
            ", enable=" + enable +
        "}";
    }
}
