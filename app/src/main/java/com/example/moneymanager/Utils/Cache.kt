package com.example.moneymanager.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

//Taken from: https://gist.github.com/nikartm/79932c0a4f0a644f7ce020143146db98#file-cache-java-L1
class Cache(val context: Context) {
    val TAG = Cache::class.java.getSimpleName()

    private val CHILD_DIR = "images"
    @SuppressLint("NewApi") private val TEMP_FILE_NAME = LocalDateTime.now().toString()
    private val FILE_EXTENSION = ".png"
    private val COMPRESS_QUALITY = 25


    /**
     * Save image to the App cache
     * @param bitmap to save to the cache
     * @param name file name in the cache.
     * If name is null file will be named by default {@link #TEMP_FILE_NAME}
     * @return file dir when file was saved
     */

    fun saveImgToCache(bitmap: Bitmap, name: String?): File? {
        var cachePath: File? = null
        var fileName = TEMP_FILE_NAME
        if (!TextUtils.isEmpty(name)) {
            fileName = name ?: "my name"
        }
        try {
            //Creates a new File instance from a parent abstract pathname and a child pathname string.
            cachePath = File(context.cacheDir, CHILD_DIR)
            //Creates the directory named by this abstract pathname (child dir)
            cachePath.mkdirs()
            //create file to specified directory
            val stream = FileOutputStream("$cachePath/$fileName$FILE_EXTENSION")
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, stream)
            stream.close()
        } catch (e: IOException) {
            Log.e(TAG, "saveImgToCache error: $bitmap", e)
        }
        return cachePath
    }

    /**
     * Save an image to the App cache dir and return it [Uri]
     * @param bitmap to save to the cache
     */
    fun saveToCacheAndGetUri(bitmap: Bitmap?): Uri? {
        return saveToCacheAndGetUri(bitmap, null)
    }

    /**
     * Save an image to the App cache dir and return it [Uri]
     * @param bitmap to save to the cache
     * @param name file name in the cache.
     * If name is null file will be named by default [.TEMP_FILE_NAME]
     */
    private fun saveToCacheAndGetUri(bitmap: Bitmap?, name: String?): Uri? {
        val file = bitmap?.let { saveImgToCache(it, name) }
        return file?.let { getImageUri(it, name) }
    }

    // Get an image Uri by name without extension from a file dir
    private fun getImageUri(fileDir: File,  name: String?): Uri {
        val context: Context = context
        var fileName = TEMP_FILE_NAME
        if (!TextUtils.isEmpty(name)) {
            fileName = name!!
        }
        val newFile = File(fileDir, fileName + FILE_EXTENSION)
        return FileProvider.getUriForFile(context, context.packageName + ".provider", newFile)
    }


}