package project.dscjss.plasmadonor.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import project.dscjss.plasmadonor.Model.FaqModel
import project.dscjss.plasmadonor.Util.DataFaq

class FaqViewModel : ViewModel() {
    fun getDatas(): List<FaqModel> = DataFaq.getData()
}