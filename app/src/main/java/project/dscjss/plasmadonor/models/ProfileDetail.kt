package project.dscjss.plasmadonor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
public  data class ProfileDetail(
    var firstName:String = "",
    var lastName:String = "",
    var gender:String =  "",
    var age:String = "",
    var bloodGroup:String = "",
    var weight:String = "",
    var emailId:String = "",
    var phone:String = "",
    var location:String = ""
    ):Parcelable