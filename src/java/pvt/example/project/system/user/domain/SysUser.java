package pvt.example.project.system.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import pvt.example.common.basic.BaseEntity;
import pvt.example.project.system.role.domain.SysRole;

import java.util.Date;

/**
 * 类&emsp;&emsp;名：SysUser <br/>
 * 描&emsp;&emsp;述：用户对象 sys_user
 */
public class SysUser extends BaseEntity {
    /** 用户ID */
    private Long userId;
    /** 用户名称 */
    private String userName;
    /** 登录名称 */
    private String nickName;
    /** 用户邮箱 */
    private String email;
    /** 手机号码 */
    private String phone;
    /** 用户性别 */
    private String gender;
    /** 用户头像 */
    private String avatar;
    /** 密码 */
    private String password;
    /** 帐号状态（0正常 1停用） */
    private String status;
    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
    /** 最后登录IP */
    private String loginIp;
    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date loginDate;
    /** 角色对象 */
    private SysRole sysRole;

    public SysUser() {}

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate=" + loginDate +
                ", sysRole=" + sysRole +
                "} " + super.toString();
    }
}
