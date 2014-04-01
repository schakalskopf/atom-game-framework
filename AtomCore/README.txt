ATOM CORE

AtomCore is the core of Atom framework for game developing in Java. Powered by JME3.

Atom framework use hundreds of other open source libraries, it towards the future of Java gaming with open source spirit!

================================================================================
Packages
---------------
sg.atom.core    	Core elements of the framework.
	actor			Concepts & Facilities for simple in JVM actor framework.
	annotations 	Annotations to setting up elements in java code. [Same in every packages!]
	assets 			Facilities to import / export assets from JME3 pipeline
	bean			Facilities to use Java bean in Atom context with mapping and binding.
	config			Facilities to use Configs in Atom, with the help of Common Configuration
	context			Bridge concepts help to bring entities from one enviroment to others. Cross platform 
	event			Concepts & Facilities for simple in JVM actor / messaging framework.
	execution		Facilities for execution, with help of Common lang and Guava
	lifecycle		Concepts for game (and real time application) cycle
	monitor			Facilities to monitor your game and application
	timing			Concepts & Facilities for real time application
	
	
sg.atom.entity		Concepts and Facilities to build up Game object. [Beta]

sg.atom.fx			Concepts and Facilities to create and manage animations and effects
	anim			Concepts for animation
	automatic 		Automatic driven for animation
	constraint		Other way to declare relationship between entities and activities
	filters			Additions to JME3 filters
	functional		Functional flavours for effects
	particles 		Concepts to build bigger system from smaller part [Atom concepts]
	sprite			Concepts for cross dimensional elements
	timeline		Enhance of timming framework
	transition		Transition between stateful objects 
	tween			Object interpolations.

sg.atom.gameplay	Concepts and facilities for games (cross-genre)
	action			Concepts and interfaces for action in games
	controls		Additions for JME3 character controls
	league			Leagues  group and tournament of players
	managers		Manager of leagues  group and tournament of players
	player			Player and their data
	replay			To record the game activities
	score			To recored the game results
	
sg.atom.logic		Basic block for building game from a programming language via formal system.

sg.atom.net			Concepts and interfaces for connectivity and communication via networks

sg.atom.stage		Concepts and facilities for cinematography like games
	actor			Bridge from entities to actor framework!
	cine			Sostiphicate cinematic framework for complex video games
	helpers			"Inplace" controls which know about Stage. Bridge from JME3 Controls concepts
	input			Sophisticate high level input system use for develop and test game
	select			Facilities for selecting (from input) an on screen spatial or entities
	sound			Additional facilities to JME3 sound system
	sync			Additional facilities to syncing between multi thread progress

sg.atom.state		Additional for JME3 app state (bridge between to systems) and some common states for a common games

sg.atom.ui			General GUI for user interaction and styling in hierachy (non-strict) elements

sg.atom.utils		Collections of userful utilities and datastructures, algorimths here and there. 
Note: This package contains a lot of stuff borrowed from libraries and should be clean up. Do not rely too much in this library!

sg.atom.world		Concepts and interfaces to build and manage the game world and enviroment
	gen				Generate the world from data
	geometry		Maths for geometries
	lod				Level of detail framework provides a lot of methods to optimize scene and geometry. 
	material		Additions for JME3 material system
	physics			Additions for JME3 physic system
	rendering		Additions for JME3 render system
	terrain			Additions for JME3 terrain system
	visibility		Additions for JME3 cull and partition system
	

================================================================================
Changes:
---------------
Link to Git commits.

================================================================================
License
-------------
	AtomCore libraries are under BSD license.
	
	http://hub.jmonkeyengine.org/wiki/doku.php/bsd_license
	
	Other modules of Atom framework may also includes special license due to its dependencies so read carefully before use.
	
