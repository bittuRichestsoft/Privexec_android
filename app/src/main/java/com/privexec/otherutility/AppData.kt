package com.privexec.otherutility

import android.app.Application
import android.content.pm.PackageInfo

class AppData : Application() {
    var packageInfo:PackageInfo
        get():PackageInfo {

            return packageInfo
        }
        set(packageInfo: PackageInfo) {
            this.packageInfo = packageInfo
        }

    /*fun getPackageInfo():PackageInfo {
        return packageInfo
    }
    fun setPackageInfo(packageInfo: PackageInfo) {
        this.packageInfo = packageInfo
    }
    */
}