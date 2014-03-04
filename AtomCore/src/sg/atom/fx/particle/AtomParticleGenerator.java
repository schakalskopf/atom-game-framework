/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.particle;

/**
 * AtomParticleGenerator is who generate (mean create) AtomParticles from its
 * internal attributes.
 *
 * <p><b>Note:</b>The different between ParticleGenerator and ParticleFactory is
 * not quite well defined. Factory usually generate objects by parmaters.
 * Generator generate objects also by parameter but the internal state of the
 * generator, so as in Java language, Generator have less "static" methods than
 * Factory. Generator also contracted to handle its managed Particles' lifespan
 * long its own lifespan. This is a feature to distinguish Generator with
 * Infulencer, who not commit to manage Particles and ParticleSystem who manage
 * particle the whole time.</p>
 *
 * <p>AtomParticleGenerator index its generated entries in a form, analogue to
 * how ParticleSystem manage them. Because the indexing will be embed
 * together.</p>
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomParticleGenerator {
}
