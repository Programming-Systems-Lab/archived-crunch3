============================
README FOR CONTENT EXTRACTOR
============================

1) Installation
==================

If you are installing from installer -
------------------------------------

To use the content extractor, simply double click on the file Crunch#.exe 
and follow the instructions (where # indicates the version of Crunch). It 
will help you install Crunch and it will be ready to run (whether or not you
have Java installed on your computer). You can jump down to the Usage section
of this document.

If you are downloading from CVS -
-------------------------------

After getting the appropriate CVSROOT from one of the Crunch developers
to check out your own copy of Crunch, check out the module psl/crunch
I recommend that you do this in Eclipse since this will also perhaps make
you switch to using Eclipse as your default IDE. :-) Anyways, once you have
checked it out, you can open it as an Eclipse Java project (the .project file
is included). All the source is in the 'src' directory. The package psl.crunch3
is the most important one. The plugin infrastructure is in the psl.crunch3.plugins
which should explain the API that you need to implement in order to create you own
plugins. For your convience, I have created a sample plugin whose implementation
you can find in psl.crunch3.plugins.sample.

You are basically ready to run the project. You have the option to run it in
verbose mode. In your runtime arguments, put in a --verbose. Also, if you want
to use our clustering stuff, you should also perhaps give the argument -Xmx768m
which will provide the JVM with a maximum memory of 768MB. (Change the syntax of
this argument depending on your IDE). This much memory will never really be used
but, well, you get the idea. You are ready to use Crunch.


2) Usage
===========

The input file and the output file are necessary but the settings file is 
optional. The default settings file is settings.txt. In order to change 
the settings without the settings GUI provided in the Proxy, the file must be 
directly edited. The file is saved using a Java Properties file. See the 
Java APIs for the proper format.

Once the content extractor starts up, it will start the proxy up on port 
4000. Point your web browser to listen to port 4000 on the localhost if 
you run it on your own machine, or on the name of the particular server 
that you run it on.

In Internet Explorer, this can be done by going to Tools -> Internet Options ->
Connections -> LAN Settings. Check the proxy server box and set the server name to
localhost (if on local machine) or to the server that you are running the proxy on. 
If running on local machine, server is localhost. Default port is 4000 unless 
otherwise specified. In Mozilla/Firefox, you can click on Tools -> Options. Click 
on the Connection settings button which will launch a new window. Change the proxy 
settings from Direct Connection to Manual. If running on local machine, server is 
localhost. Default port is 4000 unless otherwise specified.


3) Contact
=============

Send email to suhit@cs.columbia.edu if you have any questions.

