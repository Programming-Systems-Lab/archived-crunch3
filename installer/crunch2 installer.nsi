; example2.nsi
;
; This script is based on example1.nsi, but it remember the directory, 
; has uninstall support and (optionally) installs start menu shortcuts.
;
; It will install makensisw.exe into a directory that the user selects,

;--------------------------------
;Include Modern UI

  !include "MUI.nsh"

;--------------------------------

; The name of the installer
Name "Crunch 2"

!define MUI_ICON crunch2\crunch2_icon.ico
!define MUI_UNICON crunch2\crunch2_icon.ico

; The file to write
OutFile "Crunch 2 Setup.exe"

;Icon crunch2\crunch2_icon.ico

; The default installation directory
InstallDir "$PROGRAMFILES\Crunch 2"

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\Crunch2" "Install_Dir"

;--------------------------------
;Interface Configuration

  !define MUI_HEADERIMAGE
  !define MUI_ABORTWARNING
  !define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\win.bmp"
;--------------------------------

; Pages

;Page components
;Page directory
;Page instfiles

;UninstPage uninstConfirm
;UninstPage instfiles

  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------

; The stuff to install
Section "Crunch 2 (required)" SecCrunch2

  SectionIn RO
  
  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  ; Put file there
  File "crunch2\crunch2 debug.exe"
  File "crunch2\crunch2.exe"
  File "crunch2\crunch2.jar"
  File "crunch2\crunch2_icon.ico"
  File "crunch2\javaw.exe.manifest"
  File "crunch2\LICENSE-apache"
  File "crunch2\LICENSE-DOM.html"
  File "crunch2\LICENSE-nekohtml"
  File "crunch2\LICENSE-SAX.html"
  File "crunch2\LICENSE-xerces"
  File "crunch2\README.TXT"
  File "crunch2\swt-win32-3058.dll"
  File /r "crunch2\config"
  File /r "crunch2\images"
  File /r "crunch2\jre"
  
  ; Write the installation path into the registry
  WriteRegStr HKLM SOFTWARE\Crunch2 "Install_Dir" "$INSTDIR"
  
  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Crunch2" "DisplayName" "Crunch 2"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Crunch2" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Crunch2" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Crunch2" "NoRepair" 1
  WriteUninstaller "uninstall.exe"
  
SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts" SecShortcuts

  CreateDirectory "$SMPROGRAMS\Crunch 2"
  CreateShortCut "$SMPROGRAMS\Crunch 2\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\Crunch 2\Crunch 2.lnk" "$INSTDIR\crunch2.exe" "" "$INSTDIR\crunch2.exe" 0
  
SectionEnd

;--------------------------------
;Descriptions

  ;Language strings
  LangString DESC_SecCrunch2 ${LANG_ENGLISH} "Crunch 2 Program Files."
  LangString DESC_SecShortcuts ${LANG_ENGLISH} "Start Menu Shortcuts."

  ;Assign language strings to sections
  !insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${SecCrunch2} $(DESC_SecCrunch2)
    !insertmacro MUI_DESCRIPTION_TEXT ${SecShortcuts} $(DESC_SecShortcuts)
  !insertmacro MUI_FUNCTION_DESCRIPTION_END



;--------------------------------

; Uninstaller

Section "Uninstall"
  
  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Crunch2"
  DeleteRegKey HKLM SOFTWARE\Crunch2

  ; Remove files and uninstaller
  Delete "$INSTDIR\crunch2 debug.exe"
  Delete "$INSTDIR\crunch2.exe"
  Delete "$INSTDIR\crunch2.jar"
  Delete "$INSTDIR\crunch2_icon.ico"
  Delete "$INSTDIR\LICENSE-apache"
  Delete "$INSTDIR\LICENSE-DOM.html"
  Delete "$INSTDIR\LICENSE-nekohtml"
  Delete "$INSTDIR\LICENSE-SAX.html"
  Delete "$INSTDIR\LICENSE-xerces"
  Delete "$INSTDIR\README.TXT"
  Delete "$INSTDIR\swt-win32-3024.dll"
  RMDir /r "$INSTDIR\config"
  RMDir /r "$INSTDIR\images"
  RMDir /r "$INSTDIR\jre"
  Delete $INSTDIR\uninstall.exe

  ; Remove shortcuts, if any
  Delete "$SMPROGRAMS\Crunch 2\*.*"

  ; Remove directories used
  RMDir "$SMPROGRAMS\Crunch 2"
  RMDir "$INSTDIR"

SectionEnd