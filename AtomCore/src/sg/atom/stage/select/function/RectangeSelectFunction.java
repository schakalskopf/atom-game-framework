/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.function;

import com.jme3.collision.CollisionResults;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import sg.atom.entity.SpatialEntity;
import sg.atom.stage.SelectManager;
import sg.atom.stage.select.SelectFunction;
import sg.atom.stage.select.SelectUI;
import sg.atom.stage.select.condition.PiramidSelectCondition;
import sg.atom.stage.select.ui.SelectRectUI;

/**
 *
@author atomix
 */
public class RectangeSelectFunction extends SelectFunction {

    int lastX, lastY, currentX, currentY;
    protected boolean isPressed = false;
    protected boolean isDragged = false;

    @Override
    public void createSelectUI() {
        selectUI = new SelectRectUI(selectManager.getGameGUIManager());
        selectManager.getGameGUIManager().getGuiNode().attachChild(selectUI);
    }

    @Override
    public void init() {
        createSelectUI();
    }

    public RectangeSelectFunction(SelectManager manager) {
        super(manager);
    }

    protected void updateSelectRectUI() {
        SelectRectUI selectRectUI = (SelectRectUI) selectUI;
        if (isDragged) {
            if (!selectRectUI.dragging) {
                selectRectUI.setHoldPoint(lastX, lastY);
                selectRectUI.dragging = true;
            }
            selectRectUI.setDynamicPoint(currentX, currentY);
            selectRectUI.update();
        }

    }

    protected void showSelectRectUI() {
        SelectRectUI selectRectUI = (SelectRectUI) selectUI;
        //System.out.println("Show it!");
        if (!selectRectUI.isVisible()) {
            selectRectUI.setVisible(true);
        }
    }

    protected void hideSelectRectUI() {
        SelectRectUI selectRectUI = (SelectRectUI) selectUI;
        if (selectRectUI.isVisible()) {
            selectRectUI.setVisible(false);
            selectRectUI.dragging = false;
            selectRectUI.update();
        }
    }

    @Override
    public void mouseMove(MouseMotionEvent evt) {

        if (isPressed) {

            currentX = evt.getX();
            currentY = evt.getY();

            if (lastX != evt.getX() || lastY != evt.getY()) {
                isDragged = true;
                showSelectRectUI();
                updateSelectRectUI();
            } else {
                //isDragged = false;
            }

        }


    }

    @Override
    public void mouseButton(MouseButtonEvent evt) {
        //System.out.println("Pressed it!");
        if (evt.getButtonIndex() == firstButtonIndex) {
            if (evt.isPressed()) {
                //System.out.println(" Is hold !");
                // dragging for 400ms -> true
                isPressed = true;
                lastX = evt.getX();
                lastY = evt.getY();
            } else {
                isPressed = false;
                isDragged = false;
                funcSelect();
                hideSelectRectUI();
            }
        }
    }

    /**
     * This function drive the basic mechanic of selecting Multi objects. It
     * deselect the selected objects and the check if the Condition meet
     */
    @Override
    public void funcSelect() {
        CollisionResults results = selectManager.getCurrentResults();

        switch (selectManager.getSelectOperationType()) {
            case Normal:
                selectManager.deselectAll();
                break;
            case Substract:
                Add:
                Union:
                break;
        }
        
        Camera cam = selectManager.getStageManager().getCamera();
        int count = 0;
        // create a debug mesh
        Node rootNode = selectManager.getStageManager().getWorldManager().getRootNode();
        PiramidSelectCondition selectCondition = new PiramidSelectCondition(cam, getSelectRectUI());

        for (SpatialEntity entity : selectManager.getEntityManager().getAllSpatialEntities()) {
            if (entity.getSpatial() != null) { // have to be a visual Entity!
                if (selectCondition.isSelected(entity.getSpatial())) {
                    if (selectManager.getEntitySelectCondition() != null) {
                        if (selectManager.getEntitySelectCondition().isSelected(entity)) {
                            if (!selectManager.getCurrentSelection().contains(entity)) {
                                selectManager.getCurrentSelection().add(entity);
                                markAsSelect(entity);
                            }
                        }
                    } else {
                        if (!selectManager.getCurrentSelection().contains(entity)) {
                            selectManager.getCurrentSelection().add(entity);
                            markAsSelect(entity);
                        }
                    }
                    // Name it
                    System.out.println(" Select " + entity.getName());
                }

            }

        }
        notifySelectionChange();
        //System.out.println(" COUNT :" + count);

    }

    private SelectRectUI getSelectRectUI() {
        return (SelectRectUI) selectUI;
    }
}
