package com.pinto.mymovieappkt.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pinto.mymovieappkt.R


fun Context.unAvailableFeature(
    permission: String,
    permissionUnavailableMessage: String,
    permissionLauncher: ActivityResultLauncher<String>,
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.unavilable))
        .setMessage(permissionUnavailableMessage)
        .setPositiveButton(getString(R.string.grant)) { _, _ ->
            permissionLauncher.launch(permission)
        }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }
        .create()
        .show()
}

fun Context.permissionExplanation(
    permissionNeededMessage: String,
    positiveButtonFunction: () -> Unit,
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.permission_needed))
        .setMessage(
            permissionNeededMessage
        )
        .setPositiveButton(getString(R.string.ok)) { _, _ ->
            positiveButtonFunction()
        }
        .setNegativeButton(getString(R.string.no_thanks)) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}

fun Context.checkPermission(
    fragment: Fragment,
    permission: String,
    ifGrantedFunction: () -> Unit,
    permissionNeededMessage: String,
    permissionLauncher: ActivityResultLauncher<String>,
) {

    when {
        ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED -> {
            ifGrantedFunction()
        }

        fragment.shouldShowRequestPermissionRationale(permission) -> {
            permissionExplanation(permissionNeededMessage) {
                permissionLauncher.launch(permission)
            }
        }

        else -> {
            permissionLauncher.launch(permission)
        }
    }
}