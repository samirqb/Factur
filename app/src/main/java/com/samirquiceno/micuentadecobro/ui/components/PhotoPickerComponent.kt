package com.samirquiceno.micuentadecobro.ui.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.runtime.Composable


@Composable
fun PhotoPickerComponent(onImagePicked: (Uri?) -> Unit,): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>{

    //var pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
    return rememberLauncherForActivityResult(contract = PickVisualMedia(), ) { uri ->

        if (uri != null) {
            onImagePicked(uri)
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

}

