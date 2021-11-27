package com.digitalgenius.bookmytable.ui.Home_A.Profile_F

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.digitalgenius.bookmytable.R
import com.digitalgenius.bookmytable.api.models.responses.UploadProfilePicResponse
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding
import com.digitalgenius.bookmytable.ui.ChoosePicture_A.ChoosePictureActivity
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantViewModel
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity
import com.digitalgenius.bookmytable.ui.Splash_A.SplashActivity
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Constants.Companion.BASE_URL
import com.digitalgenius.bookmytable.utils.Constants.Companion.isSelectedProfilePic
import com.digitalgenius.bookmytable.utils.Constants.Companion.profilePic
import com.digitalgenius.bookmytable.utils.Constants.Companion.userData
import com.digitalgenius.bookmytable.utils.Functions
import com.digitalgenius.bookmytable.utils.Functions.hide_progress_dialog
import com.digitalgenius.bookmytable.utils.Functions.show_long_toast
import com.digitalgenius.bookmytable.utils.Resource
import com.digitalgenius.bookmytable.utils.Resource.Loading
import com.digitalgenius.bookmytable.utils.SharedPrefManager
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import com.digitalgenius.bookmytable.ui.ChoosePicture_A.ChoosePictureActivity.fileUri

import okhttp3.RequestBody


class ProfileFragment : Fragment() {
    var binding: FragmentProfileBinding? = null
    var viewModel: RestaurantViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity?)!!.restaurantViewModel
        setListener()

    }

    override fun onResume() {
        super.onResume()
        setData()
        if (isSelectedProfilePic) {
            isSelectedProfilePic = false
            Functions.show_progress_dialog(requireContext(), "Uploading Picture")


            viewModel?.uploadPic(
                Functions.createPartFromString(userData?.userId!!),
                Functions.createPartFromString("profile_pic"),
                Functions.prepareFilePart("picture_file", profilePic!!)
            )
        }

    }

    var sharedPrefManager: SharedPrefManager? = null

    fun setData() {
        binding!!.tvUsername.text = userData!!.userName
        binding!!.tvUserPhoneNumber.text = userData!!.userPhoneNumber
        binding!!.tvUserLocation.text = userData!!.userLocation
        binding!!.tvUserEmail.text = userData!!.userEmail
        if (userData!!.userProfilePic != "") {
            Glide.with(requireContext())
                .load(userData!!.userProfilePic)
                .into(binding!!.ivProfilePic)

            Log.d("Fenil old Link",userData!!.userProfilePic)
        }
        sharedPrefManager = SharedPrefManager.getInstance(requireActivity().applicationContext)
        if (sharedPrefManager?.getStringData("Mode") == "Dark") {
            binding!!.toggleMode.isChecked = true
            binding!!.tvUiMode.text = "Enable Light Mode"
        } else {
            binding!!.toggleMode.isChecked = false
            binding!!.tvUiMode.text = "Enable Dark Mode"
        }
    }

    private fun setListener() {

        binding!!.tvEditUser.setOnClickListener { v: View? ->
            NavHostFragment.findNavController(this@ProfileFragment).navigate(
                R.id.action_navigation_profile_to_userUpdateProfileFragment
            )
        }
        binding!!.termLayout.setOnClickListener { v: View? ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    requireContext().getString(R.string.term_link)
                )
            )
            startActivity(browserIntent)
        }
        binding!!.policyLayout.setOnClickListener { v: View? ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    requireContext().getString(R.string.policy_link)
                )
            )
            startActivity(browserIntent)
        }
        binding!!.logoutLayout.setOnClickListener { v: View? -> logout_user() }
        binding!!.restaurantLayout.setOnClickListener { v: View? ->
            val restaurantIntent = Intent(requireContext(), MyRestaurantActivity::class.java)
            startActivity(restaurantIntent)
        }
        binding!!.ivProfilePic.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    ChoosePictureActivity::class.java
                )
            )
        }
        binding!!.toggleMode.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            sharedPrefManager = SharedPrefManager.getInstance(
                requireActivity().applicationContext
            )
            if (isChecked) {
                binding!!.toggleMode.isChecked = true
                binding!!.tvUiMode.text = "Enable Light Mode"
                sharedPrefManager?.setStringData("Mode", "Dark")
            } else {
                binding!!.toggleMode.isChecked = false
                binding!!.tvUiMode.text = "Enable Dark Mode"
                sharedPrefManager?.setStringData("Mode", "Light")
            }
            toggleMode()
        }

        viewModel?.uploadProfilePicResponse?.observe(
            viewLifecycleOwner,
            { response: Resource<UploadProfilePicResponse> ->
                if (response is Resource.Success<*>) {
                    hide_progress_dialog()

                        show_long_toast(requireContext(), "Profile Pic Updated")
                        val profileLink =
                            BASE_URL + "static/profile_pic/" + response.data!!.pictureFilename
                        Log.d("Fenil updated Link",profileLink)
                        SharedPrefManager.getInstance(requireActivity().applicationContext)
                            .setStringData("user_profile_pic", profileLink)
                        userData = sharedPrefManager!!.userData
                        Glide.with(requireContext())
                            .load(userData!!.userProfilePic)
                            .into(binding!!.ivProfilePic)
                    viewModel?.uploadProfilePicResponse?.postValue(Resource.Loading())
                    isSelectedProfilePic = false
                } else if (response is Resource.Error<*>) {
                    hide_progress_dialog()
                    show_long_toast(
                        requireContext(),
                        response.message!!
                    )
                } else if (response is Loading<*>) {
                }
            })
    }

    private fun toggleMode() {
        sharedPrefManager = SharedPrefManager.getInstance(requireActivity().applicationContext)
        if (sharedPrefManager?.getStringData("Mode") == "Dark") {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
        } else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
        }
    }

    private fun logout_user() {
        val sharedPrefManager = SharedPrefManager.getInstance(requireContext().applicationContext)
        sharedPrefManager.setStringData("Login", "False")
        startActivity(Intent(context, SplashActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    //    var file: File? = null



}