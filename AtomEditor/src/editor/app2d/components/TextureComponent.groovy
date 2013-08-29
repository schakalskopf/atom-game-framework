package editor.app2d.components

/**
 *
 * @author cuong.nguyenmanh2
 */
/*
class TextureComponent {
    controlsPanel = panel(title:"Controls"){
        gridBagLayout()
        bd = BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.black);
                        
        panel(constraints:gbc(gridx:0,gridy:0,gridwidth:1,gridheight:1, fill:GridBagConstraints.BOTH,weightx:1, weighty:1),border:bd){
            borderLayout()
                            
            def openTextureDialog  = fileChooser(dialogTitle:"Choose an excel file", 
                id:"openTextureDialog", 
                fileSelectionMode : JFileChooser.FILES_ONLY, 
                //the file filter must show also directories, in order to be able to look into them
                fileFilter: [getDescription: {-> "*.png"}, 
                    accept: {file-> file ==~ /.*?\.png/ || file.isDirectory() }] as FileFilter)

            //later, in the controller
            def fc = openTextureDialog

            button(label:" Change Texture " ,
                constraints:BL.CENTER, 
                actionPerformed:{
                    if(fc.showOpenDialog() != JFileChooser.APPROVE_OPTION) {
                        return //user cancelled
                    } else {
                        changeTexture(fc.selectedFile)
                    }
                                    
                }
            )
                                
        }
                        
        (1..5).each{i->
            //ct = new GridBagConstraints(0, i, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0), 0, 0)
            panel(constraints:gbc(gridx:0,gridy:i,gridwidth:1,gridheight:1, fill:GridBagConstraints.BOTH,weightx:1, weighty:1),border:bd){
                borderLayout()
                //label(text:""+i)
                button(label:" button " + i,constraints:BL.CENTER)
                                
            }
        }
        //tree(constraints:BL.NORTH)    
    }
}


// This function is for changing the effect texture
// change the effect texture
// broswer the texture file
// set it
def changeTexture(file){
    if (app.changeTexture(file)==null){
        println "No texture found!"
    } else {
        println "Changed Texture !"
        previewPanel.gb.bg.file = file.absolutePath
        previewPanel.gb.bg.classpath = ""
        previewPanel.gb.rect.fill = Color.red
        //previewPanel.graphicsOperation = 
    }
}
*/