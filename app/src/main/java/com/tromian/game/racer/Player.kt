package com.tromian.game.racer
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.util.Log
import androidx.core.graphics.scale
import androidx.core.graphics.scaleMatrix

class Player(
    context: Context,
    screenX : Int,
    screenY : Int,
) {
    val rect : Rect
    val bitmap: Bitmap
    var x : Float = 0f
    var y : Float = 0f
    var maxX : Float = 0f
    var minX : Float = 0f
    var speed : Int = 1
    var health : Int = 3

    init {
        val res: Bitmap = BitmapFactory.decodeResource(context.resources,
        R.drawable.car2)
        val matrix = Matrix().apply {
            this.postScale(0.7f,0.7f)
        }
        bitmap = Bitmap.createBitmap(res,0,0,res.width,res.height,matrix, false)

        x = screenX/2f - bitmap.width/2
        y = screenY.toFloat() - bitmap.height - screenY*0.1f
        maxX = screenX - bitmap.width.toFloat() - 50f
        minX = 50f
        rect = Rect(x.toInt(),y.toInt(),bitmap.width,bitmap.height)

    }

    fun update(){

        rect.left = x.toInt()
        rect.top = y.toInt()
        rect.right = x.toInt() + bitmap.width
        rect.bottom = y.toInt() + bitmap.height

    }


}