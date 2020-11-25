package com.smartperty.smartperty.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream
import java.net.URL

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
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

fun getString(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.NO_WRAP)
}

fun convertUrlToImage(urlString: String) : Bitmap {
//    try {
//        val imageString = GlobalVariables.dbHelper.readDB(urlString)
//        var image:Bitmap? = null
//        if (imageString == "") {
//            image = BitmapFactory.decodeStream(URL(urlString).openConnection().getInputStream())
//            GlobalVariables.dbHelper.writeDB(urlString, getString(image)!!)
//        }
//        else {
//            image = convertString64ToImage(imageString)
//        }
//
//        return image
//    }
//    catch (e: Exception) {
//        return Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
//    }

    return BitmapFactory.decodeStream(URL(urlString).openConnection().getInputStream())
}