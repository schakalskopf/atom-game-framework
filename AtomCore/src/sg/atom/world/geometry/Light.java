package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;

/**
 * Light class for the preprocessed lighting.
 */
public class Light {

    /**
     * Light position
     */
    private Vec3d pos;
    /**
     * Light color
     */
    private Vec3d color;
    /**
     * Light attenuation parameters
     */
    private float nearAttenStart;
    private float nearAttenEnd;
    private float farAttenStart;
    private float farAttenEnd;

    /**
     * Contructor
     *
     * @param pos position of the light
     * @param color color of the light
     * @param nas near attenuation start
     * @param nae near attenuation end
     * @param fas far attenuation start
     * @param fae far attenuation end
     */
    public Light(Vec3d pos, Vec3d color, float nas, float nae, float fas, float fae) {
        this.pos = pos;
        this.color = color;
        nearAttenStart = nas;
        nearAttenEnd = nae;
        farAttenStart = fas;
        farAttenEnd = fae;
    }

    /**
     * Convert light into String.
     *
     * @return string representation of the light
     */
    public String toString() {
        return "\nLight at " + pos
                + "\nNear attenuation start: " + nearAttenStart
                + "\nNear attenuation end: " + nearAttenEnd
                + "\nFar attenuation start: " + farAttenStart
                + "\nFar attenuation end: " + farAttenEnd
                + "\nColor: " + color;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Vec3d getColor() {
        return color;
    }

    public float getNearAttenuationStart() {
        return nearAttenStart;
    }

    public float getNearAttenuationEnd() {
        return nearAttenEnd;
    }

    public float getFarAttenuationStart() {
        return farAttenStart;
    }

    public float getFarAttenuationEnd() {
        return farAttenEnd;
    }

    /**
     * Compute the light emitted by this light to given position.
     *
     * @param point point to compute the light color at
     * @param normal surface normal at given point
     * @return color emitted by this light as RGB vector
     */
    public Vec3d computeColorAt(Vec3d point, Vec3d normal) {
        Vec3d result = new Vec3d(0, 0, 0);

        Vec3d toLight = pos.sub(point);
        float dist = toLight.len();

        // Test if outside the lights radius (or too near)
        if (dist < nearAttenStart || dist > farAttenEnd) {
            return result;
        }

        float cosineOfIncidence = Math.abs(Vec3d.normalized(toLight).dot(normal));
        float intensity = 1;

        if (dist > farAttenStart) {
            intensity = 1 - ((dist - farAttenStart) / (farAttenEnd - farAttenStart));
        } else if (dist < nearAttenEnd) {
            intensity = (dist - nearAttenStart) / (nearAttenEnd - nearAttenStart);
        }
        result = color.scale(intensity * cosineOfIncidence);

        return result;
    }
}
