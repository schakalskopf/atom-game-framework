package sg.atom.world.geometry.collision;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.algebra.Matrix3;

public class Ball {

    Vec3d center;
    float radius, radius2;

    public Ball(Vec3d center, float radius) {
        this.center = center;
        this.radius = radius;
        this.radius2 = radius * radius;
    }

    public Ball(Vec3d a, Vec3d b) {
        center = (a.add(b)).scaleTo(0.5f);
        radius2 = (b.sub(a)).lensq() * 0.25f;
        radius = (float) Math.sqrt(radius2);
    }

    public Ball(Vec3d a, Vec3d b, Vec3d c) {
        Vec3d ta = a.sub(c);
        Vec3d tb = b.sub(c);

        float a2 = ta.lensq();
        float b2 = tb.lensq();
        float ab = ta.dot(tb);

        if (a2 == 0) {
            center = (b.add(c)).scaleTo(0.5f);
        } else if (b2 == 0) {
            center = (a.add(c)).scaleTo(0.5f);
        } else if (ab * ab == a2 * b2) {
            center = (a.add(c)).scaleTo(0.5f);
        } else {
            float p = 0.5f * (a2 * b2 - ab * b2) / (a2 * b2 - ab * ab);
            float q = 0.5f * (a2 * b2 - ab * a2) / (a2 * b2 - ab * ab);

            center = (ta.scale(p)).addTo(tb.scale(q));
        }

        radius2 = center.lensq();
        radius = (float) Math.sqrt(radius2);
        center.addTo(c);
    }

    public Ball(Vec3d a, Vec3d b, Vec3d c, Vec3d d) {
        Vec3d ta = a.sub(d);
        Vec3d tb = b.sub(d);
        Vec3d tc = c.sub(d);

//     System.out.println("ta: " + ta);
//     System.out.println("tb: " + tb);
//     System.out.println("tc: " + tc);

        Matrix3 mx = new Matrix3();

        mx.c[0][0] = ta.c[0];
        mx.c[0][1] = ta.c[1];
        mx.c[0][2] = ta.c[2];
        mx.c[1][0] = tb.c[0];
        mx.c[1][1] = tb.c[1];
        mx.c[1][2] = tb.c[2];
        mx.c[2][0] = tc.c[0];
        mx.c[2][1] = tc.c[1];
        mx.c[2][2] = tc.c[2];

        center = new Vec3d(ta.len() * 0.5f,
                tb.len() * 0.5f,
                tc.len() * 0.5f);

//     System.out.println("center: "+center);
//     System.out.println("mx:\n"+mx);
        Matrix3 inv = Matrix3.inverse(mx);
//     System.out.println("inv:\n"+inv);
        if (inv != null) {
            center = inv.mul(center);
        }
//     System.out.println("center: "+center);

        radius2 = center.lensq();
        radius = (float) Math.sqrt(radius2);
//     System.out.println("radius: "+radius);
        center.addTo(d);
//     System.out.println("final center: "+center);
    }

    public boolean isInside(Vec3d p) {
//     System.out.println("Ball::isInside()");
        return (p.sub(center).lensq() < radius2);
    }

    // olettaa, ett� |dir| = 1
    public float angularDistance(Vec3d p, Vec3d dir) {
        Vec3d c = center.sub(p);
        float lenc2 = c.lensq();
        float cd = c.dot(dir);

        float term1 = lenc2 - radius2;
        if (term1 < 0.0f) {
            return 1.0f;
        }
        float term2 = lenc2 - cd * cd;
        if (term2 < radius2) {
            return 1.0f;
        }

        return ((cd * (float) Math.sqrt(term1) + radius * (float) Math.sqrt(term2)) / lenc2);
    }

    public Vec3d getCenter() {
        return center;
    }

    public void setCenter(Vec3d center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        this.radius2 = radius * radius;
    }

    public void addPointInside(Vec3d p) {
        Vec3d P = p.sub(center);
        float lenP = P.lensq();

        if (lenP <= radius2) {
            return;
        }
        lenP = (float) Math.sqrt(lenP);

        center.addTo(P.scaleTo(0.5f * (1.0f - radius / lenP)));
        radius = 0.5f * (radius + lenP);
        radius2 = radius * radius;
    }

    /*
     public boolean firstIntersection(Ray ray, Intersection is)
     {
     Vec3d et = center - ray.place;
     float pt = et * ray.dir;
     float disk = pt*pt-et.lensq()+radius2;
     if(disk<=0)
     return(0);
     disk = sqrt(disk);
     if(pt+disk<=0)
     return(0);
     if(pt-disk > 0) {
     is.t = pt-disk;
     is.obj = this;
     return(1);
     }
     is.t = pt+disk;
     is.obj = this;
     return(1);
     }
     */
    public static Ball boundingBall(Vec3d[] points) {
        if (points.length < 1) {
            return new Ball(new Vec3d(0f, 0f, 0f), 1.0f);
        } else if (points.length < 2) {
            return new Ball(points[0], 1.0f);
        }

        Ball bball = new Ball(points[0], points[1]);

        for (int n1 = 2; n1 < points.length; n1++) {
            if (!bball.isInside(points[n1])) {
                Vec3d p1 = points[n1];
                bball = new Ball(p1, points[0]);

                for (int n2 = 1; n2 < n1; n2++) {
                    if (!bball.isInside(points[n2])) {
                        Vec3d p2 = points[n2];
                        bball = new Ball(p1, p2);

                        for (int n3 = 0; n3 < n2; n3++) {
                            if (!bball.isInside(points[n3])) {
                                Vec3d p3 = points[n3];
                                bball = new Ball(p1, p2, p3);

                                for (int n4 = 0; n4 < n3; n4++) {
                                    if (!bball.isInside(points[n4])) {
                                        Vec3d p4 = points[n4];
                                        bball = new Ball(p1, p2, p3, p4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bball;
    }

    private static Ball initialBoundingBall(Vec3d[] points) {
        Vec3d[] extremePoints = new Vec3d[6];

        for (int i = 0; i < 6; i++) {
            extremePoints[i] = points[0];
        }

        for (int i = 1; i < points.length; i++) {
            if (points[i].c[0] > extremePoints[0].c[0]) {
                extremePoints[0] = points[i];
            } else if (points[i].c[0] < extremePoints[1].c[0]) {
                extremePoints[1] = points[i];
            }

            if (points[i].c[1] > extremePoints[2].c[1]) {
                extremePoints[2] = points[i];
            } else if (points[i].c[1] < extremePoints[3].c[1]) {
                extremePoints[3] = points[i];
            }

            if (points[i].c[2] > extremePoints[4].c[2]) {
                extremePoints[4] = points[i];
            } else if (points[i].c[2] < extremePoints[5].c[2]) {
                extremePoints[5] = points[i];
            }
        }

        return boundingBall(extremePoints);
    }

    public static Ball fastBoundingBall(Vec3d[] points) {
        if (points.length < 1) {
            return new Ball(new Vec3d(0, 0, 0), 1.0f);
        }

        Ball bball = initialBoundingBall(points);

        for (int i = 0; i < points.length; i++) {
            bball.addPointInside(points[i]);
        }

        return bball;
    }

//    public static void main(String[] args) {
//        Vec3d[] points = new Vec3d[10];
//        for (int i = 0; i < points.length; i++) {
//            points[i] = new Vec3d((float) Math.random() * 10,
//                    (float) Math.random() * 10,
//                    (float) Math.random() * 10);
//        }
//
//        Ball ball = Ball.boundingBall(points);
//
//        Ball bball = new Ball(new Vec3d(1f, 1f, 1f),
//                new Vec3d(-1f, 1f, 1f),
//                new Vec3d(1f, -1f, 1f),
//                new Vec3d(1f, 1f, -1f));
//
//        System.out.println("ball.center: " + ball.getCenter());
//        System.out.println("ball.radius: " + ball.getRadius());
//    }
}
