package com.smartperty.smartperty.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.igreenwood.loupe.Loupe
import kotlinx.android.synthetic.main.activity_landlord.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.net.URL

class ImageHelper {

    fun scaleImage(srcBmp: Bitmap, pixelSize:Int): Bitmap {
        val image: Bitmap
        if (srcBmp.getWidth() > srcBmp.getHeight()){

            image = Bitmap.createBitmap(
                srcBmp,
                srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                0,
                srcBmp.getHeight(),
                srcBmp.getHeight()
            );

        }else if (srcBmp.getWidth() < srcBmp.getHeight()){

            image = Bitmap.createBitmap(
                srcBmp,
                0,
                srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                srcBmp.getWidth(),
                srcBmp.getWidth()
            );
        }
        else image = srcBmp

        return image.scale(pixelSize,pixelSize)
    }

    fun convertString64ToImage(base64String: String): Bitmap {
        return try {
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        }
    }

    fun getString(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

    fun convertUrlToImage(urlString: String) : Bitmap? {
        try {
            val imageString = GlobalVariables.dbHelper.readDB(urlString)
            var image:Bitmap? = null
            if (imageString == "") {
                image = BitmapFactory.decodeStream(URL(urlString).openConnection().getInputStream())
                GlobalVariables.dbHelper.writeDB(urlString, getString(image)!!)
            }
            else {
                image = convertString64ToImage(imageString)
            }

            return image
        }
        catch (e: Exception) {
            return convertString64ToImage(urlString)
        }
    }

    fun openLargeImage(image: Bitmap) {
        val loupe = Loupe(
            GlobalVariables.activity.image,
            GlobalVariables.activity.ImageContainer).apply{ // imageView is your full screen ImageView and container is the direct parent of the ImageView
            onViewTranslateListener = object : Loupe.OnViewTranslateListener {
                override fun onStart(view: ImageView) {

                }
                override fun onViewTranslate(view: ImageView, amount: Float) {

                }
                override fun onRestore(view: ImageView) {

                }
                override fun onDismiss(view: ImageView) {
                    GlobalVariables.activity.ImageContainer.visibility = View.GONE
                }
            }
        }

        GlobalVariables.activity.image.setImageBitmap( image)
        GlobalVariables.activity.ImageContainer.visibility = View.VISIBLE
    }
}