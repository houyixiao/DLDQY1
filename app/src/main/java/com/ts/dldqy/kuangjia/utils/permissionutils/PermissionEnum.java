package com.ts.dldqy.kuangjia.utils.permissionutils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

/**
 * 枚举Android6.0所有危险权限
 * @author wh
 * 如果之后存在新权限手动添加即可
 */


@SuppressLint("InlinedApi")
public enum PermissionEnum {
    /**
     * 位置权限
     */
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION,"定位"),//通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION,"定位"),//当声明ACCESS_FINE_LOCATION时，拿到的位置信息将更精确（几十米到几百米）
    
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL,"语音信箱"),//允许应用程序添加系统中的语音邮件权限
    
    BODY_SENSORS(Manifest.permission.BODY_SENSORS,"传感器"),//身体传感器
    
    CALL_PHONE(Manifest.permission.CALL_PHONE,"拨打电话"),//允许程序从非系统拨号器里输入电话号码
    
    CAMERA(Manifest.permission.CAMERA,"相机"),//允许访问摄像头进行拍照
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO,"录音"),//录制声音通过手机或耳机的麦克
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS,"手机账户列表"),//访问GMail账户列表
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS,"电话"),//允许程序监视，修改或放弃播出电话
    USE_SIP(Manifest.permission.USE_SIP,"视频"),//允许程序使用SIP视频服务
    SEND_SMS(Manifest.permission.SEND_SMS,"发送短信"),//发送短信
    /**
     * 读取权限
     */
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE,"电话"),//读取手机状态权限
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG,"读取通讯录"),//读取通讯录权限
    READ_CALENDAR(Manifest.permission.READ_CALENDAR,"读取日历"),//允许程序读取用户的日程信息
    READ_CONTACTS(Manifest.permission.READ_CONTACTS,"读取联系人"),//允许应用访问联系人通讯录信息
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE,"读取内存卡"),//读取内存卡权限
    READ_SMS(Manifest.permission.READ_SMS,"读取短信"),//读取短信内容
    /**
     * 写入权限
     */
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG,"写入通讯录"),//写入通讯录权限
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR,"写入日历"),//写入日程，但不可读取
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS,"写入联系人"),//写入联系人，但不可读取
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE,"写入内存卡"),//写入内存卡权限
    /**
     * 接收权限
     */
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS,"接收短信"),//接收短信
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH,"接收Wap Push"),//接收WAP PUSH信息
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS,"接收彩信"),//接收彩信


    /**
     * permission_group
     */
    /**
     * READ_CALENDAR、WRITE_CALENDAR
     */
    GROUP_CALENDAR(Manifest.permission_group.CALENDAR,"日历"),
    /**
     * CAMERA
     */
    GROUP_CAMERA(Manifest.permission_group.CAMERA,"相机"),
    /**
     * READ_CONTACTS、WRITE_CONTACTS、GET_ACCOUNTS
     */
    GROUP_CONTACTS(Manifest.permission_group.CONTACTS,"联系人"),
    /**
     * ACCESS_FINE_LOCATION、ACCESS_COARSE_LOCATION
     */
    GROUP_LOCATION(Manifest.permission_group.LOCATION,"位置"),
    /**
     * RECORD_AUDIO
     */
    GROUP_MICROPHONE(Manifest.permission_group.MICROPHONE,"麦克风"),
    /**
     * READ_PHONE_STATE、CALL_PHONE、READ_CALL_LOG、WRITE_CALL_LOG、ADD_VOICEMAIL、USE_SIP、PROCESS_OUTGOING_CALLS
     */
    GROUP_PHONE(Manifest.permission_group.PHONE,"电话"),
    /**
     * BODY_SENSORS
     */
    GROUP_SENSORS(Manifest.permission_group.SENSORS,"传感器"),
    /**
     * SEND_SMS、RECEIVE_SMS、READ_SMS、RECEIVE_WAP_PUSH、RECEIVE_MMS、READ_CELL_BROADCASTS
     */
    GROUP_SMS(Manifest.permission_group.SMS,"短信"),
    /**
     * READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE
     */
    GROUP_STORAGE(Manifest.permission_group.STORAGE,"内存卡");

    private final String permisson;
    private String name_cn;

    PermissionEnum(String permisson, String name_cn) {
        this.permisson = permisson;
        this.name_cn = name_cn;
    }


    /**
     * 对请求权限的响应遍历
     * @param value
     * @return
     */
    public static PermissionEnum onResultPermissions(@NonNull String value) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (value.equalsIgnoreCase(permissionEnum.permisson)) {
                return permissionEnum;
            }
        }
        return null;
    }

    public String getPermisson() {
        return permisson;
    }

    public String getName_cn() {
        return name_cn;
    }
}
