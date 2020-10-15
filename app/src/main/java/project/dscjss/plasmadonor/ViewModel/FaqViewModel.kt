package project.dscjss.plasmadonor.ViewModel

import androidx.lifecycle.ViewModel
import project.dscjss.plasmadonor.Model.FaqModel
import project.dscjss.plasmadonor.Util.DataFaq

class FaqViewModel : ViewModel() {
    fun getDatas(): List<FaqModel> = DataFaq.getData()
}
