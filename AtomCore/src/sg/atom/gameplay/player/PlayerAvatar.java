/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.player;

/**
 * Avatar (Common implementation) is usually seen as a gamer's representor
 * (lightweight) in the game community.
 *
 * @author atomix
 */
public class PlayerAvatar {

    private String title;
    private String image;
    private String id;
    private String avatarName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }
    
    
}
