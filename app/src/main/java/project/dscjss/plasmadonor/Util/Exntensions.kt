package project.dscjss.plasmadonor.Util

/**
 * Created by Harsh Rajgor on 03/10/20.
 */

fun isPhoneNumberValid(phone: String) : Boolean{
    if (phone.contains("+") && phone.indexOf("+") != 0) return false
    if (phone.startsWith("+") && phone.lastIndexOf("+") == 0) {
        return phone.length == 13
    }
    return phone.length == 10
}