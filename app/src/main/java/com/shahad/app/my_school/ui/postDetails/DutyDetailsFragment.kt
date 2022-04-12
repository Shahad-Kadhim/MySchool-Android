package com.shahad.app.my_school.ui.postDetails

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDutyDetailsBinding
import com.shahad.app.my_school.ui.add.post.RealPathUtil
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class DutyDetailsFragment: BaseFragment<FragmentDutyDetailsBinding>() {

    override fun getLayoutId() = R.layout.fragment_duty_details
    override val viewModel: DutyDetailsViewModel by viewModels()

    @Inject
    lateinit var realPathUtil: RealPathUtil

    override fun onStart() {
        super.onStart()
        viewDataBinding.comments.adapter =
            CommentAdapterRecycler(
                viewModel.postDetails.value?.toData()?.data?.comments ?: emptyList(),
                viewModel
            )
        observe()
    }

    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@DutyDetailsFragment){
                findNavController().navigateUp()
            }
            clickUploadImageEvent.observeEvent(this@DutyDetailsFragment) {
                chooseImageFromGallery()
            }
            solution.observe(this@DutyDetailsFragment){
                Log.i("TAG URLLLLL", it.toString())
            }
        }
    }
    private fun chooseImageFromGallery() {
        Intent(Intent.ACTION_PICK).apply {
            type = "*/*"
            resultLauncher.launch(this)
        }
    }

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    viewModel.imagePost.postValue(getBitmapFromURI(it))
                    realPathUtil.getRealPath(this.requireContext(),it)?.let{ path ->
                        viewModel.file.postValue(File(path))
                    }
                }
            }else{
                Toast.makeText(requireContext(),"fail to upload Image", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getBitmapFromURI(uri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media
                .getBitmap(requireActivity().contentResolver,uri)
        }

}