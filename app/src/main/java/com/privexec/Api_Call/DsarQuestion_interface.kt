package com.privexec.Api_Call

import com.privexec.pojoclass.AllDsarQuestionPojo
import com.privexec.pojoclass.All_ratingPojo

interface DsarQuestion_interface {

fun getQuestions(list:List<AllDsarQuestionPojo.AllDsarQuestionModel>)

}