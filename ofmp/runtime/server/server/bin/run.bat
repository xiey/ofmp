@echo off
rem -------------------------------------------------------------------------
rem Eclipse OFMP Bootstrap Script for Win32
rem -------------------------------------------------------------------------

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=run.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

set JAVA=java

set SERVER_HOME=%DIRNAME%\..

rem Setup Equ%inox specific properties
set JAVA_OPTS=%JAVA_OPTS% -Dosgi.install.area=%SERVER_HOME% -Dosgi.configuration.area=%SERVER_HOME%\configuration -Dofmp.base.dir=%SERVER_HOME%

rem Sun JVM memory allocation pool parameters. Modify as appropriate.
::set JAVA_OPTS=%JAVA_OPTS% -Xms256m -Xmx256m -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000

rem JPDA options. Uncomment and modify as appropriate to enable remote debugging.
::set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n %JAVA_OPTS%

rem Profiling options. Uncomment and modify as appropriate to enable remote profiling.
::set JAVA_OPTS=-XrunpiAgent:server=enabled %JAVA_OPTS%

rem Enable monitoring with jconsole
::set JAVA_OPTS=-Dcom.sun.management.jmxremote %JAVA_OPTS%  

rem Equinox debugging
::set EQUINOX_OPTS=-debug SERVER_HOME\configuration\debug.options

echo ===============================================================================
echo .
echo   Eclipse OFMP Bootstrap Environment
echo .
echo   SERVER_HOME: %SERVER_HOME%
echo .
echo   JAVA: %JAVA%
echo .
echo   JAVA_OPTS: %JAVA_OPTS%
echo .
echo   PATH: %PATH%
echo .
echo ===============================================================================

rem the Equinox Launcher requires the process to be launched from the folder where it is deployed
cd ..\plugins

"%JAVA%" %JAVA_OPTS% -classpath org.eclipse.equinox.launcher_1.0.100.v20080509-1800.jar org.eclipse.equinox.launcher.Main -console %EQUINOX_OPTS%
