package com.example.sensor;

public interface AccelerometerListener {
    public void onAccelerationChangedInt(float x, float y, float z);

    public void onAccelerationChanged(float x, float y, float z);
    
    public void onShake(float force);
    
    public void flipUp();
    
    public void flipDown();

}
