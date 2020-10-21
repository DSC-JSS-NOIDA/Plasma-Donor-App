package project.dscjss.plasmadonor.Util

import java.util.regex.Pattern

/**
 * Created by Harsh Rajgor on 03/10/20.
 */

fun isPhoneNumberValid(phone: String): Boolean {
    if (phone.contains("+") && phone.indexOf("+") != 0) return false
    if (phone.startsWith("+") && phone.lastIndexOf("+") == 0) {
        return phone.length == 13
    }
    return phone.length == 10
}

fun isValidEmail(emailId: String): Boolean {
    return if (emailId.isEmpty()) {
        false
    } else {
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$"

        val pat = Pattern.compile(emailRegex)
        pat.matcher(emailId).matches()
    }
}
