package project.dscjss.plasmadonor.Fragment.data

import com.google.firebase.firestore.PropertyName

data class Patient(
    @field:PropertyName("Name")
    val name: String = "",
    @field:PropertyName("Age")
    val age: String = "",
    @field:PropertyName("BloodGroup")
    val bloodGroup: String = "",
    @field:PropertyName("Hospital")
    val hospital: String = "",
    @field:PropertyName("Gender")
    val gender: String = "",
    @field:PropertyName("Mobile")
    val mobile: String = "",
    @field:PropertyName("Email")
    val email: String = "",
    @field:PropertyName("Diabetes")
    val diabetes: String = "",
    @field:PropertyName("Location")
    val location: String = "",
    @field:PropertyName("LiverProblem")
    val liverProblem: String = "",
    @field:PropertyName("BpProblem")
    val bpProblem: String = ""
)
