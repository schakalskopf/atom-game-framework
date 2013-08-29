package sg.atom.script.engine.groovy.gamecore

def onGameStart(me,str){
    println "Hello $me, $str $name!"
}

def onUpdate(){
    number++;
    println "Update"+number
}

def onGameQuit(){
    println "Game Quit"
}