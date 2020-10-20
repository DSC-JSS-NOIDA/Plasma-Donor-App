package project.dscjss.plasmadonor.Fragment.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.models.ProfileDetail
import project.dscjss.plasmadonor.Util.isPhoneNumberValid
import project.dscjss.plasmadonor.Util.isValidEmail
import project.dscjss.plasmadonor.interfaces.EditProfileCallBack
class EditProfileViewModel : ViewModel() {
    var loader = MutableLiveData<Boolean>()
    var stringResources = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    private var repository: EditProfileRepository = EditProfileRepository()
    fun saveUserDetail(
        firebaseFirestore: FirebaseFirestore,
        userId: String?,
        detail: ProfileDetail
    ) {
        if (detail.firstName.isEmpty()) { stringResources.postValue(R.string.err_first_name) }
        else if(detail.lastName.isEmpty()) { stringResources.postValue(R.string.err_last_name) }
        else if (detail.gender.isEmpty()) { stringResources.postValue(R.string.err_gender) }
        else if (detail.age.isEmpty()) { stringResources.postValue(R.string.err_age) }
        else if (detail.bloodGroup.isEmpty()) { stringResources.postValue(R.string.err_blood_group) }
        else if (detail.weight.isEmpty()) { stringResources.postValue(R.string.err_weight) }
        else if (!isValidEmail(detail.emailId)) { stringResources.postValue(R.string.err_email) }
        else if (!isPhoneNumberValid(detail.phone)) { stringResources.postValue(R.string.err_phone) }
        else if (detail.location.isEmpty()) { stringResources.postValue(R.string.err_location) } else {
            loader.postValue(true)
            repository.updateFirebaseUserDB(firebaseFirestore, userId, detail, object : EditProfileCallBack {
                override fun onSuccess() {
                    loader.postValue(false)
                    stringResources.postValue(R.string.msg_profile_update_success)
                    success.postValue(true)
                }
                override fun onError(strResource: Int) {
                    loader.postValue(false)
                    stringResources.postValue(strResource)
                }
            })
         }
    }
}
