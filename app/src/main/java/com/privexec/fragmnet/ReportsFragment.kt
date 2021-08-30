package com.privexec.fragmnet

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.imanoweb.calendarview.CalendarListener
import com.imanoweb.calendarview.CustomCalendarView
import com.imanoweb.calendarview.DayDecorator
import com.imanoweb.calendarview.DayView
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Saplash
import com.privexec.adapter.Rating_Adapter
import com.privexec.adapter.ReportAppByCategoryAdapter
import com.privexec.databinding.FragmentReportsBinding
import com.privexec.interfaces.PermissionsReportInterface
import com.privexec.pojoclass.piiShared.Rating
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.privexec.pojoclass.reportAppByCategory.ReportLastTimeUsedPojo
import com.privexec.pojoclass.reportAppByCategory.ReportPotentiallyDataPojo
import com.privexec.pojoclass.totoalAppsPii.Result
import com.privexec.utills.CalendarUtils
import com.privexec.viewmodel.ListVm
import kotlinx.android.synthetic.main.fragment_reports.*
import kotlinx.android.synthetic.main.header.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import me.ithebk.barchart.BarChartModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReportsFragment : Fragment(), PermissionsReportInterface, View.OnClickListener {
var TAG="ReportsFragment "
    private var viewBinding: FragmentReportsBinding? = null
    var pieData: ArrayList<SliceValue>? = null
    var pieChartData: PieChartData? = null
    var pieChartDataForCat: PieChartData? = null
    var listVm: ListVm? = null
    private var views: View? = null
    private var pieChart : PieChart? = null
    private var ratingPiiSharePermission: MutableList<Rating>? = ArrayList()
    private var totalAppsResult: Result? = null
    private var piiTotalAppsResult: MutableList<com.privexec.pojoclass.piiTotalApps.Result>? = ArrayList()
    private var firstOriginalAppByCatLst: MutableList<ReportAppByCategoryPojo.AppsCategoryCount>? = ArrayList()
    private var secondOriginalAppsTmUsedData:ReportLastTimeUsedPojo? =ReportLastTimeUsedPojo()
    private var thirdOriginalPrsnlPotDataLst: MutableList<ReportPotentiallyDataPojo.PersonalDataType>? = ArrayList()
    var colors16 = intArrayOf()
    val labels = java.util.ArrayList<String>()

    override fun thirdPiiSharePermission(rating: MutableList<Rating>, views: View) {
        ratingPiiSharePermission!!.clear()
        ratingPiiSharePermission = rating
        if (!ratingPiiSharePermission.isNullOrEmpty()) {
            setThirdCircularPieChartData ()
        viewBinding!!.circularPiechart.pieChartData = pieChartData
         }
    }

    override fun firstSpeedVw_totalAppsPiiShared(result: Result, views: View) {
        totalAppsResult = result
        setFirstTotalAppsPieChartData(totalAppsResult!!)
        viewBinding!!.speedViewChartCircle.maxSpeed=totalAppsResult!!.totalApps
        viewBinding!!.speedViewChartCircle.speedTo(totalAppsResult!!.piiSharedApps)
        viewBinding!!.tvMaxAppsSpeedVw.setText("" + totalAppsResult!!.totalApps)
    }

    override fun secondBarChartpiitotalAppsData(
        result: MutableList<com.privexec.pojoclass.piiTotalApps.Result>,
        views: View
    ) {
        piiTotalAppsResult!!.clear()
        piiTotalAppsResult =result
        setSecondBarChartData(views)
    }

    override fun firstOriginalAppByCategory(
        reportAppByCategoryLst: MutableList<ReportAppByCategoryPojo.AppsCategoryCount>,
        views: View
    ) {
      Log.e(TAG, "firstOriginalAppByCategory  reportAppByCategoryLst=${reportAppByCategoryLst.size}")
        firstOriginalAppByCatLst!!.clear()
        firstOriginalAppByCatLst =reportAppByCategoryLst
        if (!firstOriginalAppByCatLst.isNullOrEmpty()) {
            setFirstOriginalChartData(views)
         //   pieChartDataForCat!!.setCenterCircleScale(10.4f)
        }
    }

    override fun secondOriginalAppsTimeUsed(reportLstTmUsed: ReportLastTimeUsedPojo, views: View) {
        secondOriginalAppsTmUsedData=reportLstTmUsed
        Log.e(
            TAG,
            " secondOriginalAppsTimeUsed  within 3 mnths=${secondOriginalAppsTmUsedData!!._3Months.size} <<>> within 6 mnths=${secondOriginalAppsTmUsedData!!._6Months.size}  <<>> before 6 mnths=${secondOriginalAppsTmUsedData!!.notUsedLast6Month.size}"
        )

         var underThreeMnth=0
        var underSixMnth=0
        var beforeSixMnth=(secondOriginalAppsTmUsedData!!.totalCount -  (secondOriginalAppsTmUsedData!!._3Months.size + secondOriginalAppsTmUsedData!!._6Months.size))
        var allAppsCount=secondOriginalAppsTmUsedData!!.totalCount ;
        viewBinding!!.tvUsedTmBasedAppsCount.text="Total Apps- "+allAppsCount


              if(secondOriginalAppsTmUsedData!!._3Months.size>0)
{
    underThreeMnth=secondOriginalAppsTmUsedData!!._3Months.size
}

        if(secondOriginalAppsTmUsedData!!._6Months.size>0)
        {
            underSixMnth=secondOriginalAppsTmUsedData!!._6Months.size
        }
       /* if(secondOriginalAppsTmUsedData!!.notUsedLast6Month.size>0)
        {
            beforeSixMnth=secondOriginalAppsTmUsedData!!.notUsedLast6Month.size
        }
*/
        pieData = java.util.ArrayList()
        pieData!!.clear()

                //  Log.e("rating all Permissions $i", " gfjhdg: "+100f/ratingPiiSharePermission!!.size.toFloat()+"  Size: "+ratingPiiSharePermission!!.size+ " name:  "+ratingPiiSharePermission!!.get(i).name+"   color="+color[i])

            if(underThreeMnth>0) {
               /*  var sliceUnderThreeMnth = allAppsCount / underThreeMnth*/
                pieData!!.add(
                    SliceValue(
                        underThreeMnth.toFloat(),
                        /*Color.GREEN*/  Color.parseColor("#10E119")
                    ).setLabel("" + underThreeMnth)
                )
            }
            else{
                pieData!!.add(
                    SliceValue(
                        0.001f,
                        /*Color.GREEN*/Color.parseColor("#10E119")
                    ).setLabel("" + 0)
                )
             }


        if(underSixMnth>0) {
          /*  var sliceUnderSixMnth = allAppsCount / underSixMnth*/
            pieData!!.add(
                SliceValue(
                    underSixMnth.toFloat(),
                      Color.parseColor("#FFC107")
                ).setLabel("" + underSixMnth)
            )
        }
else{
            pieData!!.add(
                SliceValue(
                    0.001f,
                    Color.parseColor("#FFC107")
                ).setLabel("" + 0)
            )
         }

        if(beforeSixMnth>0) {
           // var sliceUnderThreeMnth = allAppsCount / underThreeMnth
            pieData!!.add(
                SliceValue(
                    beforeSixMnth.toFloat(),
                    Color.parseColor("#F44336")
                ).setLabel("" + beforeSixMnth)
            )
        }
        else{
            pieData!!.add(
                SliceValue(
                    0.001f, /*Color.RED*/Color.parseColor("#F44336")
                ).setLabel("" + 0)
            )

        }

        viewBinding!!.tvUsedInLastMonth.text="Used in the last 3 months- "+secondOriginalAppsTmUsedData!!._3Months.size
        viewBinding!!.tvUsedInLast6Months.text= "Used in the last 3 to 6 months- "+secondOriginalAppsTmUsedData!!._6Months.size

            //        var totalsize=  secondOriginalAppsTmUsedData!!.totalCount

        viewBinding!!.tvNotUsedInLast6Months.text="Not used within last 6 months- "+  (secondOriginalAppsTmUsedData!!.totalCount -  (secondOriginalAppsTmUsedData!!._3Months.size + secondOriginalAppsTmUsedData!!._6Months.size))


        pieChartData = PieChartData(pieData)
        pieChartData!!.setHasLabels(true).setValueLabelTextSize(16);
        pieChartData!!.setHasCenterCircle(true)
        pieChart!!.setHasTransientState(true)
        viewBinding!!.appsTimeBaseApps.pieChartData = pieChartData
        viewBinding!!.appsTimeBaseApps.visibility=View.VISIBLE
    }


    override fun thirdOriginalPersonalPotentialData(
        reportPotentiallyDataLst: MutableList<ReportPotentiallyDataPojo.PersonalDataType>,
        views: View
    ) {
        Log.e(
            TAG,
            "thirdOriginalPrsnlPotDataLst  reportPotentiallyDataLst=${reportPotentiallyDataLst.size}"
        )
        thirdOriginalPrsnlPotDataLst!!.clear()
        thirdOriginalPrsnlPotDataLst =reportPotentiallyDataLst
        if (!thirdOriginalPrsnlPotDataLst.isNullOrEmpty()) {
            setThirdOriginalChartData(views)
         //   pieChartDataForCat!!.setCenterCircleScale(10.4f)
        }
    }

    private fun setThirdOriginalChartData(views: View) {
         if(thirdOriginalPrsnlPotDataLst!=null){
            //var: List<BarChartModel> = ArrayList()
        var barChartModelList = ArrayList<BarChartModel>()
             for (i in 0 until thirdOriginalPrsnlPotDataLst!!.size){

                 val barChartModel = BarChartModel()
                 barChartModel.setBarValue(thirdOriginalPrsnlPotDataLst!!.get(i).count)
                 barChartModel.setBarColor(Color.parseColor("" + thirdOriginalPrsnlPotDataLst!!.get(i).hexaColor))
                 barChartModel.setBarTag("sfsfa")
                 barChartModel.setBarText(
                     "" +thirdOriginalPrsnlPotDataLst!!.get(i).typeName + " (${
                         thirdOriginalPrsnlPotDataLst!!.get(i).count
                     })"
                 )
             if(thirdOriginalPrsnlPotDataLst!!.get(i).count>0) {
                 barChartModelList.add(barChartModel)


             }
                 Log.e(
                     TAG,
                     "setThirdOriginalChartData Size=" + thirdOriginalPrsnlPotDataLst!!.get(i).count + " inner Size: " + thirdOriginalPrsnlPotDataLst!!.get(
                         i
                     )
                         .typeName
                 )

            }

             Log.e(TAG,"setThirdOriginalChartData Size=" + thirdOriginalPrsnlPotDataLst!!.size)
             Log.e(
                 TAG,
                 " barChartModelList size: " +barChartModelList.size)

                 var appsPersonalPotentialData = this.views!!.findViewById<me.ithebk.barchart.BarChart>(
                 R.id.appsPersonalPotentialData
             )

             var tvPotentialDataNotFound = this.views!!.findViewById<TextView>(
                 R.id.tv_potentialDataNotFound
             )

             if(barChartModelList.size==0)
             {
                 tvPotentialDataNotFound.visibility=View.VISIBLE
                 appsPersonalPotentialData.visibility=View.GONE
             }
             else
             {
                 tvPotentialDataNotFound.visibility=View.GONE
                 appsPersonalPotentialData.visibility=View.VISIBLE

             }

             appsPersonalPotentialData.setBarMaxValue(100);
             appsPersonalPotentialData.addBar(barChartModelList)
               appsPersonalPotentialData.setOnBarClickListener(object :
                 me.ithebk.barchart.BarChart.OnBarClickListener {
                 override
                 fun onBarClick(barChartModel: BarChartModel?) {
                     Toast.makeText(
                         context,
                         "" + barChartModel!!.getBarText(),
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             })
         }
    }

    private fun setFirstOriginalChartData(views: View) {
        pieData = java.util.ArrayList()
        pieData!!.clear()
        var             barChartModelList = ArrayList<BarChartModel>()

        if(firstOriginalAppByCatLst!=null){
            Log.e(TAG+"", "setFirstOriginalChartData  Size: " + firstOriginalAppByCatLst!!.size)
            for (i in 0 ..((firstOriginalAppByCatLst!!.size)-1)){
                var  sliceValue =  firstOriginalAppByCatLst!!.get(i).categoriesCount

                    pieData!!.add(
                        SliceValue(
                            sliceValue.toFloat(),
                            Color.parseColor(firstOriginalAppByCatLst!![i].hexaColor)
                        )//.setLabel("" + firstOriginalAppByCatLst!![i].categoriesName)
                    ) //.setLabel(ratingAllPermission!!.get(i).name+""))
                val barChartModel = BarChartModel()
                barChartModel.setBarValue(firstOriginalAppByCatLst!![i].categoriesCount)
                barChartModel.setBarColor(Color.parseColor("" + firstOriginalAppByCatLst!![i].hexaColor))
                barChartModel.setBarTag("sfsfa")
                barChartModel.setBarText(
                    "" + firstOriginalAppByCatLst!![i].categoriesName+ " (${
                        firstOriginalAppByCatLst!![i].categoriesCount
                    })"
                )
                barChartModelList.add(barChartModel)
            }
        setAppCatWithColorAdap(firstOriginalAppByCatLst!!)
        }

        pieChartData = PieChartData(pieData)
        pieChartData!!.setHasLabels(true).setValueLabelTextSize(10);
        pieChart!!.setHasTransientState(true)
        viewBinding!!.appsByCatPieChart.pieChartData = pieChartData


    var appsByCatBarChart = this.views!!.findViewById<me.ithebk.barchart.BarChart>(
        R.id.appsByCat_barChart
    )

    appsByCatBarChart.setBarMaxValue(100);
    appsByCatBarChart.addBar(barChartModelList)
    appsByCatBarChart.setOnBarClickListener(object :
        me.ithebk.barchart.BarChart.OnBarClickListener {
        override
        fun onBarClick(barChartModel: BarChartModel?) {
            Toast.makeText(
                context,
                "" + barChartModel!!.getBarText(),
                Toast.LENGTH_SHORT
            ).show()
        }
    })


    }

    private fun setAppCatWithColorAdap( firstOriginalAppByCatLst: MutableList<ReportAppByCategoryPojo.AppsCategoryCount>) {

        val myFiles = firstOriginalAppByCatLst
        val array = arrayListOf<ReportAppByCategoryPojo.AppsCategoryCount>()
        array.addAll(myFiles)
        Log.e(TAG,"setAppCatWithColorAdap array.size="+  array.size)

        var ratingAdapter = ReportAppByCategoryAdapter(activity!!, array)
        viewBinding!!.rvAppCatWithColor!!.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        viewBinding!!.rvAppCatWithColor!!.setHasFixedSize(true)
        viewBinding!!.rvAppCatWithColor!!.setItemAnimator(DefaultItemAnimator())
        viewBinding!!.rvAppCatWithColor!!.adapter = ratingAdapter



    }

    private fun setThirdCircularPieChartData () {
        var color : ArrayList<Int> = ArrayList()
        color.add(Color.RED)
        color.add(Color.BLUE)
        color.add(Color.GRAY)
        color.add(Color.GREEN)
        color.add(Color.BLACK)
        color.add(Color.MAGENTA)
        color.add(Color.LTGRAY)
        color.add(Color.CYAN)
        color.add(Color.YELLOW)
        color.add(Color.DKGRAY)
        pieData = java.util.ArrayList()
        pieData!!.clear()
         if(ratingPiiSharePermission!=null){
             Log.e("rating all Permissions  ", "  Size: " + ratingPiiSharePermission!!.size)
             for (i in 0 ..((ratingPiiSharePermission!!.size)-1)){
               //  Log.e("rating all Permissions $i", " gfjhdg: "+100f/ratingPiiSharePermission!!.size.toFloat()+"  Size: "+ratingPiiSharePermission!!.size+ " name:  "+ratingPiiSharePermission!!.get(i).name+"   color="+color[i])
                 var  sliceValue = 100f/ratingPiiSharePermission!!.size
                 if(colors16.size>i)
               pieData!!.add(
                   SliceValue(
                       sliceValue.toFloat(),
                       colors16.get(i)
                   ).setLabel("" + ratingPiiSharePermission!![i].name)
               )
            else{
                     pieData!!.add(
                         SliceValue(
                             sliceValue.toFloat(),
                             Color.MAGENTA
                         ).setLabel("" + ratingPiiSharePermission!![i].name)
                     ) //.setLabel(ratingAllPermission!!.get(i).name+""))
                 }
              }
                 }

        pieChartData = PieChartData(pieData)
        pieChartData!!.setHasLabels(true).setValueLabelTextSize(8);

pieChart!!.setHasTransientState(true)
    }


    private fun setFirstTotalAppsPieChartData(totalAppsResult: Result) {
        pieData = java.util.ArrayList()
        pieData!!.clear()
           var dff = totalAppsResult.totalApps
          pieData!!.add(
            SliceValue(totalAppsResult.percentage.toFloat(), Color.RED).setLabel(
                totalAppsResult.piiSharedApps.toString()
            )
        )
        pieData!!.add(SliceValue(dff.toFloat(), Color.GREEN).setLabel(dff.toString()))

        pieChartData = PieChartData(pieData)
        pieChartData!!.setHasLabels(true).setValueLabelTextSize(18);

        pieChartData!!.centerText1 = totalAppsResult.totalApps.toString()
        pieChartData!!.setHasCenterCircle(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reports, container, false)
        views = viewBinding!!.root
setColorDataInColorLst()
        var session_Id = CSPreferences.readString(activity!!, "session_id")
         pieChart = views!!.findViewById<PieChart>(R.id.pieChart)
      //  val loginViewmodel = ViewModelProviders.of(this).get(ListVm::class.java)

        if (Global_utility.isNetworkConnected(activity!!)) {
            //  appdata(CSPreferences.readString(activity!!,"session_id")!!,"[{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@8c88663\",\"name\":\"YouTube\",\"package_name\":\"com.google.android.youtube\",\"version_name\":\"13.15.61\",\"targetversion\":\"28\",\"firstupdate\":\"04/11/2019 04:06:53\",\"lastupdate\":\"04/11/2019 04:06:53\"}]",activity!!,this)
            apifirstSpeedVwTotalAppsPiiChart(session_Id, activity!!, this, views!!)
            apiSecondPiiTotalAppsData(session_Id, activity!!, this, views!!)
            apiThirdDataPiiChart(session_Id, activity!!, this, views!!)
            apiFirstOriginalAppByCategory(session_Id, activity!!, this, views!!)
        apiSecondOriginalAppsTimeUsed(session_Id, activity!!, this, views!!)
            apiThirdOriginalPersonalPotentialData(session_Id, activity!!, this, views!!)

        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }


      //  setupBarChartData(views!!)
        setupLineChartData(views!!)
        setUpPieChartData(views!!)
        setUpCustomCalender(views!!)
        //setUpPieChartData2(views!!)

        return views
    }

    private fun apiSecondOriginalAppsTimeUsed(
        sessionId: String?,
        activity: FragmentActivity,
        reportsFragment: ReportsFragment,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)
        listVm.reportOfTimeUsedByAppApi(sessionId, activity)!!.observe(this, Observer {
            if (it.status!!.equals(200)) {
                reportsFragment.secondOriginalAppsTimeUsed(it, views)
            } else if (it.status!!.equals(403)) {
                Global_utility.showtost(activity!!, it.message.toString())
                CSPreferences.putString(activity!!, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity!!.startActivity(intent)
                (activity!! as Activity).overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                (activity!! as Activity).finish()
            } else {
                it.message!!.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
    }

    private fun apiThirdOriginalPersonalPotentialData(
        sessionId: String?,
        activity: FragmentActivity,
        reportsFragment: ReportsFragment,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)
         listVm.reportOfPersonalPotentialDataApi(sessionId, activity)!!.observe(this, Observer {
             if (it.status!!.equals(200)) {
                 reportsFragment.thirdOriginalPersonalPotentialData(it.personalDataType, views)
             } else if (it.status!!.equals(403)) {
                 Global_utility.showtost(activity!!, it.message.toString())
                 CSPreferences.putString(activity!!, "session_id", "")
                 val intent = Intent(context, Saplash::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                 activity!!.startActivity(intent)
                 (activity!! as Activity).overridePendingTransition(
                     android.R.anim.fade_in,
                     android.R.anim.fade_out
                 )
                 (activity!! as Activity).finish()
             } else {
                 it.message!!.let { it1 -> Global_utility.showtost(activity!!, it1) }
             }
         })
    }

    private fun setColorDataInColorLst() {
        colors16 = intArrayOf(
            Color.parseColor("#bfaeba"),
            Color.parseColor("#e7b63c"),
            Color.parseColor("#0377a8"),
            Color.parseColor("#b4fadc"),
            Color.parseColor("#eeff00"),
            Color.parseColor("#b67c7c"),
            Color.parseColor("#ddc1bd"),
            Color.parseColor("#4ed8a9"),
            Color.parseColor("#90f3d9"),
            Color.parseColor("#c7ebe6"),
            Color.parseColor("#e7cbb9"),
            Color.parseColor("#1a4201"),
            Color.parseColor("#bc8862"),

            Color.parseColor("#ff0000"),
            Color.parseColor("#cc6600"),
            Color.parseColor("#cc66ff"),
            Color.parseColor("#660033"),
            Color.parseColor("#d2fe1a"),
            Color.parseColor("#2b83b2"),
            Color.parseColor("#2bb29e"),
            Color.parseColor("#1ad2fe"),
            Color.parseColor("#01cdfe"),
            Color.parseColor("#2bb25a"),
            Color.parseColor("#b22b83"),
            Color.parseColor("#ff3ebc"),
            Color.parseColor("#ff71ce"),
            Color.parseColor("#6497b1"),
            Color.parseColor("#005b96"),
            Color.parseColor("#03396c"),
            Color.parseColor("#5cb85c"),
            Color.parseColor("#24b9dd"),
            Color.parseColor("#2bb7ca"),
            Color.parseColor("#799367"),
            Color.parseColor("#816793"),
            Color.parseColor("#4b2665"),
            Color.parseColor("#fdd0e4"),
            Color.parseColor("#2fa207"),
            Color.parseColor("#44e80a"),
            Color.parseColor("#e066ff"),
            Color.parseColor("#00ffff"),
            Color.parseColor("#7fffd4"),
            Color.parseColor("#ff7f50"),
            Color.parseColor("#8470ff"),
            Color.parseColor("#a8c3b3"),
            Color.parseColor("#f6d26e"),
            Color.parseColor("#8fb26c"),
            Color.parseColor("#990000"),
            Color.parseColor("#ffcc00"),
            Color.parseColor("#ff33cc"),
            Color.parseColor("#ff9966"),
            Color.parseColor("#0066ff"),
            Color.parseColor("#669900"),
            Color.parseColor("#66ffcc"),
            Color.parseColor("#ffff99")
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Reports")
         fl_fragReports.setOnClickListener(this)
    }
    private fun apiSecondPiiTotalAppsData(
        sessionId: String?,
        activity: FragmentActivity,
        permissionFrag: PermissionsReportInterface,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)
        listVm.getPiiTotalAppsData(sessionId, activity)!!.observe(this, Observer {
            if (it.getStatus().equals(200)) {
                permissionFrag.secondBarChartpiitotalAppsData(it.result!!, views)
            } else if (it.getStatus().equals(403)) {
                Global_utility.showtost(activity!!, it.message.toString())
                CSPreferences.putString(activity!!, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity!!.startActivity(intent)
                (activity!! as Activity).overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                (activity!! as Activity).finish()
            } else {
                it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
    }

    private fun apifirstSpeedVwTotalAppsPiiChart(
        sessionId: String?,
        activity: FragmentActivity,
        permissionFrag: PermissionsReportInterface,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)
        listVm.getTotalAppsChartData(sessionId, activity)!!.observe(this, Observer {
            if (it.getStatus().equals(200)) {
                permissionFrag.firstSpeedVw_totalAppsPiiShared(it.result!!, views)
            } else if (it.getStatus().equals(403)) {
                Global_utility.showtost(activity!!, it.message.toString())
                CSPreferences.putString(activity!!, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity!!.startActivity(intent)
                (activity!! as Activity).overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                (activity!! as Activity).finish()
            } else {
                it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
    }

    private fun apiThirdDataPiiChart(
        sessionId: String?, activity: FragmentActivity, permissionFrag: PermissionsReportInterface,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)
        listVm.getPiiData(sessionId, activity)!!.observe(this, Observer {
            if (it.getStatus().equals(200)) {
                permissionFrag.thirdPiiSharePermission(it.rating!!, views)

            } else if (it.getStatus().equals(403)) {
                Global_utility.showtost(activity!!, it.message.toString())
                CSPreferences.putString(activity!!, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity!!.startActivity(intent)
                (activity!! as Activity).overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                (activity!! as Activity).finish()
            } else {
                it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })

    }

    private fun apiFirstOriginalAppByCategory(
        sessionId: String?, activity: FragmentActivity, permissionFrag: PermissionsReportInterface,
        views: View
    ) {
        val listVm = ViewModelProviders.of(this).get(ListVm::class.java)

        listVm.reportOfAppCategoryApi(sessionId, activity)!!.observe(this, Observer {
            if (it.status!!.equals(200)) {
                permissionFrag.firstOriginalAppByCategory(it.appsCategoryCount, views)
            } else if (it.status!!.equals(403)) {
                Global_utility.showtost(activity!!, it.message.toString())
                CSPreferences.putString(activity!!, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity!!.startActivity(intent)
                (activity!! as Activity).overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                (activity!! as Activity).finish()
            } else {
                it.message!!.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
    }


    private fun setUpCustomCalender(views: View) {
//Initialize CustomCalendarView from layout
        //Initialize CustomCalendarView from layout
        var calendarView = views.findViewById(R.id.calendar_view) as CustomCalendarView
//Initialize calendar with date
        //Initialize calendar with date
        val currentCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        //Show Monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
//Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);
//call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        calendarView.setCalendarListener(object : CalendarListener {
            override fun onDateSelected(p0: Date?) {
                var df = SimpleDateFormat("dd-MM-yyyy")
            }

            override fun onMonthChanged(p0: Date?) {
                var df = SimpleDateFormat("dd-MM-yyyy")
            }
        })

        //adding calendar day decorators
        //adding calendar day decorators
        val decorators: MutableList<DayDecorator> = ArrayList()
        decorators.add(DisabledColorDecorator(activity!!))
        calendarView.decorators = decorators
        calendarView.refreshCalendar(currentCalendar)
    }

    private fun setupLineChartData(views: View) {
        val yVals = ArrayList<Entry>()
        yVals.add(Entry(0f, 30f, "0"))
        yVals.add(Entry(1f, 2f, "1"))
        yVals.add(Entry(2f, 4f, "2"))
        yVals.add(Entry(3f, 6f, "3"))
        yVals.add(Entry(4f, 8f, "4"))
        yVals.add(Entry(5f, 10f, "5"))
        yVals.add(Entry(6f, 22f, "6"))
        yVals.add(Entry(7f, 12.5f, "7"))
        yVals.add(Entry(8f, 22f, "8"))
        yVals.add(Entry(9f, 32f, "9"))
        yVals.add(Entry(10f, 54f, "10"))
        yVals.add(Entry(11f, 28f, "11"))

        val set1: LineDataSet
        set1 = LineDataSet(yVals, "DataSet 1")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);
        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        var lineChart = this.views!!.findViewById<LineChart>(R.id.lineChart)
        lineChart.setData(data)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        lineChart.xAxis.labelCount = 11
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun setUpPieChartData(views: View) {
        val yVals = ArrayList<PieEntry>()
        if(ratingPiiSharePermission!=null){
            for (i in 0 until ratingPiiSharePermission!!.size){
                println(
                    "rating all Permissions " + " gfjhdg: " + 100f / ratingPiiSharePermission!!.size.toFloat() + "  Size: " + ratingPiiSharePermission!!.size + " name:  " + ratingPiiSharePermission!!.get(
                        i
                    ).name
                )

                var  sliceValue = 100/ratingPiiSharePermission!!.size
               // pieData!!.add(SliceValue(sliceValue.toFloat(), Color.BLUE).setLabel("")) //.setLabel(ratingAllPermission!!.get(i).name+""))
               // yVals.add(PieEntry(sliceValue.toFloat()))
            }
        }
      yVals.add(PieEntry(30f))
        yVals.add(PieEntry(2f))
        yVals.add(PieEntry(4f))
        yVals.add(PieEntry(22f))
        yVals.add(PieEntry(12.5f))
       // yVals.add(PieEntry(sliceValue.toFloat()))
        val dataSet = PieDataSet(yVals, "")
        dataSet.valueTextSize = 0f
        val colors = java.util.ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.BLUE)
        colors.add(Color.GRAY)
        colors.add(Color.GREEN)
        colors.add(Color.BLACK)
//        colors.add(Color.MAGENTA)
//        colors.add(Color.LTGRAY)
//        colors.add(Color.CYAN)
//        colors.add(Color.YELLOW)
//        colors.add(Color.DKGRAY)

        dataSet.setColors(colors)
        val data = PieData(dataSet)
        var pieChart = this.views!!.findViewById<PieChart>(R.id.pieChart)
        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.isDrawHoleEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
    }

    private fun setSecondBarChartData(views: View) {
        // create BarEntry for Bar Group

        val bargroup = ArrayList<BarEntry>()
        if(!piiTotalAppsResult.isNullOrEmpty()){
            for(k in 0 until piiTotalAppsResult!!.size){

              //   Log.e("title",""+piiTotalAppsResult!!.get(k).loc.toString()!!)
                labels.add(piiTotalAppsResult!!.get(k).loc.toString()!!)
                bargroup.add(
                    BarEntry(
                        piiTotalAppsResult!!.get(k).x.toFloat(), piiTotalAppsResult!!.get(
                            k
                        ).y.toFloat(), piiTotalAppsResult!!.get(k).loc.toString()
                    )
                )
            }
        }
        /*bargroup.add(BarEntry(0f, 30f, "0"))
        bargroup.add(BarEntry(1f, 2f, "1"))
        bargroup.add(BarEntry(2f, 4f, "2"))
        bargroup.add(BarEntry(3f, 6f, "3"))
        bargroup.add(BarEntry(4f, 8f, "4"))
        bargroup.add(BarEntry(5f, 10f, "5"))
        bargroup.add(BarEntry(6f, 22f, "6"))
        bargroup.add(BarEntry(7f, 12.5f, "7"))
        bargroup.add(BarEntry(8f, 22f, "8"))
        bargroup.add(BarEntry(9f, 32f, "9"))
        bargroup.add(BarEntry(10f, 54f, "10"))
        bargroup.add(BarEntry(11f, 28f, "11"))
*/
        // creating dataset for Bar Group
        val barDataSet = BarDataSet(bargroup, "")
        barDataSet.setColors(ColorTemplate.createColors(colors16))
        val data = BarData(barDataSet)
        if (data != null) {
            var barChart = views.findViewById<BarChart>(R.id.barChart)
            barChart.setData(data)

         /*   barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.labelCount = piiTotalAppsResult!!.size
            barChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
            barChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
            barChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
            barChart.description.isEnabled = false
            barChart.animateY(1000)
            barChart.legend.isEnabled = false
            barChart.setPinchZoom(true)
            barChart.data.setDrawValues(true)*/
            barChart.setPinchZoom(false)
            barChart.setData(data)

            // set the data and list of lables into chart
            barChart.zoom(4f, 4f, 5f, 5f)
      var desc =Description()
            desc.text="PII Shared"
            barChart.setDescription(desc) // set the description
            barChart.animateY(500)
        }

    }

    private class DisabledColorDecorator(var activity: FragmentActivity) : DayDecorator {

        override fun decorate(dayView: DayView) {
            if (CalendarUtils.isPastDay(dayView.getDate())) {
                val color = Color.parseColor("#a9afb9")
           //     Global_utility.showtost(activity, "color.........")
                //dayView.setBackgroundColor(color)
                dayView.setBackground(activity.resources.getDrawable(R.drawable.logo_p))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // if(!ratingAllPermission.isNullOrEmpty()){
        // println("rating all Permissions "+ " gfjhdg: "+100f/ratingAllPermission!!.size.toFloat()+"  Size: "+ratingAllPermission!!.size+ " name:  ")

//            setUpPieChartData2()
//            viewBinding!!.chart.pieChartData = pieChartData
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.fl_fragReports -> {
            }
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragReports, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

        }
    }
    //}
}
