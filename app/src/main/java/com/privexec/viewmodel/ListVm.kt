package com.privexec.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privexec.Api_Call.WebAPicall
import com.privexec.pojoclass.piiShared.PiiSharedData
import com.privexec.pojoclass.piiTotalApps.PiiTotalAppsResponse
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.privexec.pojoclass.reportAppByCategory.ReportLastTimeUsedPojo
import com.privexec.pojoclass.reportAppByCategory.ReportPotentiallyDataPojo
import com.privexec.pojoclass.totoalAppsPii.TotalAppsPiiSharedData

class ListVm : ViewModel() {
    private val mService  =  WebAPicall()
    fun getPiiData(sessionId: String?, activity: FragmentActivity): MutableLiveData<PiiSharedData>? {

        return mService.getPiiData(sessionId, activity)
    }
    fun getTotalAppsChartData(sessionId: String?, activity: FragmentActivity): MutableLiveData<TotalAppsPiiSharedData>? {

        return mService.getTotalAppsPiiData(sessionId, activity)
    } fun getPiiTotalAppsData(sessionId: String?, activity: FragmentActivity): MutableLiveData<PiiTotalAppsResponse>? {
        return mService.getPiiTotalAppsData(sessionId, activity)
    }

    fun reportOfAppCategoryApi(sessionId: String?, activity: FragmentActivity): MutableLiveData<ReportAppByCategoryPojo>? {
        return mService.reportOfAppCategoryApi(sessionId, activity)
    }
    fun reportOfTimeUsedByAppApi(sessionId: String?, activity: FragmentActivity): MutableLiveData<ReportLastTimeUsedPojo>?  {
        return mService.timeUsedByAppApi(sessionId, activity)
    }

    fun reportOfPersonalPotentialDataApi(sessionId: String?, activity: FragmentActivity): MutableLiveData<ReportPotentiallyDataPojo>? {
        return mService.reportOfPersonalPotentialDataApi(sessionId, activity)
    }

}