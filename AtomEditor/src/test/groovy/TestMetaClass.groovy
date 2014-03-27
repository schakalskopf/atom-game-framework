package test.groovy

import sg.atom.uix.nifty.controller.ClosureScreenController
/**
 *
 * @author CuongNguyen
 */
def methodName = "Bob"
/*
class Person {
   String name = "Fred"
}



Person.metaClass."changeNameTo${methodName}" = {-> delegate.name = "Bob" }

def p = new Person()

assert "Fred" == p.name

p.changeNameToBob()

assert "Bob" == p.name
*/

def sc = new ClosureScreenController()
sc.metaClass."${methodName}" = {println "Hello"}
sc.Bob()