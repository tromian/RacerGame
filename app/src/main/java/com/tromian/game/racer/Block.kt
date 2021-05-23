package com.tromian.game.racer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.util.Log
import kotlin.random.Random

class Block(
    context: Context,
    screenX : Int,
    screenY : Int
){
    val rect : Rect
    val bitmap: Bitmap
    var x : Float = 0f
    var y : Float = 0f

    init {
        val res: Bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.block)
        val matrix = Matrix().apply {
            this.postScale(0.4f,0.4f)
        }
        bitmap = Bitmap.createBitmap(res,0,0,res.width,res.height,matrix, false)

        val rand = Random.nextInt(3)
        when(rand){
            0 -> {
                x = 50f
            }
            1 -> {
                x = screenX/2f - bitmap.width/2
            }
            2 -> {
                x = screenX - bitmap.width.toFloat() - 50
            }
        }
        rect = Rect(x.toInt(),y.toInt(),bitmap.width,bitmap.height)
    }

    fun update(){
        rect.left = x.toInt()
        rect.top = y.toInt()
        rect.right = x.toInt() + this.bitmap.width
        rect.bottom = y.toInt() + this.bitmap.height
    }


}