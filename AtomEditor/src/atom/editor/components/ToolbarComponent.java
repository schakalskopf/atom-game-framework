/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.editor.components;

import atom.editor.EditorGUIManager;
import atom.editor.controls.button.ExButtonBuilder;
import atom.managex.action.AtomFunction;
import atom.managex.action.FunctionSystem;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ToolbarComponent extends ExNiftyComponent {

    Element lblStatus;
    private FunctionSystem functionSystem;
    private String defaultIconPath;

    public ToolbarComponent(EditorGUIManager guiManager, FunctionSystem functionSystem) {
        super("toolbar",guiManager);
        this.functionSystem = functionSystem;
        defaultIconPath = guiManager.getDefaultIconPath();
    }

    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        lblStatus = nScreen.findElementByName("lblStatus");
    }

    @Override
    public Element build(Nifty nifty, Screen screen, Element parent) {
        return createToolbar().build(nifty, screen, parent);
    }

    public PanelBuilder createToolbar() {
        this.nScreen = guiManager.getNiftyScreen();
        return new PanelBuilder() {
            {
                id("Toolbar");
                padding("4px");
                childLayoutHorizontal();
                height(percentage(100));
                width(percentage(100));
                valignBottom();
                backgroundColor("#ccc8");
                for (final AtomFunction function : functionSystem.getFunctions()) {

                    control(new ExButtonBuilder("btn" + function.getName(), function.getName()) {
                        {

                            if (function.getIcon().endsWith(".png")) {
                                icon(function.getIcon());
                                iconImageMode("normal");
                            } else {
                                icon(defaultIconPath);
                                iconImageMode(function.getIcon());
                            }
                            onHoverEffect(new HoverEffectBuilder("hint") {
                                {
                                    //parameter("name", "hint");
                                    parameter("hintText", function.getName());
                                    //set("hintText", function.getName());

                                }
                            });
                            interactOnClick("doFunction(" + function.getName() + ")");
                        }
                    });

                }
                text(new TextBuilder("lblStatus") {
                    {
                        width("80%");
                        style("base-font");
                        text("Status");
                    }
                });
            }
        };
    }

    public void setStatusText(String text) {
        lblStatus.getRenderer(TextRenderer.class).setText(" " + text);
    }

    
}
