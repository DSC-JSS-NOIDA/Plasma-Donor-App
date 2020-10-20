package project.dscjss.plasmadonor.Fragment.editprofile

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.interfaces.EditProfileCallBack
import project.dscjss.plasmadonor.models.ProfileDetail
import kotlin.collections.HashMap

class EditProfileRepository {
    fun updateFirebaseUserDB(
        firebaseFireStore: FirebaseFirestore,
        userId: String?,
        detail: ProfileDetail,
        editProfileCallBack: EditProfileCallBack
    ) {
        if (userId == null) {
            editProfileCallBack.onError(R.string.err_user_detail_firebase__save_failure)
        } else {
            firebaseFireStore.collection("users").document(userId)
                .set(createUserInputMaps(userId, detail), SetOptions.merge())
                .addOnSuccessListener {
                    editProfileCallBack.onSuccess()
               }
              .addOnFailureListener {
                    Log.e(this.javaClass.toString(), it.stackTraceToString())
                    editProfileCallBack.onError(R.string.err_user_detail_firebase__save_failure)
                }
        }
    }

    fun createUserInputMaps(userId: String, detail: ProfileDetail): HashMap<String, String> {
         val user = HashMap<String, String>()
            user["uid"] = userId
            user["FirstName"] = detail.firstName
            user["LastName"] = detail.lastName
            user["Gender"] = detail.gender
            user["Age"] = detail.age
            user["BloodGroup"] = detail.bloodGroup
            user["Weight"] = detail.weight
            user["Email"] = detail.emailId
            user["Phone"] = detail.phone
           user["Location"] = detail.location
           return user
    }
}
