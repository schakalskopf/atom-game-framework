package sg.atom.editor.ui.nifty.controls.button;

import de.lessvoid.nifty.builder.ControlBuilder;

public class ExButtonBuilder extends ControlBuilder {

    public ExButtonBuilder(final String id) {
        super(id, "ex-button");
    }

    public ExButtonBuilder(final String id, final String buttonLabel) {
        super(id, "ex-button");
        label(buttonLabel);
        iconImageMode("normal");
    }

    public ExButtonBuilder(final String id, final String buttonLabel, final String buttonIcon) {
        super(id, "ex-button");
        label(buttonLabel);
        icon(buttonIcon);
        iconImageMode("normal");
    }

    public ExButtonBuilder(final String id, final String buttonLabel, final String buttonIcon, final String buttonImageMode) {
        super(id, "ex-button");
        label(buttonLabel);
        icon(buttonIcon);
        iconImageMode(buttonImageMode);
    }

    public void label(final String label) {
        set("label", label);
    }

    public void icon(final String icon) {
        set("icon", icon);
    }

    public void iconImageMode(final String iconImageMode) {
        set("iconImageMode", iconImageMode);
    }
}
