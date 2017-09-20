package com.ts.dldqy.kuangjia.utils.permissionutils;

import java.util.ArrayList;

/**
 * 权限请求回调接口
 * @author wh
 */

public interface PerimissionsCallback {
    //权限被允许
    void onGranted(ArrayList<PermissionEnum> grantedList);
    //权限被拒绝
    void onDenied(ArrayList<PermissionEnum> deniedList);
}
