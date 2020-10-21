package project.dscjss.plasmadonor.interfaces

import androidx.annotation.StringRes

interface EditProfileCallBack {
    fun onSuccess()
    fun onError(@StringRes strResource: Int)
}
