package com.yyjj.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yml
 * @since 2020-01-08
 */
public class SubMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父id
     */
    private Integer menuId;

    /**
     * 菜单编号
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

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
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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
        return "SubMenu{" +
            "id=" + id +
            ", menuId=" + menuId +
            ", menuCode=" + menuCode +
            ", menuName=" + menuName +
            ", sort=" + sort +
            ", remark=" + remark +
            ", method=" + method +
            ", url=" + url +
            ", enable=" + enable +
        "}";
    }
}
