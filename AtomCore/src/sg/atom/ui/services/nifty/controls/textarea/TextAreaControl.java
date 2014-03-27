package sg.atom.ui.services.nifty.controls.textarea;

import java.util.Properties;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.controls.FocusHandler;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.textfield.TextFieldView;
import de.lessvoid.nifty.controls.textfield.filter.delete.TextFieldDeleteFilter;
import de.lessvoid.nifty.controls.textfield.filter.input.TextFieldInputCharFilter;
import de.lessvoid.nifty.controls.textfield.filter.input.TextFieldInputCharSequenceFilter;
import de.lessvoid.nifty.controls.textfield.filter.input.TextFieldInputFilter;
import de.lessvoid.nifty.controls.textfield.format.TextFieldDisplayFormat;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.elements.tools.FontHelper;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;

/**
 * An editable text area control.
 *
 * @author void
 * @author Joakim Lindskog
 */
public class TextAreaControl extends AbstractController implements TextField, TextFieldView {

    private static final int CURSOR_Y = 0;
    private Nifty nifty;
    private Screen screen;
    private Element textElement;
    private Element fieldElement;
    private Element cursorElement;
    private TextAreaLogic textArea;
    private int firstVisibleCharacterIndex;
    private int lastVisibleCharacterIndex;
    private int fieldWidth;
    private int fromClickCursorPos;
    private int toClickCursorPos;
    private FocusHandler focusHandler;
    private Character passwordChar;

    public void bind(
            final Nifty niftyParam,
            final Screen screenParam,
            final Element newElement,
            final Properties properties,
            final Attributes controlDefinitionAttributes) {
        super.bind(newElement);

        this.nifty = niftyParam;
        this.screen = screenParam;
        this.fromClickCursorPos = -1;
        this.toClickCursorPos = -1;

        this.textArea = new TextAreaLogic(properties.getProperty("text", ""), nifty.getClipboard(), this);
        this.textArea.toFirstPosition();

        this.textElement = getElement().findElementByName("#text");
        this.fieldElement = getElement().findElementByName("#field");
        this.cursorElement = getElement().findElementByName("#cursor");

        passwordChar = null;
        if (properties.containsKey("passwordChar")) {
            passwordChar = properties.get("passwordChar").toString().charAt(0);
        }
        if (properties.containsKey("maxLength")) {
            setMaxLength(new Integer(properties.getProperty("maxLength")));
        }
    }

    @Override
    public void init(final Properties parameter, final Attributes controlDefinitionAttributes) {
        this.focusHandler = screen.getFocusHandler();

        this.textArea.initWithText(textElement.getRenderer(TextRenderer.class).getOriginalText());
        this.fieldWidth = this.fieldElement.getWidth() - this.cursorElement.getWidth();

        TextRenderer textRenderer = textElement.getRenderer(TextRenderer.class);
        this.firstVisibleCharacterIndex = 0;
        this.lastVisibleCharacterIndex = FontHelper.getVisibleCharactersFromStart(textRenderer.getFont(), this.textArea.getText(), fieldWidth, 1.0f);

        updateCursor();
        super.init(parameter, controlDefinitionAttributes);
    }

    public void onStartScreen() {
    }

    @Override
    public void layoutCallback() {
        this.fieldWidth = this.fieldElement.getWidth() - this.cursorElement.getWidth();
    }

    public void onClick(final int mouseX, final int mouseY) {
//Find out what row is clicked
        TextRenderer renderer = textElement.getRenderer(TextRenderer.class);
        String text = renderer.getWrappedText();
        int row = (mouseY - fieldElement.getY()) / renderer.getFont().getHeight();
        if (row > text.split("\n", -1).length - 1) {
            row = text.split("\n", -1).length - 1;
        }
        String[] split = text.split("\n", row + 2);
        String visibleString = /*editTextArea.getText()*/ split[row];//.substring(firstVisibleCharacterIndex, lastVisibleCharacterIndex);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < row; i++) {
            if (buf.length() == 0) {
                buf.append(split[i] + " ");
            } else {
                buf.append("\n" + split[i]);
            }
        }
        int indexFromPixel = getCursorPosFromMouse(mouseX, visibleString) + buf.length();
        if (indexFromPixel != -1) {
            fromClickCursorPos = firstVisibleCharacterIndex + indexFromPixel;
        }
        textArea.resetSelection();
        textArea.setCursorPosition(fromClickCursorPos);
        updateCursor();
    }

    public void onClickMouseMove(final int mouseX, final int mouseY) {
        //TODO: Fix NiftyRenderEngineImpl
        if (true) {
            return;
        }
        TextRenderer renderer = textElement.getRenderer(TextRenderer.class);
        String text = renderer.getWrappedText();
        int row = (mouseY - fieldElement.getY()) / renderer.getFont().getHeight();
        if (row > text.split("\n", -1).length - 1) {
            row = text.split("\n", -1).length - 1;
        }
        //System.out.println("wrapped text::: " + text);
        //System.out.println("row: " + row + " firstVisCharIdx: " + firstVisibleCharacterIndex);
        String[] split = text.split("n", row + 2);
        //System.out.println("text: " + split[row] + " split length: " + split.length);
//	String visibleString = textField.getText().substring(firstVisibleCharacterIndex, lastVisibleCharacterIndex);
        String visibleString = /*editTextArea.getText()*/ split[row];//.substring(firstVisibleCharacterIndex, lastVisibleCharacterIndex);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < row; i++) {
            if (buf.length() == 0) {
                buf.append(split[i] + " ");
            } else {
                buf.append("\n" + split[i]);
            }
        }

//    String visibleString = textArea.getText().substring(firstVisibleCharacterIndex, lastVisibleCharacterIndex);
        int indexFromPixel = getCursorPosFromMouse(mouseX, visibleString) + buf.length();
        if (indexFromPixel != -1) {
            toClickCursorPos = firstVisibleCharacterIndex + indexFromPixel;
        }

        //System.out.println("fromClickCursorPos " + fromClickCursorPos);
        textArea.setCursorPosition(fromClickCursorPos);
        textArea.startSelecting();
        //System.out.println("toClickCursorPos " + toClickCursorPos);
        textArea.setCursorPosition(toClickCursorPos);
        textArea.endSelecting();
        updateCursor();
    }

    private int getCursorPosFromMouse(final int mouseX, final String visibleString) {
        TextRenderer textRenderer = textElement.getRenderer(TextRenderer.class);
        return FontHelper.getCharacterIndexFromPixelPosition(
                textRenderer.getFont(), visibleString, (mouseX - fieldElement.getX()), 1.0f);
    }

    @Override
    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        if (inputEvent == NiftyInputEvent.MoveCursorLeft) {
            textArea.cursorLeft();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.MoveCursorRight) {
            textArea.cursorRight();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.MoveCursorUp) {
            textArea.cursorUp();
            updateCursor();
        } else if (inputEvent == NiftyInputEvent.MoveCursorDown) {
            textArea.cursorDown();
            updateCursor();
        } else if (inputEvent == NiftyInputEvent.Delete) {
            textArea.delete();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.Backspace) {
            textArea.backspace();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.MoveCursorToLastPosition) {
            textArea.toLastPosition();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.MoveCursorToFirstPosition) {
            textArea.toFirstPosition();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.SelectionStart) {
            textArea.startSelecting();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.SelectionEnd) {
            textArea.endSelecting();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.Cut) {
            textArea.cut(passwordChar);
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.Copy) {
            textArea.copy(passwordChar);
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.Paste) {
            textArea.put();
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.SelectAll) {
            if (textArea.getText().length() > 0) {
                textArea.setCursorPosition(0);
                textArea.startSelecting();
                textArea.setCursorPosition(textArea.getText().length());
                textArea.endSelecting();
                updateCursor();
                return true;
            }
        } else if (inputEvent == NiftyInputEvent.Character) {
            textArea.insert(inputEvent.getCharacter());
            updateCursor();
            return true;
        } else if (inputEvent == NiftyInputEvent.NextInputElement) {
            if (focusHandler != null) {
                focusHandler.getNext(fieldElement).setFocus();
                updateCursor();
                return true;
            }
        } else if (inputEvent == NiftyInputEvent.PrevInputElement) {
            textArea.endSelecting();
            if (focusHandler != null) {
                focusHandler.getPrev(fieldElement).setFocus();
                updateCursor();
                return true;
            }
        } else if (inputEvent == NiftyInputEvent.SubmitText) {
            textArea.insert('\n');
        }

        updateCursor();
        return false;
    }

    private void updateCursor() {
        TextRenderer textRenderer = textElement.getRenderer(TextRenderer.class);
        String text = textArea.getText();
        checkBounds(text, textRenderer);
        calcLastVisibleIndex(textRenderer);

// update text
        if (isPassword(passwordChar)) {
            int numChar = text.length();
            char[] chars = new char[numChar];
            for (int i = 0; i < numChar; ++i) {
                chars[i] = passwordChar;
            }
            text = new String(chars);
        }

        textRenderer.setText(text);
        textRenderer.setSelection(textArea.getSelectionStart(), textArea.getSelectionEnd());
// Lay out element to get correctly wrapped text
        getElement().getParent().layoutElements();

//Get the position of the cursor in the text
        int cursorPos = textArea.getCursorPosition();

// outside, move window to fit cursorPos inside [first,last]
//calcFirstVisibleIndex(cursorPos);
//calcLastVisibleIndex(textRenderer);

        String substring2 = text.substring(0, firstVisibleCharacterIndex);
        int d = textRenderer.getFont().getWidth(substring2);
        textRenderer.setXoffsetHack(-d);

// Get wrapped text
        String breakText = textRenderer.getWrappedText();
// Get the current line
        int currentLine = breakText.substring(0, cursorPos > 0 ? cursorPos : 0).split("\n", -1).length;

        int startIndex = breakText.lastIndexOf("\n") + 1;
        if (cursorPos - startIndex < 0) {
// Going up to previous line
            startIndex = breakText.substring(0, cursorPos).lastIndexOf("\n") + 1;
        }
        int textWidth = textRenderer.getFont().getWidth(breakText.substring(startIndex > -1 ? startIndex : 0,
                cursorPos > 0 ? cursorPos : 0));
        int cursorPixelPos = textWidth - d;

        cursorElement.setConstraintX(new SizeValue(cursorPixelPos + "px"));
        cursorElement.setConstraintY(new SizeValue((textRenderer.getFont().getHeight() * (currentLine - 1)) + CURSOR_Y + "px"));
        cursorElement.startEffect(EffectEventId.onActive, null);
        if (screen != null) {
            screen.layoutLayers();
        }
    }

    private boolean isPassword(final Character currentPasswordChar) {
        return currentPasswordChar != null;
    }

    private void calcFirstVisibleIndex(final int cursorPos) {
        if (cursorPos > lastVisibleCharacterIndex) {
            int cursorPosDelta = cursorPos - lastVisibleCharacterIndex;
            firstVisibleCharacterIndex += cursorPosDelta;
        } else if (cursorPos < firstVisibleCharacterIndex) {
            int cursorPosDelta = firstVisibleCharacterIndex - cursorPos;
            firstVisibleCharacterIndex -= cursorPosDelta;
        }
    }

    private void checkBounds(final String text, final TextRenderer textRenderer) {
        int textLen = text.length();
        if (firstVisibleCharacterIndex > textLen) {
// reposition so that we show as much text as possible
            lastVisibleCharacterIndex = textLen;
            firstVisibleCharacterIndex =
                    FontHelper.getVisibleCharactersFromEnd(textRenderer.getFont(), text, fieldWidth, 1.0f);
        }
    }

    private void calcLastVisibleIndex(final TextRenderer textRenderer) {
        String currentText = this.textArea.getText();
        if (firstVisibleCharacterIndex < currentText.length()) {
            String textToCheck = currentText.substring(firstVisibleCharacterIndex);
            int lengthFitting =
                    FontHelper.getVisibleCharactersFromStart(textRenderer.getFont(), textToCheck, fieldWidth, 1.0f);
            lastVisibleCharacterIndex = lengthFitting + firstVisibleCharacterIndex;
        } else {
            lastVisibleCharacterIndex = firstVisibleCharacterIndex;
        }
    }

    public void onFocus(final boolean getFocus) {
        if (cursorElement != null) {
            super.onFocus(getFocus);
            if (getFocus) {
                cursorElement.startEffect(EffectEventId.onCustom);
            } else {
                cursorElement.stopEffect(EffectEventId.onCustom);
            }
            updateCursor();
        }
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(final String newText) {
        textArea.initWithText(nifty.specialValuesReplace(newText));
        updateCursor();
    }

    public void setMaxLength(final int maxLength) {
        textArea.setMaxLength(maxLength);
        updateCursor();
    }

    public void setCursorPosition(final int position) {
        textArea.setCursorPosition(position);
        updateCursor();
    }

    @Override
    public void textChangeEvent(final String newText) {
        nifty.publishEvent(getElement().getId(), new TextFieldChangedEvent(this, newText));
    }

    @Override
    public void enablePasswordChar(final char passwordChar) {
        this.passwordChar = passwordChar;
        updateCursor();
    }

    @Override
    public void disablePasswordChar() {
        this.passwordChar = null;
        updateCursor();
    }

    @Override
    public boolean isPasswordCharEnabled() {
        return passwordChar != null;
    }

    public String getDisplayedText() {
        return textArea.getText();
    }

    public String getRealText() {
        return textArea.getText();
    }

    public void enableInputFilter(TextFieldInputFilter tfif) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableInputFilter(TextFieldInputCharFilter tficf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableInputFilter(TextFieldInputCharSequenceFilter tficsf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disableInputFilter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableDeleteFilter(TextFieldDeleteFilter tfdf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disableDeleteFilter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFormat(TextFieldDisplayFormat tfdf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setText(CharSequence cs) {
        setText(cs.toString());
    }
}
