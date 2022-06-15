package com.permissionx.tangzndev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

//typealias用于指定别名
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    private var calllback: PermissionCallback? = null

    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        calllback = cb
        requestPermissions(permission, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            calllback?.let { it(allGranted, deniedList) }
        }
    }
}