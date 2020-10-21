package project.dscjss.plasmadonor.Util

import com.google.firebase.firestore.FirebaseFirestore
import project.dscjss.plasmadonor.models.FaqModel

object DataFaq {

    fun getData(firebaseFireStore: FirebaseFirestore): List<FaqModel> {
        val faqList: MutableList<FaqModel> = mutableListOf<FaqModel>()
        firebaseFireStore.collection("faq")
            .get().addOnSuccessListener { doc ->
                val it = doc.documents
                faqList.clear()
                for (i in it) {

                    val ques = i["question"].toString()
                    val sol = i["solution"].toString()

                    faqList.add(
                        FaqModel(ques, sol)
                    )
                }
            }
        return faqList
    }
}
