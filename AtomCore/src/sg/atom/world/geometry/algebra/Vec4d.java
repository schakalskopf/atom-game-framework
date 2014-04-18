package sg.atom.world.geometry.algebra;

/**
 * 4d vector class
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Vec4d
{
  public float c[] = new float [4];

  /**
   * Default constructor
   *
   */
  public Vec4d() {}

  /**
   * Constructor
   *
   * @param s x,y,z and w component of vector
   */
  public Vec4d(float s)
  {
    c[0] = s;
    c[1] = s;
    c[2] = s;
    c[3] = s;
  }

  /**
   * Constructor
   *
   * @param x x component of vector
   * @param y y component of vector
   * @param z z component of vector
   * @param w w component of vector
   */
  public Vec4d(float x, float y, float z, float w)
  {
    c[0] = x;
    c[1] = y;
    c[2] = z;
    c[3] = w;
  }

  /**
   * Copy constructor
   *
   * @param v vector to copy
   */
  public Vec4d(Vec4d v)
  {
    c[0] = v.c[0];
    c[1] = v.c[1];
    c[2] = v.c[2];
    c[3] = v.c[3];
  }

  /**
   * Checks if two vectors are equal.
   *
   * @param v vector to compare this to.
   * @return true if equal, false otherwise.
   */
  public boolean equals(Vec4d v)
  {
    if (c[0] == v.c[0] &&
        c[1] == v.c[1] &&
        c[2] == v.c[2] &&
        c[3] == v.c[3])
      return true;
    return false;
  }

  /**
   * Add two vectors
   *
   * @param v vector to add
   * @return sum of vectors
   */
  public Vec4d add(Vec4d v)
  {
    return new Vec4d(c[0] + v.c[0], c[1] + v.c[1], c[2] + v.c[2], c[3] + v.c[4]);
  }

  /**
   * Add vector to this vector
   *
   * @param v vector to add
   * @return this vector after addition
   */
  public Vec4d addTo(Vec4d v)
  {
    for(int i = 0; i < 4; i++)
      c[i] += v.c[i];

    return this;
  }

  /**
   * Subtract two vectors
   *
   * @param v vector to subtract
   * @return subtraction of vectors
   */
  public Vec4d sub(Vec4d v)
  {
    return new Vec4d(c[0] - v.c[0], c[1] - v.c[1], c[2] - v.c[2], c[3] - v.c[3]);
  }

  /**
   * Subtract vector from this vector
   *
   * @param v vector to subtract
   * @return this vector after subtaction
   */
  public Vec4d subFrom(Vec4d v)
  {
    for(int i = 0; i < 4; i++)
      c[i] -= v.c[i];

    return this;
  }

  /**
   * Multiply by scalar
   *
   * @param s scalar to multiply with
   * @return result of scaling
   */
  public Vec4d scale(float s)
  {
    return new Vec4d(c[0] * s, c[1] * s, c[2] * s, c[3] * s);
  }

  /**
   * Scale this vector
   *
   * @param s scalar to multiply with
   * @return this vector after scaling
   */
  public Vec4d scaleTo(float s)
  {
    for(int i = 0; i < 4; i++)
      c[i] *= s;
    
    return this;
  }

  /**
   * Compute the dot product of two 4d vectors
   *
   * @param v another vector
   * @return dot product
   */
  public float dot(Vec4d v)
  {
    return (c[0] * v.c[0] + c[1] * v.c[1] + c[2] * v.c[2] + c[3] * v.c[3]);
  }

  /**
   * Compute the squared length of vector
   *
   * @return squared length
   */
  public float lensq()
  {
    return this.dot(this);
  }

  /**
   * Get the length of a vector
   *
   * @return vector length
   */
  public float len()
  {
    return (float)Math.sqrt(lensq());
  }

  /**
   * Normalize a vector
   *
   * @return this vector
   */
  public Vec4d normalize()
  {
    float invLen = len();
    if(invLen > 0) invLen = 1 / invLen;

    return scaleTo(invLen);
  }

  /**
   * Return a normalized copy of a vector
   *
   * @param v vector to normalize
   * @return normalized vector
   */
  public static Vec4d normalized(Vec4d v)
  {
    float invLen = v.len();
    if(invLen > 0) invLen = 1 / invLen;

    return new Vec4d(v.c[0] * invLen, v.c[1] * invLen, v.c[2] * invLen, v.c[3] * invLen);
  }


  /**
   * Convert Vec4d to String
   *
   * @return Vec4d as String
   */
  public String toString()
  {
    return "<" + c[0] + "," + c[1] + "," + c[2] + "," + c[3] + ">";
  }  
}
