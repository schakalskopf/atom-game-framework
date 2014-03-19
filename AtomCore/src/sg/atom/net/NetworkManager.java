package sg.atom.net;

/**
 * NetworkManager . Represent the manager for all Network activites.
 *
 * <p>The network architect of Atom depend on MirrorMonkey and also can provide
 * hook in other such as Kryonet, ThreeRings, DarkStar, Arrianne... The multiple
 * approaches because of in Author's oppinion, currently there is no complete
 * Java networking solution at the momment, due to the complexity and the scale
 * in production enviroment of various game genres.
 *
 * <p>Use protocolbuff (default), kryonet, netty, spidermonkey, mirrormonkey to
 * send messages over network.
 *
 * <p>The Atom Network heavily use Google Guice and protocolbuff, its architect
 * and godness to inject dependencies on run-time.
 *
 * <p>Atom Network also provide ultilies for user to Config, Cache, Monitor,
 * Optimize, Balance network activies. Which are very handy in various case.
 *
 * <p>If user don't want to use the built-in util but use the specific
 * underliying framework directly, they can do it manually and accordingly.
 *
 * @author atomix
 */
public class NetworkManager {
}
