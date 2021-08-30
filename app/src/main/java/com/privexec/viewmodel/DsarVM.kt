package com.privexec.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privexec.Api_Call.WebAPicall
import com.privexec.pojoclass.AllDsarQuestionPojo
import com.privexec.pojoclass.EmailTemplatePojo
import com.privexec.pojoclass.piiTotalApps.PiiTotalAppsResponse

class DsarVM  : ViewModel(){
    private val mService  =  WebAPicall()


    fun getDsarQuestApi(sessionId: String?, activity: FragmentActivity): MutableLiveData<AllDsarQuestionPojo>? {

        return mService.getDsarQuestApi( activity,""+sessionId )
    }

    fun emailTemplate(sessionId: String?,jsonQuesAnsArray: String?,appid: String?, activity: FragmentActivity): MutableLiveData<EmailTemplatePojo>? {

        return mService.getEmailTemplateApi( activity,""+sessionId ,""+jsonQuesAnsArray,""+appid)
    }

}