@echo off
setlocal enabledelayedexpansion

:: Prompt the user for the folder path
set /p "folder_path=Enter the folder path: "

:: Check if the folder exists
if not exist "%folder_path%" (
    echo The specified folder does not exist.
    pause
    exit /b
)

:: Get the folder name
for %%I in ("%folder_path%") do set "folder_name=%%~nxI"

:: Create a temporary folder in the current directory to store modified files
set "temp_folder=%CD%\%folder_name%_temp"
mkdir "!temp_folder!" 2>nul

:: Iterate over each file in the folder, remove the first line, and copy to the temporary folder
for %%F in ("%folder_path%\*") do (
    set "outfile=%temp_folder%\%%~nxF"
    (for /f "skip=1 delims=" %%L in (%%F) do echo %%L) > "!outfile!"
)

:: Create a zip file in the current directory with the folder contents
powershell Compress-Archive -Path "!temp_folder!\*" -DestinationPath "aufgabe.zip" -Force

echo Zip file created: "aufgabe.zip"
pause
exit /b
