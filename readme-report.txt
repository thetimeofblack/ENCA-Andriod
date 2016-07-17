# Readme (Report version)
ENCA not cleaning agent, the cleaning agent picker.

## Upload content
- ENCA Eclipse project zip:                    Enca.zip
- ENCA-express Android Studio project zip:     Enca-Android.zip
- Executable jar:                              Enca.jar
- Android apk:                                 Enca.apk
- Project report pdf:                          Project Report.pdf
- Database:                                    data.db
- Database dump:                               data.sql
- Software readme:                             README-SW.md

## Instructions
- Test run:
	You can run our program with Enca.jar on your desktop computer to test ENCA.
	You can install Enca.apk on your Android device or emulator to test ENCA-express.
- Import, build and run:
	* Use Eclipse for ENCA:
		Import Enca.zip to your Eclipse project.
		Run from class "de.fhl.enca.gui.application.Login".
	* Use Android Studio for ENCA-express:
		ImportXXXXXXXXXX
- Diagnose:
	* Interface looks strange:
		Check your Java Runtime Environment version, if it is not up-to-date,
		please update now.
		Check if you have installed javafx runtime environment.
		(Usually comes with JRE)
	* File error:
		If errors such as "file not found" and "no such directory" occur, try to clean
		"{home}\AppData\Roaming\Enca"          on Windows;
		or
		"~/Library/Application Support/Enca"   on Mac;
		or
		"Device Storage/Enca/"                 on Android device
		and clean the eclipse project.
		These errors are very likely caused by debug run configurations and tests.
		We recommend you to use Enca.jar if you want to try out our software.
	* JUnit error:
		Running JUnit test all at once may cause database lock. If you encounter
		with the problem, try to run JUnit class by class.

## Warnings
- ENCA would write files to:
	"{home}\AppData\Roaming\Enca"              on Windows;
	or
	"~/Library/Application Support/Enca"       on Mac;
	or
	"Device Storage/Enca/"                     on Android device.
	Do not forget to clean this directory after trying out our software.
- DO NOT OPEN:
	Please do not open the database dump data.sql unless truly necessary.
	And do not use auto wrapping to open the file.
	It contains blobs of considerable size and may take quite a while to open.
	That is why we also provide you a database file.

This software is also host on GitHub under MIT license:
https://github.com/Nimita311/ENCA-Andriod
ENCA group 2016
