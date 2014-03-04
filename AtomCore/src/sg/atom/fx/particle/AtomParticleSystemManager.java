/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.particle;

/**
 * A Manager specific for Particles activities. The same concept usually called
 * ParticleSystem in other game engines.
 *
 * <p>In Atom framework, its work as a Manager, who carefully designed to take
 * care of numberous of particles( as its entities) in following area: LOD,
 * bandwidth compressing, load-balance, events, cache, indexing, partitioning,
 * ... etc you name it :). As a Manager, it helps WorldManager and StageManager
 * for manage AtomParticle and AtomParticleEffects. It also offer a hook to
 * render operation to blend particle in some custom way (soft, volumetric,
 * ..etc)</p>
 *
 * <p>This is the prototype of Spatial partitioning in the World package.</p>
 *
 * @author CuongNguyen
 */
public class AtomParticleSystemManager {
}
