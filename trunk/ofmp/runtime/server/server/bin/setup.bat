@echo off
rem -------------------------------------------------------------------------
rem Eclipse OFMP Setup Script for Win32
rem -------------------------------------------------------------------------

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=setup.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

set SERVER_HOME=%DIRNAME%\..

if "%ORACLE_SID%" == "" set ORACLE_SID=XE

if "%SYSTEM_PWD%" == "" set SYSTEM_PWD=system

echo ===============================================================================
echo .
echo   Eclipse OFMP Setup
echo .
echo   Output SQL logs redirected to log/setup.log
echo .

echo   Create tablespaces
sqlplus -S system/%SYSTEM_PWD%@%ORACLE_SID% @%SERVER_HOME%\sql\1_tablespaces.sql >> ..\log\setup.log

echo   Create the VT_OWNER user
sqlplus -S system/%SYSTEM_PWD%@%ORACLE_SID% @%SERVER_HOME%\sql\2_owner.sql >> ..\log\setup.log

echo   Create the VT_USER user
sqlplus -S VT_OWNER/VT_OWNER@%ORACLE_SID% @%SERVER_HOME%\sql\3_user.sql >> ..\log\setup.log

echo   Create the VT_REPORT user
sqlplus -S VT_OWNER/VT_OWNER@%ORACLE_SID% @%SERVER_HOME%\sql\4_report.sql >> ..\log\setup.log

echo   Create the database ddl
sqlplus -S VT_OWNER/VT_OWNER@%ORACLE_SID% @%SERVER_HOME%\sql\5_ddl.sql >> ..\log\setup.log

echo   Grant rights to users
sqlplus -S VT_OWNER/VT_OWNER@%ORACLE_SID% @%SERVER_HOME%\sql\6_grants.sql >> ..\log\setup.log

echo .
echo   Done.
echo .
echo ===============================================================================
