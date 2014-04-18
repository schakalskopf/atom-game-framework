/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.enviroment;

/**
 * A fuzzy weather system make up from some parameters. A state can be indicate
 * by deference the fuzzy values. Most important application is to control the
 * enviroment and the visualization of weather simmulation.
 *
 * <p>
 *
 * @author CuongNguyen
 */
public abstract class EnviromentWeather {

    //boolean isRaining;
    float rainyLevel;
    float cloudyLevel;
    float brightnessLevel;
    float foggyLevel;
    float sunnyLevel;
    //Rain
    //Cloudy
    //Sunny
    //

    //Common and visualization elements-----------------------------------------
    //FogFilter
    //Lensflare
    //Sunray
    //RainEffects
    public void changeWeatherTo(EnviromentWeather newWeather) {
    }
    
    public void setConfiguration(){
        
    }
    
    public void updateParams(){
        
    }
}
