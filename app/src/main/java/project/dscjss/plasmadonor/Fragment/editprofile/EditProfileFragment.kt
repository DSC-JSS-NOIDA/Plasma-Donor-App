package project.dscjss.plasmadonor.Fragment.editprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import project.dscjss.plasmadonor.Adapter.SpinnerTextAdapter
import project.dscjss.plasmadonor.interfaces.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities.showShortToast
import project.dscjss.plasmadonor.databinding.EditProfileFragmentBinding
import project.dscjss.plasmadonor.interfaces.OnBackPressInterface
import project.dscjss.plasmadonor.models.ProfileDetail
class EditProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        fun newInstance(detail: ProfileDetail?): EditProfileFragment {
            val frag = EditProfileFragment()
            if (detail != null) {
                frag.arguments = Bundle().apply {
                    putParcelable(PROFILE_DETAIL, detail)
                }
            }
            return frag
        }
        const val PROFILE_DETAIL = "PROFILE_DETAIL"
    }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var onBackPressInterface: OnBackPressInterface
    private lateinit var viewModel: EditProfileViewModel
    private lateinit var mContext: Context
    private lateinit var binding: EditProfileFragmentBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    var bloodGroup: String = ""
    var gender: String = ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_profile_fragment, container, false)
        binding = EditProfileFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    private fun handleLoadStates(isLoading: Boolean) {
        if (isLoading) {
            btSave.isClickable = false
            progress_circular.visibility = View.VISIBLE
            etFirstName.isEnabled = false
            etLastName.isEnabled = false
            etGender.isEnabled = false
            etAge.isEnabled = false
            etBloodGrp.isEnabled = false
            etWeight.isEnabled = false
            etEmail.isEnabled = false
            etPhone.isEnabled = false
            etLocation.isEnabled = false
        } else {
            btSave.isClickable = true
            progress_circular.visibility = View.GONE
            etFirstName.isEnabled = true
            etLastName.isEnabled = true
            etGender.isEnabled = true
            etAge.isEnabled = true
            etBloodGrp.isEnabled = true
            etWeight.isEnabled = true
            etEmail.isEnabled = true
            etPhone.isEnabled = true
            etLocation.isEnabled = true
        }
    }

    private fun init() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        fragmentChangeInterface = mContext as FragmentChangeInterface
        onBackPressInterface = mContext as OnBackPressInterface

        arguments?.getParcelable<ProfileDetail>(PROFILE_DETAIL)?.also {
            gender = it.gender
            bloodGroup = it.bloodGroup
            binding.user = it
        }
        etGender.apply {
            adapter = SpinnerTextAdapter(
                this@EditProfileFragment.mContext,
                R.layout.spinner_text_layout,
                resources.getStringArray(R.array.gender))
            setSelection(
                getPreviousSelectedPos(this@EditProfileFragment.gender, resources.getStringArray(R.array.gender)), true)
            onItemSelectedListener = this@EditProfileFragment
        }

        etBloodGrp.apply {
            adapter = SpinnerTextAdapter(
                this@EditProfileFragment.mContext,
                R.layout.spinner_text_layout,
                resources.getStringArray(R.array.blood_grp))
            setSelection(
                getPreviousSelectedPos(
                    this@EditProfileFragment.bloodGroup, resources.getStringArray(R.array.blood_grp)), true)
            onItemSelectedListener = this@EditProfileFragment
        }
        btSave.setOnClickListener {
            viewModel.saveUserDetail(firebaseFirestore, firebaseAuth.uid, createProfileDetail())
        }
        etLocation.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btSave.callOnClick()
                true
            } else false
        }
        viewModel.loader.observe(viewLifecycleOwner, { handleLoadStates(it) })
        viewModel.stringResources.observe(viewLifecycleOwner, { mContext.showShortToast(it) })
        viewModel.success.observe(viewLifecycleOwner, { if (it) {
            onBackPressInterface.navigateBack()
        } })
    }
    private fun EditText.getString(): String { return this.text.toString() }

    private fun createProfileDetail(): ProfileDetail {
        return ProfileDetail().apply {
            firstName = etFirstName.getString()
            lastName = etLastName.getString()
            gender = this@EditProfileFragment.gender
            age = etAge.getString()
            bloodGroup = this@EditProfileFragment.bloodGroup
            weight = etWeight.getString()
            emailId = etEmail.getString()
            phone = etPhone.getString()
            location = etLocation.getString() }
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        when (adapterView?.id) {
            R.id.etGender -> gender = adapterView.getItemAtPosition(position).toString()
            R.id.etBloodGrp -> bloodGroup = adapterView.getItemAtPosition(position).toString()
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) = Unit

    private fun getPreviousSelectedPos(previousSelection: String, stringArray: Array<String>): Int {
        var position = 0
        if (previousSelection.isNotEmpty()) {
            for ((index, value) in stringArray.withIndex()) {
                if (previousSelection.equals(value, ignoreCase = true)) {
                    position = index
                    break
                }
            }
        }
        return position
    }
}
