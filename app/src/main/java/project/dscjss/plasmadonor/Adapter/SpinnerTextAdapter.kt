package project.dscjss.plasmadonor.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import project.dscjss.plasmadonor.R

class SpinnerTextAdapter constructor(
    var mContext: Context,
    layout: Int,
    var spinnerType: Array<String>
) : ArrayAdapter<String>(mContext, layout, spinnerType) {

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val dropdownView = super.getDropDownView(position, convertView, parent) as TextView
        if (position == 0) {
            dropdownView.setTextColor(ContextCompat.getColor(mContext, R.color.colorHint))
        } else {
            dropdownView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
        }
        return dropdownView
    }
}
