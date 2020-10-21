package project.dscjss.plasmadonor.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import project.dscjss.plasmadonor.models.FaqModel
import project.dscjss.plasmadonor.Util.DataFaq

class FaqViewModel : ViewModel() {
    fun getData(firebaseFireStore: FirebaseFirestore): List<FaqModel> = DataFaq.getData(firebaseFireStore)
}
