/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.atom.managex.helpers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author hungcuong
 */
public abstract class AbstractHelper extends AbstractControl{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    AbstractHelper(String name){
        this.name = name;

    }
    public abstract void initHelper();

    public abstract Node getSubNode();
    
    public abstract SpatialList getSelectableList();
    
    public boolean isHelperNode(Spatial sp){
        return getSubNode().hasChild(sp);
    }
    
    

}
                                                                                                                                                                         l a s s 0�    � �     Ђ   	 b��B��oפͻ�oפͻ�b��B��       p              #R o a d E d i t o r D e m o $ S l i d e r L i s t e n e r . c l a s s 3�   	 � j     Ђ   	 �k�B����ͻ���ͻ��k�B�� 0      �%              R o a d E d i t o r D e m o . c l a s s Z     +�   	 p Z     Ђ   	 2��B��+;�ͻ�+;�ͻ�2��B��                     R O A D E D ~ 1 . C L A       ,�    p Z     Ђ   	 2��B��<b�ͻ�<b�ͻ�2��B��                     R O A D E D ~ 2 . C L A      -�   	 p Z     Ђ   	 RύB��<b�ͻ�<b�ͻ�RύB��       Y              R O A D E D ~ 3 . C L A       .�    p Z     Ђ   	 RύB��M��ͻ�M��ͻ�RύB��       _              R O A D E D ~ 4 . C L A       2�    p Z     Ђ   	 r�B���s�ͻ��s�ͻ�r�B��                     R O C 2 0 D ~ 1 . C L A       1�    p Z     Ђ   	 r�B���L�ͻ��L�ͻ�r�B��       �              R O D 6 0 F ~ 1 . C L A       @�    p Z     Ђ   	 �B���B���B���B�� 0     �&              S A 6 B 1 A ~ 1 . C L A w D e ?�    p Z     Ђ   	 �ƑB���ƑB���ƑB���ƑB��       �              S A C 5 8 C ~ 1 . C L A w D e 8�    � t     Ђ   	 �x�B���x�B���x�B���x�B��       �              S a t e l l i t e V i e w D e m o $ 1 . c l a s s     9�    � t     Ђ   	 �x�B��⟑B��⟑B���x�B��       �              S a t e l l i t e V i e w D e m o $ 2 . c l a s s     :�    � t     