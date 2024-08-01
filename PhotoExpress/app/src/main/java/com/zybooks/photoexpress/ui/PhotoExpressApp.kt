package com.zybooks.photoexpress.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LightingColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhotoExpressApp() {
   var capturedImageUri by remember { mutableStateOf(Uri.EMPTY) }
   val context = LocalContext.current
   val photoFile: File = context.createImageFile()
   val photoUri: Uri = FileProvider.getUriForFile(
      context, "com.zybooks.photoexpress.fileprovider", photoFile)

   val cameraLauncher =
      rememberLauncherForActivityResult(
         ActivityResultContracts.TakePicture()
      ) { success ->
         if (success) {
            capturedImageUri = photoUri
            println("capturedImageUri = $capturedImageUri")
         }
      }

   val imageVisible = capturedImageUri.path?.isNotEmpty() == true
   var sliderPosition by remember { mutableFloatStateOf(100f) }
   val coroutineScope = rememberCoroutineScope()

   Column(
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(16.dp)
   ) {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         Button(onClick = {
            cameraLauncher.launch(photoUri)
         }) {
            Text("Take Picture")
         }
         if (imageVisible) {
            Button(onClick = {
               coroutineScope.launch {
                  saveAlteredPhoto(
                     photoFile = photoFile,
                     colorFilter = changeBrightness(sliderPosition)
                  )
               }
            }) {
               Text("Save")
            }
         }
      }

      if (imageVisible) {
         Image(
            painter = rememberAsyncImagePainter(capturedImageUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = changeBrightness(sliderPosition)
         )

         Slider(
            value = sliderPosition,
            valueRange = 0f..200f,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.weight(1f)
         )
      }
      else {
         Spacer(Modifier.weight(1f))
      }
   }
}

fun Context.createImageFile(): File {
   // Create a unique filename
   val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
   val imageFilename = "photo_$timeStamp.jpg"

   // Create the file in the Pictures directory on external storage
   val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
   return File(storageDir, imageFilename)
}

fun changeBrightness(brightness: Float): LightingColorFilter {
   var addColor = Color.Black
   var multColor = Color.White

   // 100 is the middle value
   if (brightness >= 101) {
      // Add color
      val addMult = (255 * brightness / 100f - 1).toInt()
      addColor = Color(red = addMult, green = addMult, blue = addMult)
   } else {
      // Scale color down
      val brightMult = (255 * brightness / 100f).toInt().coerceAtMost(255)
      multColor = Color(red = brightMult, green = brightMult, blue = brightMult)
   }

   return LightingColorFilter(multColor, addColor)
}

suspend fun saveAlteredPhoto(
   photoFile: File,
   colorFilter: LightingColorFilter
) = withContext(Dispatchers.IO) {
   // Read original image
   val origBitmap = BitmapFactory.decodeFile(photoFile.absolutePath, null)

   // Create a new origBitmap with the same dimensions as the original
   val alteredBitmap = Bitmap.createBitmap(
      origBitmap.width, origBitmap.height, origBitmap.config)

   // Draw original origBitmap on canvas and apply the color filter
   val canvas = Canvas(alteredBitmap.asImageBitmap())
   val paint = Paint()
   paint.colorFilter = colorFilter
   canvas.drawImage(
      image = origBitmap.asImageBitmap(),
      topLeftOffset = Offset.Zero,
      paint = paint
   )

   // Save altered origBitmap over the original image
   val imageFile = File(photoFile.absolutePath)
   imageFile.outputStream().use { outStream ->
      alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
   }
}
