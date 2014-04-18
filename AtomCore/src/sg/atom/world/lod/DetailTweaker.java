/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

/**
 * Detail tweaker is used for simplification or complexification (complicating)
 * of objects (or meshs). In normal English, that mean make the object simplier
 * or more complex in appearance or behaviours.
 *
 * <p>The most simple way to see it as a 2 way convertor of one object
 * (preserved the topoligical type).
 *
 * <h3>How tweaking works: </h3> <h4>Reducing</h4> Let's take a look at how
 * reducing actually work in low level.
 *
 * <p>Reducing a java object: Convert java object to a simplier form. First
 * ideas come to mind is to serialize or hash it. Those two method collapse the
 * properties of the object together and reduce the info that object contains;
 * in turn decrease memory... The same result can also be achieved with methods
 * like compression. The problem is: how can an object that compressed return to
 * its "original form" but "lossless" and "like unchanged" and do "lossless"
 * even nessessary? This is an interesting question... and have no universal
 * answer for it of course!
 *
 * <p>So approaches in 3D application in the past, usually saved serveral
 * "static form " versions of the object which in serveral different levels of
 * detail and then switch or intepolate between them. This static methods
 * require more manual jobs but it's de facto of the industry till today.
 *
 * <p>Other methods "automatic" and "progressive" the LOD tasks and data in
 * various aspects and degree. Let's say we can generate the simplier mesh on
 * the fly.
 *
 * <h4>Increasing or upgrading</h4> In opposite to the above process,
 *
 * @author cuong.nguyenmanh2
 */
public interface DetailTweaker<T> {
}
