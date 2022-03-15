package com.shahad.app.my_school.ui.add.post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentCreatePostBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_create_post

    override val viewModel: CreatePostViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessJoined.observeEvent(this@CreatePostFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
            clickBackEvent.observeEvent(this@CreatePostFragment){
                findNavController().navigateUp()
            }
            clickUploadImageEvent.observeEvent(this@CreatePostFragment) {
                chooseImageFromGallery()
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
                    viewModel.file.postValue(File(it.path))
                }
            }else{
                Toast.makeText(requireContext(),"fail to upload Image",Toast.LENGTH_SHORT).show()
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