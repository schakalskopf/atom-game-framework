package sg.atom.world.geometry.algebra;

import java.io.*;

/**
 * Class to model quaternions.
 * Contains C++ code from Hannu Niemist� ported to Java.
 *
 * @author <a href="mailto:tvlehton@cc.hut.fi">Esa Nuuros</a>
 * @author <a href="mailto:tvlehton@cc.hut.fi">Tuukka Lehtonen</a>
 */
public class Quaternion implements Externalizable
{
  /** Scalar part of the quaternion */
  public float r;
  /** First vector part of the quaternion */
  public float i;
  /** Second vector part of the quaternion */
  public float j;
  /** Third vector part of the quaternion */
  public float k;

  /**
   * Reads an externalized quaternion.
   *
   * @param in the input stream to read
   */
  public void readExternal(ObjectInput in)
  {
    try
    {
      r = in.readFloat();
      i = in.readFloat();
      j = in.readFloat();
      k = in.readFloat();
    }
    catch(Exception e)
    {
      System.out.println("Error de-externalizing Quaternion !");
    }
  }

  /**
   * Externalizes this quaternion.
   *
   * @param in the output stream to write
   */
  public void writeExternal(ObjectOutput out)
  {
    try
    {
      out.writeFloat(r);
      out.writeFloat(i);
      out.writeFloat(j);
      out.writeFloat(k);
    }
    catch(Exception e)
    {
      System.out.println("Error externalizing Quaternion !");
    }
  }

  /**
   * Creates a zero Quaternion.
   */
  public Quaternion()
  {}

  /**
   * Copies a Quaternion.
   *
   * @param copy the Quaternion to copy
   */
  public Quaternion(Quaternion copy)
  {
    r = copy.r;
    i = copy.i;
    j = copy.j;
    k = copy.k;
  }


  /**
   * Creates a Quaternion corresponding to the given scalar.
   */
  public Quaternion(float scalar)
  {
    r = scalar;
    i = j = k = 0;
  }
  
  /**
   * Creates a Quaternion corresponding to the given scalar
   * and vector parts.
   *
   * @param s the scalar part
   * @param v the vector part
   */
  public Quaternion(float s, Vec3d v)
  {
    r = s;
    i = v.c[0];
    j = v.c[1];
    k = v.c[2];
  }
  
  /**
   * Creates a Quaternion corresponding to the given scalar
   * and vector parts.
   *
   * @param s the scalar part
   * @param x first vector part
   * @param y second vector part
   * @param z third vector part
   */
  public Quaternion(float s, float x, float y, float z)
  {
    r = s;
    i = x;
    j = y;
    k = z;
  }
  
  /**
   * Adds the given quaternion to this quaternion.
   *
   * @param b the quaternion to add
   * @return this quaternion
   */
  public Quaternion addTo(Quaternion b)
  {
    r += b.r;
    i += b.i;
    j += b.j;
    k += b.k;

    return this;
  }

  /**
   * Return a new quaternion containing the sum of this and
   * another quaternion.
   *
   * @param b the other quaternion
   * @return a new quaternion containing the sum
   */
  public Quaternion add(Quaternion b)
  {
    return new Quaternion(r + b.r, i + b.i, j + b.j, k + b.k);
  }
  
  /**
   * Subtracts the given quaternion from this quaternion.
   *
   * @param b the quaternion to subtract
   * @return this quaternion
   */
  public Quaternion subFrom(Quaternion b)
  {
    r -= b.r;
    i -= b.i;
    j -= b.j;
    k -= b.k;

    return this;
  }

  /**
   * Return a new quaternion containing the subtraction of this and
   * another quaternion.
   *
   * @param b the quaternion to subtract from this
   * @return a new quaternion containing the subtraction
   */
  public Quaternion sub(Quaternion b)
  {
    return new Quaternion(r - b.r, i - b.i, j - b.j, k - b.k);
  }

  /**
   * Scale this quaternion by a scalar.
   *
   * @param s the scaling scalar
   * @return this quaternion
   */
  public Quaternion scaleTo(float s)
  {
    r *= s;
    i *= s;
    j *= s;
    k *= s;

    return this;
  }

  /**
   * Return a new scaled version of this quaternion.
   *
   * @param s the scaling scalar
   * @return a new scaled quaternion
   */
  public Quaternion scale(float s)
  {
    return new Quaternion(r * s, i * s, j * s, k * s);
  }

  /**
   * Multiplies this quaternion by another.
   *
   * @param b the multiplying quaternion
   * @return this quaternion
   */
  public Quaternion mulTo(Quaternion b)
  {
    float temps = r * b.r - i * b.i - j * b.j - k * b.k;
    float tempi = r * b.i + i * b.r + j * b.k - k * b.j;
    float tempj = r * b.j + j * b.r + k * b.i - i * b.k;
    k           = r * b.k + k * b.r + i * b.j - j * b.i;
    r = temps;
    i = tempi;
    j = tempj;

    return this;
  }

  /**
   * Return a new quaternion containing this quaternion multiplied by
   * another.
   *
   * @param b the multiplying quaternion
   * @return a new quaternion containing the multiplication
   */
  public Quaternion mul(Quaternion b)
  {
    return new
      Quaternion(r * b.r - i * b.i - j * b.j - k * b.k,
                 r * b.i + i * b.r + j * b.k - k * b.j,
                 r * b.j + j * b.r + k * b.i - i * b.k,
                 r * b.k + k * b.r + i * b.j - j * b.i);
  }

  /**
   * Return a new negated version of this quaternion.
   *
   * @return a new negated quaternion
   */
  public Quaternion neg()
  {
    return new Quaternion(-r,-i,-j,-k);
  }

  /**
   * Return the conjugate of this quaternion.
   *
   * @return a new conjugate quaternion
   */
  public Quaternion conjugate()
  {
    return new Quaternion(r,-i,-j,-k);
  }

  /**
   * Returns the squared length of this quaternion.
   *
   * @return this quaternions length squared
   */
  public float lensq()
  {
    return (r * r + i * i + j * j + k * k);
  }

  /**
   * Returns the length of this quaternion.
   *
   * @return this quaternions length
   */
  public float len()
  {
    return (float)Math.sqrt(lensq());
  }

  /**
   * Normalize this quaternion.
   *
   * @return this quaternion normalized
   */
  public Quaternion normalize()
  {
    float invLen = len();
    if(invLen > 0) invLen = 1 / invLen;
    
    return scaleTo(invLen);
  }

  /**
   * Get a normalized version of the given quaternion.
   *
   * @param v the quaternion to normalize
   * @return a new normalized quaternion
   */
  public static Quaternion normalized(Quaternion v)
  {
    float invLen = v.len();
    if(invLen > 0) invLen = 1 / invLen;
    
    return new Quaternion(v.r * invLen, v.i * invLen, v.j * invLen, v.k * invLen);
  }
  
  /**
   * Returns the rotation matrix representation of this quaternion.
   * Assumes nothing about the quaternion, and always normalizes
   * while creating the rotation matrix.
   *
   * @return the rotation matrix
   */
  public Matrix3 rotationMatrix()
  {
    Matrix3 result = new Matrix3();
    float invLen = lensq();
    if(invLen == 0) return result;
    else invLen = 1 / (float)Math.sqrt(invLen);

    float temp = r * r * invLen;

    result.c[0][0] = temp;
    result.c[1][1] = temp;
    result.c[2][2] = temp;

    temp = i * i * invLen;
    result.c[0][0] += temp;
    result.c[1][1] -= temp;
    result.c[2][2] -= temp;

    temp = j * j * invLen;
    result.c[0][0] -= temp;
    result.c[1][1] += temp;
    result.c[2][2] -= temp;

    temp = k * k * invLen;
    result.c[0][0] -= temp;
    result.c[1][1] -= temp;
    result.c[2][2] += temp;

    temp = r * i * invLen;
    result.c[1][2] = temp;
    result.c[2][1] =- temp;

    temp = r * j * invLen;
    result.c[0][2] =- temp;
    result.c[2][0] = temp;

    temp = r * k * invLen;
    result.c[0][1] = temp;
    result.c[1][0] =- temp;

    temp = i * j * invLen;
    result.c[0][1] += temp;
    result.c[1][0] += temp;

    temp = j * k * invLen;
    result.c[1][2] += temp;
    result.c[2][1] += temp;

    temp = k * i * invLen;
    result.c[0][2] += temp;
    result.c[2][0] += temp;

    result.c[0][1] += result.c[0][1];
    result.c[0][2] += result.c[0][2];
    result.c[1][0] += result.c[1][0];
    result.c[1][2] += result.c[1][2];
    result.c[2][0] += result.c[2][0];
    result.c[2][1] += result.c[2][1];

    return result;
  }

  /**
   * Returns the rotation matrix representation of this quaternion.
   * Assumes the quaternion to already be normalized and is therefore
   * faster than rotationMatrix
   *
   * @return the rotation matrix
   */
  public Matrix3 rotationMatrix2()
  {
    Matrix3 result = new Matrix3();

    float temp = r * r;
    result.c[0][0] = temp;
    result.c[1][1] = temp;
    result.c[2][2] = temp;

    temp = i * i;
    result.c[0][0] += temp;
    result.c[1][1] -= temp;
    result.c[2][2] -= temp;

    temp = j * j;
    result.c[0][0] -= temp;
    result.c[1][1] += temp;
    result.c[2][2] -= temp;

    temp = k * k;
    result.c[0][0] -= temp;
    result.c[1][1] -= temp;
    result.c[2][2] += temp;

    temp = r * i;
    result.c[1][2] = temp;
    result.c[2][1] =- temp;

    temp = r * j;
    result.c[0][2] =- temp;
    result.c[2][0] = temp;

    temp = r * k;
    result.c[0][1] = temp;
    result.c[1][0] =- temp;

    temp = i * j;
    result.c[0][1] += temp;
    result.c[1][0] += temp;

    temp = j * k;
    result.c[1][2] += temp;
    result.c[2][1] += temp;

    temp = k * i;
    result.c[0][2] += temp;
    result.c[2][0] += temp;

    result.c[0][1] += result.c[0][1];
    result.c[0][2] += result.c[0][2];
    result.c[1][0] += result.c[1][0];
    result.c[1][2] += result.c[1][2];
    result.c[2][0] += result.c[2][0];
    result.c[2][1] += result.c[2][1];

    return result;
  }

  /**
   * Interpolates between two given quaternions.
   *
   * @param q1 starting quaternion
   * @param q2 ending quaternion
   * @param t a scalar in [0,1], where 0 gives the starting and 1 the
   * ending point of interpolation
   */
  public void interpolate(Quaternion q1, Quaternion q2, float t)
  {
    float angle = (float)Math.acos(q1.r * q2.r + q1.i * q2.i + q1.j * q2.j + q1.k * q2.k);

    if(angle > 0)
      {
        float div = 1 / (float)Math.sin(angle);
        float c1 = (float)Math.sin(angle * (1 - t)) * div;
        float c2 = (float)Math.sin(angle * t) * div;
        
        r = q1.r * c1 + q2.r * c2;
        i = q1.i * c1 + q2.i * c2;
        j = q1.j * c1 + q2.j * c2;
        k = q1.k * c1 + q2.k * c2;
        
      }
    else
      {
        r = q1.r;
        i = q1.i;
        j = q1.j;
        k = q1.k;
      }
  }
  
  /**
   * Interpolate between two given quaternions with a given speed.
   *
   * @param q1 starting quaternion
   * @param q2 ending quaternion
   * @param speed interpolation speed
   */
  public void interpolateWithSpeed(Quaternion q1, Quaternion q2, float speed)
  {
    float angle = (float)Math.acos(q1.r * q2.r + q1.i * q2.i + q1.j * q2.j + q1.k * q2.k);
    float t = speed/angle;
    if(t>1) t = 1;
    interpolate(q1,q2,t);
  }

  /**
   * Give a quaternion describing the position defined by the given axis
   * and angle of rotation.
   *
   * @param angle the angle of rotation around the axis
   * @param axis the axis around which to rotate
   * @return a quaternion representing the given parameters
   */
  public static Quaternion rotation(float angle, Vec3d axis)
  {
    angle *= 0.5;
    return new
      Quaternion((float)Math.cos(angle),
                 Vec3d.normalized(axis).scale((float)Math.sin(angle)));
  }
}
