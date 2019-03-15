call gradlew build
if "%ERRORLEVEL%" == "0" goto startruncrud
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:startruncrud
call C:\Documents\Development\Projects\tasks\runcrud.bat

start firefox
if "%ERRORLEVEL%" == "0" goto runfirefox
echo Cannot start the browser
goto fail

:runfirefox
call "C:\Program Files\Mozilla Firefox\firefox.exe" http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo
echo Task ended