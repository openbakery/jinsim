JInSim - Java InSim Library for Live For Speed v0.6
==================

The purpose of JInSim is to allow Java programmers to write add-on applications for the Live For Speed racing simulation.
 
Live For Speed has a network protocol called InSim, and two sub-protocols called OutSim and OutGauge. JInSim makes it easier to set up a connection to Live For Speed and only get the InSim packets that are wanted. 

v0.6 is compatile with LFS Version 0.6H

Installation
----------------
Once you've downloaded the zip file that contains the release of JInSim, the installation is easy. Simply unzip the zip file and find the binary distribution in the "dist" directory. Here is a more complete rundown of the top level directory structure:

jinsim.zip
	LICENSE - The license file that outlines how this library is distributed, and how you may distribute it. It uses the Mozilla Public License v1.1.
	README - This file.

	CHANGES - A list of changes that have taken place with each release.

	src - The java source files for JInSim

	lib - The libraries needed to link with JInSim. Currently, inside are the libs for the logging and junit.
	 	Only the commons-logging.jar is needed to run JInSim.
	
	dist - The binary distribution. The jinsim.jar file contained within is needed on the classpath for you to run applications created using JInSim.

	doc - The javadoc for the JInSim classes.


Examples
---------------
Even with this first release, there are a number of working examples contained in JInSim. 
The simple "Hello World!" example is a good one to start off with. You can find the source
in "src/org.openbakery/jinsim/examples/helloworld/". The rest of the examples can be found one
level up in the examples package.

To run "Hello World!":

1. Start up Live For Speed with InSim enabled for a port as described in the InSim.txt
    documentation that comes with Live For Speed.

2. Open a command line. For instance, in Windows XP you can go to the Start menu,
    select "Run..." and type "cmd" and hit the Enter key.

3. Change the directory to the location where you unzipped the jinsim zip file.
4. Execute the following command line where <port> is the port InSim is listening
    on, and <password> is your admin password. If you don't have an admin password
    set, just leave it blank.

    java -cp dist/jinsim.jar org.openbakery.examples.helloworld.Main localhost <port> <password>

    You should see this on your Live For Speed screen:

           host: Hello World!
           InSimClose: waiting for connection

    And this in your console:
	host : ^LHello World!
	InSimClose : waiting for connection

5. Read the javadocs for more information about any of the examples. Look at the source to see 
    even more in-depth comments about what's going on.

Comments
----------------

For comments, questions or suggestions email: <brilwing@liveforspeed.at>



