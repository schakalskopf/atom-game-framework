/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.function;

import com.jme3.collision.CollisionResults;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import sg.atom.stage.SelectManager;
import sg.atom.stage.select.SelectFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SwitchableSelectFunction extends SelectFunction {

    int lastX, lastY, currentX, currentY;
    protected boolean isPressed = false;
    protected boolean isDragged = false;
    protected boolean isMulti = false;
    protected boolean isMultiSelecting = false;
    protected boolean automaticSwitch = true;
    SelectFunction singleSelectFunc;
    SelectFunction multiSelectFunction;
    private boolean canSelectMulti = true;
    private boolean canSelectSingle = true;
    private MouseButtonEvent savedEvt;

    public SwitchableSelectFunction(SelectManager manager) {
        super(manager);
    }

    @Override
    public void init() {
        singleSelectFunc = new SingleSelectFunction(selectManager);
        multiSelectFunction = new RectangeSelectFunction(selectManager);
        multiSelectFunction.init();
    }

    public void init(SelectFunction singleSelectFunc,
            SelectFunction multiSelectFunction) {
        this.singleSelectFunc = singleSelectFunc;
        this.multiSelectFunction = multiSelectFunction;
    }

    @Override
    public void funcSelect() {
    }

    @Override
    public void mouseButton(MouseButtonEvent evt) {
        // Decide behavior?
        if (evt.getButtonIndex() == firstButtonIndex) {
            savedEvt = evt;
            if (evt.isPressed()) {
                //System.out.println(" Is hold !");
                // dragging for 400ms -> true
                isPressed = true;
                lastX = evt.getX();
                lastY = evt.getY();
            } else {
                isPressed = false;
                isDragged = false;
            }
            redispatchEvent();
        }

    }

    void redispatchEvent() {
        if (savedEvt != null) {
            if (isMultiSelecting) {
                //System.out.println("Multi selecting");
                multiSelectFunction.mouseButton(savedEvt);
            } else {
                singleSelectFunc.mouseButton(savedEvt);
            }
        }
    }

    @Override
    public void mouseMove(MouseMotionEvent evt) {
        // Decide behavior?
        if (isPressed) {
            currentX = evt.getX();
            currentY = evt.getY();
            if (lastX != evt.getX() || lastY != evt.getY()) {
                isDragged = true;
            }
        }
        //System.out.println("" + isDragged);
        // Decide which function that behavior map to?
        if (isDragged) {
            isMultiSelecting = true;
            //System.out.println("Multi selecting !");
            redispatchEvent();
        } else {
            isMultiSelecting = false;
        }

        // Decide what to do?
        if (isMultiSelecting) {
            multiSelectFunction.mouseMove(evt);
        } else {
            singleSelectFunc.mouseMove(evt);
        }
    }

    public void setSingleSelectFunc(SelectFunction singleSelectFunc) {
        this.singleSelectFunc = singleSelectFunc;
    }

    public void setMultiSelectFunction(SelectFunction multiSelectFunction) {
        this.multiSelectFunction = multiSelectFunction;
    }

    public SelectFunction getSingleSelectFunc() {
        return singleSelectFunc;
    }

    public SelectFunction getMultiSelectFunction() {
        return multiSelectFunction;
    }
}
