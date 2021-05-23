package com.tromian.game.racer

import android.content.Context
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var senManager: SensorManager
    private lateinit var senGyroscope: Sensor

    private var screenX : Int = 0
    private var screenY : Int = 0


    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set display sizes to X, Y parameters
        checkDisplaySize()

        //init RaceScene object
        gameView = GameView(this,screenX,screenY)
        //attach gameView to contentview
        setContentView(gameView)

        //Register sensor
        senManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        senGyroscope = senManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        senManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_GAME)

        gameView?.run()

    }
    override fun onPause() {
        super.onPause()
        senManager.unregisterListener(this)
        gameView?.pause()
    }

    override fun onResume() {
        super.onResume()
        senManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_GAME)
        gameView?.resume()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        gameView?.updatePlayerX(event)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun checkDisplaySize(){
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.display
        } else {
            windowManager.defaultDisplay
        }

        val size = Point()
        display?.getSize(size)

        screenX = size.x
        screenY = size.y
    }

}
