/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.proxy.presentation;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import java.util.Map;
import sg.atom.utils.proxy.IPresenter;

/**
 * IProjection can be use as a function which map the object to its presenter
 * (presentation_) with a View (or context). is a bridge between the visual
 * aspect and the abstract aspect of the programming language such as Java.
 *
 * <p>Projection "extends" Map because its contracted to involve View in the
 * mapping process. It's quite similar to Transformation (Transformer),
 * Convertion (Convertor) or alike but the View is contracted to reify (return)
 * the View to an object! Also, the Result of the whole processing is a Map. I
 * does NOT extends Map directly because the Size and containment is unknown
 * until the project() method not called (may be even after that). The View can
 * also be the Map or other type of Collections (structure) that cause the
 * projection, it can also be a point in space and a direction like a camera, or
 * a collapsed Node of serveral nodes in a tree... (who know?).
 *
 * <p>Projection also extends Function because of View but also its procedure a
 * processing result (the Map) and it also descriptive, and expose infos about
 * the View (not the input). It can also return a converter if it capable for
 * "zero" mapping. So call: A -> B : {0} -> f{0} is supported!
 *
 * <p><b>Note : </b>This specific function with 2 parameters (one optional) can
 * NOT be expressed if not extend Guava functional capacilities, because
 * Converter is an abstract class, not an interface! Also remember Projection is
 * tend to be reused. Generate thousand of Projections is not a wise move in
 * almost every application.
 *
 * <h4>Usage : </h4>This going to be used intensively between layers in
 * AtomFramework corporate beside of Morph for example : <ul>
 *
 * <li>Spatial, project to Transform, project to Points, project to RTree Node.
 *
 * <li>Object between Level of detail is casted (project) to simplier form. The
 * DefaultLODManager save the presentation of the Objects to serve.
 *
 * </ul>
 *
 * @author cuong.nguyenmanh2
 */
public interface IProjection<T> extends Function<T, IPresenter<T>> {

    IPresenter<T> project(T object, Object view);

    Map<T, IPresenter<T>> getMap();

    Converter<T, IPresenter<T>> getConverter();

    Object getView();
}
