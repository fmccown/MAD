package com.zybooks.photoexpress.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LightingColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageRepository(private val context: Context) {
   private lateinit var photoFile: File

   private fun createEmptyImageFile(): File {
      // Create a unique filename
      val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
      val imageFilename = "photo_$timeStamp.jpg"

      // Create the file in the Pictures directory on external storage
      val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
      return File(storageDir, imageFilename)
   }

   fun createPhotoFile(): Uri {
      photoFile = createEmptyImageFile()
      val photoUri = FileProvider.getUriForFile(
         context, "com.zybooks.photoexpress.fileprovider", photoFile
      )
      return photoUri
   }

   fun createColorFilter(brightness: Float): LightingColorFilter {
      var addColor = Color.Black
      var multColor = Color.White

      // 100 is the middle value
      if (brightness >= 101) {
         // Add color
         val addMult = (255 * (brightness - 100) / 100f).toInt()
         addColor = Color(red = addMult, green = addMult, blue = addMult)
      } else if (brightness < 100) {
         // Scale color down
         val brightMult = (255 * brightness / 100f).toInt()
         multColor = Color(red = brightMult, green = brightMult, blue = brightMult)
      }

      return LightingColorFilter(multColor, addColor)
   }

   suspend fun saveAlteredPhoto_ORIG(colorFilter: LightingColorFilter) = withContext(Dispatchers.IO) {
      // Load original image from file
      val origBitmap = BitmapFactory.decodeFile(photoFile.absolutePath, null)

      // Create a new Bitmap with the same dimensions as the original
      val alteredBitmap = Bitmap.createBitmap(
         origBitmap.width, origBitmap.height, origBitmap.config
      )

      // Draw original origBitmap on canvas and apply the color filter
      val canvas = Canvas(alteredBitmap.asImageBitmap())
      val paint = Paint()
      paint.colorFilter = colorFilter
      canvas.drawImage(
         image = origBitmap.asImageBitmap(),
         topLeftOffset = Offset.Zero,
         paint = paint
      )

      // Save altered photo with "_alt" suffix
      val newFileName = photoFile.nameWithoutExtension + "_alt.jpg"
      val altPhotoFile = File(photoFile.parentFile, newFileName)
      altPhotoFile.outputStream().use { outStream ->
         alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
      }
   }

   suspend fun saveAlteredPhoto(colorFilter: LightingColorFilter) = withContext(Dispatchers.IO) {
      // Load original image from file
      val origBitmap = BitmapFactory.decodeFile(photoFile.absolutePath, null)

      // Create a new Bitmap with the same dimensions as the original
      val alteredBitmap = Bitmap.createBitmap(
         origBitmap.width, origBitmap.height, origBitmap.config
      )

      // Draw original origBitmap on canvas and apply the color filter
      val canvas = Canvas(alteredBitmap.asImageBitmap())
      val paint = Paint()
      paint.colorFilter = colorFilter
      canvas.drawImage(
         image = origBitmap.asImageBitmap(),
         topLeftOffset = Offset.Zero,
         paint = paint
      )

      // Create an entry for the MediaStore
      val imageValues = ContentValues().apply {
         put(MediaStore.MediaColumns.DISPLAY_NAME, photoFile.name)
      }

      // Insert a new row into the MediaStore
      val resolver = context.applicationContext.contentResolver
      val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageValues)

      // Save bitmap as JPEG
      imageUri?.let {
         resolver.openOutputStream(it).use { outStream ->
            outStream?.let {
               alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            }
         }
      }
   }
}