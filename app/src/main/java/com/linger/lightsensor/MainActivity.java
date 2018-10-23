package com.linger.lightsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    //传感器管理
    SensorManager sensorManager;

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        relativeLayout= (RelativeLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册光线传感器监听器
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_GAME);
    }
    //监听传感器值的变化
    @Override
    public void onSensorChanged(SensorEvent event) {
        //获取传感器的值
        float[] values=event.values;
        int sensorType=event.sensor.getType();
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                //获取窗口管理属性
                WindowManager.LayoutParams lp=this.getWindow().getAttributes();
                //计算屏幕的亮度
                lp.screenBrightness=Float.valueOf(values[0])*(1f/255f);
                //设置屏幕亮度
                this.getWindow().setAttributes(lp);
                if(values[0]==0){
                    relativeLayout.setBackgroundResource(R.drawable.night);
                }else {
                    relativeLayout.setBackgroundResource(R.drawable.day);
                }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
