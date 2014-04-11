/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.tween;

/**
 * Interpolate between two material by interpolate their parameters or by
 * modified shaders.
 *
 * <p>Two materials with a same matdef can be interpolate by params
 *
 * <p>Two materials with different matdef but use Codegen GLSL (going to support
 * official ShaderNode too) to generate "Bridge" can be apply transistion via
 * ShaderBridge.
 *
 * <p>Another choise is a convultion/transistion filter which can be injected
 * into fragment shader.
 *
 * <p>Some actions are actually going in shaders: Texture blending is in
 * fragment shader: diffuse, normal, lighting...etc. Some attributes like
 * additional blendstate can be change between transistion but will be return
 * after finish.
 *
 * @author CuongNguyen
 */
public class MaterialInterpolator {
}
