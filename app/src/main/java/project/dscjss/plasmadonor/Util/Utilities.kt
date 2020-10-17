package project.dscjss.plasmadonor.Util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object Utilities {

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showLongSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    //extension function
    fun Context.showShortToast(@StringRes stringResource:Int){
        Toast.makeText(this, this.getString(stringResource),Toast.LENGTH_SHORT).show()
    }

    fun Context.showLongToast(@StringRes stringResource: Int){
        Toast.makeText(this, this.getString(stringResource),Toast.LENGTH_LONG).show()
    }


}