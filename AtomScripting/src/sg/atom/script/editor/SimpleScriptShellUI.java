package sg.atom.script.editor;

import java.util.LinkedList;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author hungcuong
 */
public class SimpleScriptShellUI {

    protected ScriptEngineManager factory;
    protected ScriptEngine genericScriptEngine;
    protected String headerLine;
    protected LinkedList<ScriptString> codeLines = new LinkedList<ScriptString>();
    protected LinkedList<ScriptString> resultLines = new LinkedList<ScriptString>();
    protected ScriptEngine shell;

    public SimpleScriptShellUI(ScriptEngine shell) {
        this.shell = shell;
    }

    public Object eval(String line) {
        try {
            codeLines.add(new ScriptString("Code", line));
            //shell.parse(line);


            if (!isEngineReady()) {
                resultLines.add(new ScriptString("Exception", "Engine not ready"));
                return null;
            }

            Object result = shell.eval(line);
            if (result != null) {
                resultLines.add(new ScriptString("Result", result.toString()));
                return result;
            } else {
                resultLines.add(new ScriptString("null", ""));
                return null;
            }
        } catch (CompilationFailedException ex) {
            resultLines.add(new ScriptString("Syntax", line + ex.getMessage()));
            return null;
        } catch (Exception ex) {
            resultLines.add(new ScriptString("Exception", ex.getMessage()));
            return null;
        }
    }

    public String getAllCodeLines() {
        String allCodeLine = "";

        for (int i = 0; i < resultLines.size(); i++) {

            String line, line2;

            if (codeLines.get(i) == null) {
                line = "";
            } else {
                line = codeLines.get(i).colorString();
            }

            if (resultLines.get(i) == null) {
                line2 = "";
            } else {
                line2 = resultLines.get(i).colorString();
            }

            String add = "[Code Line " + (i + 1) + " ] <BR>" + line + " <BR>" + line2 + "<BR>";

            allCodeLine = allCodeLine + add;
            Logger.getLogger("ScriptEngine").info(">>>" + add);
        }
        allCodeLine = "<html>" + allCodeLine + "</html>";
        return allCodeLine;
    }

    private boolean isEngineReady() {
        return (shell == null) ? false : true;
    }

    private static class ScriptString {

        String type;
        String value;

        public ScriptString(String type, String value) {
            this.type = type;
            this.value = value;
        }

        String colorString() {
            String color = "";
            if (type.equals("Syntax")) {
                color = "<b color='red'>  Syntax error : </b>" + value;
            } else if (type.equals("Exception")) {
                color = "<b color='red'>  Exception : </b>" + value;
            } else if (type.equals("Result")) {
                color = "<font color=blue>" + value + "</font>";
            } else if (type.equals("Code")) {
                color = "<b color='GREEN'>" + value + "</b>";
            }
            return color;
        }
    }
}
