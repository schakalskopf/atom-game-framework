/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package script.groovy.snippets

import java.util.regex.Pattern
/**
 *
 * @author CuongNguyen
 */
path = "E:/JGE/OTHER MODULE/3D JME3 expansion/Geometry/Constraint/code/geosolver"
newDirName= path + "/g/"

def mainRun(dirName){
    makeResultDir()
    new File(dirName).eachFile() { file -> 
        if (file.getName() =~ /.py/){
            
            def code = processPythonCode(file.text)
            writeGroovyFile(file,code)

            //println file.getName() + " -> " + f.getName() 
            //println file.getName() + " -> " + newName
        }
    } 

}
def readPythonFile(file){
    return file.text
}
def makeResultDir(){
     
    File newDir = new File(newDirName)
    newDir.mkdirs()
}
def writeGroovyFile(file,text){
    def newName = file.getName().replace(".py",".groovy")
    newName = newDirName + newName
    File newFile = new File(newName)
    newFile.createNewFile()
    //copy(file,newFile)
    newFile.write(text.toString())
    
}
def copy(File src,File dest){
 
    def input = src.newInputStream()
    def output = dest.newOutputStream()
 
    output << input
 
    input.close()
    output.close()
}
/* A simple line by line Python parser and Py2Groovy converter */
// Current context of reading
currentClass = ""
currentContext = ""
currentIndent = 0
currentBlock = 0
currentType = ""
currentPackage = ""

//Matcher
C_COMMENT_START = ~/"""([^"]*)/
C_COMMENT_END = ~/(.*)"""/
C_SCOMMENT = ~/\s*#/
C_IF = ~/^if ([^:]*):/
C_ELIF = ~/elif ([^:]*):/
C_EL = ~/else\b?:/
C_INDENT = ~/^(\s*)/
C_CLASS = ~/^class (\w*) \(([\w\s,]*)\):/

//Transformation rules
rules =[]
//rules << [regex:C_COMMENT_START,temp:{m->"\\*${m[0][1]}"}]
//rules << [regex:C_COMMENT_END,temp:{m->"${m[0][1]}*/"},r:true]
rules << [regex:C_COMMENT_START,temp:["k":"\"\"\"","v":"/*"]]
rules << [regex:C_COMMENT_END,temp:["k":"\"\"\"","v":"*/"]]
rules << [regex:C_IF,temp:{m->"if (${m[0][1]}){"}]
rules << [regex:C_ELIF,temp:{m->"}else if (${m[0][1]}){"}]
rules << [regex:C_EL,temp:{m->"}else {"}]
rules << [regex:C_SCOMMENT,temp:"//"]
rules << [regex:C_CLASS,temp:{m->;"class ${m[0][1]} {"}]

//Keyword direct change rule
keywords=[:]
keywords["isinstance"]="instanceof"
keywords["self."]="this."

keywords.each{k,v->
    rules << [regex:k,temp:v]
}
def processPythonCode(text){
    //captureImportBlock(text)
    def gcode = new StringBuffer("")


    text.eachLine { line, count ->
        //println "line $count: $line"

        
        //println "number of tabs:" + indents[0][0].size()
        if (rules.any{ r->
                //println r.regex.class.name
                applyRule(line,r,gcode)
            }){
        
        } else {
            // passthrough
            gcode<<line<<"\n"
        }
        
        
    }
    //println "Result================================================="
    //println gcode
    gcode
}

def applyRule(line,rule,gcode){
    def code
    def m
    def indents = (line =~ C_INDENT)
    def ni=indents[0][0].size()
    
    if (rule.regex instanceof Pattern){
        m = (line =~ rule.regex)
    } else if (rule.regex instanceof Closure){
        m = rule.regex(line)
    } else {
        m = line.contains(rule.regex)
    }
    
    if (m){
        // Match a transform rule
        if (rule.temp instanceof Closure){
            code = rule.temp(m)
        } else if (rule.temp instanceof Map){
            code = line.replace(rule.temp["k"],rule.temp["v"])
        } else {
            code = line.replaceAll(rule.regex,rule.temp)
        }
        //println line + "=>" + code
        ni.times{gcode<< " "}
        gcode << code << "\n"
        return true
    } else {
        return false
    }
}

def captureBlock(){
    
}

def captureComment(){
    
}
def captureImport(){
    
}

mainRun(path)