package sg.atom.net;

/**
 *
@author atomix
 */
/*
 * NetworkManager . Represent the manager for all Network activites.
 * 
 * The network architect of Atom depend on MirrorMonkey and also can provide hook 
 * in other such as Kryonet, ThreeRings, DarkStar, Arrianne... The multiple approaches 
 * because of in Author's oppinion, currently there is no complete Java networking solution at the momment, 
 * due to  the complexity and the scale in production enviroment of various game genres.
 * 
 * The Atom Network heavily use Google Guice, its architect and godness to inject dependencies on run-time.
 * 
 * Atom Network also provide ultilies for user to Config, Cache, Monitor, Optimize, Balance network activies. Which are very handy in various case.
 * 
 * If user don't want to use the built-in util but use the specific underliying framework directly, they can do it manually and accordingly.
 */
public class NetworkManager {
}
