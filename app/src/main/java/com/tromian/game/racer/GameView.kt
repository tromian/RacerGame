package com.tromian.game.racer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.SurfaceView
import android.widget.Toast
import kotlinx.coroutines.*

class GameView(
    context: Context,
    val screenX: Int = 0,
    val screenY: Int = 0
) : SurfaceView(context) {


    private var paint: Paint = Paint()
    private var canvas: Canvas? = null
    private var player : Player = Player(context,screenX,screenY)
    private var playerPosition : Float = screenX/2f - player.bitmap.width/2

    @Volatile
    private var playing = true
    private var accident = false
    private val DELAY_TIME = 16L

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private var blocks : MutableList<Block> = mutableListOf()


    //run game
    fun run() {
        scope.launch {
            while (playing) {

                if (accident){
                    delay(2000)
                    accident = false
                }

                update()

                updateBlock()

                try {
                    draw()
                }catch (e : ConcurrentModificationException){
                    holder.unlockCanvasAndPost(canvas)
                    Log.d("exc",e.toString())
                }

                delay(DELAY_TIME)

            }
        }
    }

    // stopping game
    fun pause() {
        playing = false
    }

    // starting game
    fun resume() {
        playing = true
        run()
    }


    fun updateBlock(){
        Log.d("bum",blocks.size.toString())
        if (accident){
            blocks.removeAll(blocks)
        }
        if (blocks.isEmpty()){
            blocks.add(Block(context, screenX = screenX,screenY = screenY))
        }
        if (blocks[blocks.size-1].y > player.bitmap.height*1.5 + blocks[blocks.size-1].bitmap.height){
            blocks.add(Block(context, screenX = screenX,screenY = screenY))
        }
        if (blocks.size < 10){
            blocks.forEach {
                it.y += 5f
            }
        }
        if (blocks.size >= 10){
            blocks.forEach {
                it.y += 5f
            }
        }
        if (blocks.size > 25){
            //blocks.removeAt(0)
            blocks.forEach {
                it.y += 8f
            }
        }
        if (blocks.size > 40){
            //blocks.removeAt(0)
            blocks.forEach {
                it.y += 10f
            }
        }
        if (blocks.size > 60){
            //blocks.removeAt(0)
            blocks.forEach {
                it.y += 14f
            }
        }

    }

    fun updatePlayerX(event: SensorEvent?){
        // Method takes rotation value from Sensor
        // and set player X coordinate
        val mySensor = event?.sensor
        if (mySensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {

            val y: Float = event.values[0]

            if (y >= 0) {
                if (playerPosition < player.maxX) {
                    playerPosition += y*2
                }

            } else if (y < 0) {
                if (playerPosition > player.minX) {
                    playerPosition += y*2
                    //Log.d("sens", lastUpdate.toString())
                }
            }

            player.x = playerPosition

        }
    }

    // handle updating coordinates
    private fun update() {
        player.update()

        for (i in 0 until blocks.size){
            blocks[i].update()
            if (Rect.intersects(player.rect, blocks[i].rect)){
                accident = true
            }
        }

    }

    //draw elements on view
    private fun draw() {

        if (holder.surface.isValid) {
            //locking the canvas
            canvas = holder.lockCanvas()

            //Log.d("bum", player.x.toString())
            canvas?.let { canvas ->
                //drawing background
                canvas.drawColor(Color.BLACK)
                //drawing player
                canvas.drawBitmap(
                    player.bitmap,
                    player.x,
                    player.y,
                    paint
                )
                //drawing blocks
                blocks.forEach {
                    canvas.drawBitmap(it.bitmap,
                        it.x,
                        it.y,
                        paint
                    )
                }
            }

            //unlocking the canvas
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun onFail(){

    }

}