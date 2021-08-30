package com.privexec.interfaces

import android.view.View
import com.privexec.pojoclass.piiShared.Rating
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.privexec.pojoclass.reportAppByCategory.ReportLastTimeUsedPojo
import com.privexec.pojoclass.reportAppByCategory.ReportPotentiallyDataPojo
import com.privexec.pojoclass.totoalAppsPii.Result

interface PermissionsReportInterface {
    fun thirdPiiSharePermission( rating: MutableList<Rating>, views: View)
    fun firstSpeedVw_totalAppsPiiShared( result: Result, views: View)
    fun secondBarChartpiitotalAppsData(
        result: MutableList<com.privexec.pojoclass.piiTotalApps.Result>,
        views: View
    )

    fun firstOriginalAppByCategory( reportAppByCategoryLst: MutableList<ReportAppByCategoryPojo.AppsCategoryCount> , views: View)
    fun secondOriginalAppsTimeUsed(reportLstTmUsed: ReportLastTimeUsedPojo, views: View)
    fun thirdOriginalPersonalPotentialData( reportAppByCategoryLst: MutableList<ReportPotentiallyDataPojo.PersonalDataType> , views: View)


}