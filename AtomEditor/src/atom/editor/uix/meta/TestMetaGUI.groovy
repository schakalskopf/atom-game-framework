package atom.editor.uix.meta

import java.awt.Image
/**
 *
 * @author cuong.nguyenmanh2
 */
/*
class TestObj{
String name ="Yu"
int age = 10
float height = 8.6
String job="Artist"
String company="Gameloft"
Image image
}


def listObj =[]
 */

class User {
    String name
    String city
    Date birthDate
    float height
    Image image
    String job
    public String toString() { "$name" }
}
def users = [
    new User(name:'mrhaki', city:'Tilburg',height:4, job:"Artist", birthDate:new Date(73,9,7)),
    new User(name:'bob', city:'New York',height:9, job:"Artist", birthDate:new Date(63,3,30)),
    new User(name:'britt', city:'Amsterdam',height:4, job:"GD", birthDate:new Date(80,5,12)),
    new User(name:'kim', city:'Amsterdam',height:4, job:"Dev", birthDate:new Date(83,3,30)),
    new User(name:'liam', city:'Tilburg',height:5, job:"GD", birthDate:new Date(109,3,6))
]

GUIGenerator gg=new GUIGenerator()
gg.instrospector = new Introspector(transform:{
        new MetaType(type:it.type,data:it)
    })
/*
gg.factory = new GUIFactory()
gg.gen(users)
gg.nifty()
*/
def typeList = gg.instrospector.intropect(users[0])
/*
typeList.each{metaType->
    println metaType.type
}
*/