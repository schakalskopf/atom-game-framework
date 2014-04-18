package sg.atom.utils.proxy.presentation.pface;

/**
 * This project was created after watching <a hred="http://bit.ly/5gWWXM">The google IO 'Go' talk</a>
 * <p>
 * Although dynamic interfaces offer an ease of development, it could be argued that statically typed languages may be better suited to
 * finding bugs at compile time rather than run time.
 * </p>
 * <p>
 * Never-the-less, it can be quite verbose and cumbersome sometimes to knock up wrappers/adapters for classes, especially when rapid
 * prototyping.
 * </p>
 * <p>
 * For that reason, this project was created to provide a simple way to treat an object as if it implements an interface:
 * </p>
 * 
 * <pre>
 * interface SomeInterface { 
 *    public void doit(); 
 * }
 * 
 * class MyClass { 
 *     public void doit() { ... }; 
 * } MyClass obj = ...; 
 * 
 * //treat 'MyClass' as if it implements 'SomeInterface' 
 * SomeInterface si = PFace.as(SomeInterface.class, obj); 
 * si.doit();
 * </pre>
 * <p>
 * Should an interface method be called which does not match a corresponding method on the object, then an exception is thrown.
 * </p>
 */
