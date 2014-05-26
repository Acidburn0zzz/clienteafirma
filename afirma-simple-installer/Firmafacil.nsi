;Include Modern UI
  !include "MUI.nsh"

;Seleccionamos el algoritmo de compresi�n utilizado para comprimir nuestra aplicaci�n
SetCompressor lzma


;--------------------------------
;Con esta opcion alertamos al usuario y le pedimos confirmaci�n para abortar
;la instalaci�n
;Esta macro debe colocarse en esta posici�n del script sino no funcionara
	

  !define MUI_ABORTWARNING
  !define MUI_HEADERIMAGE
  !define MUI_HEADERIMAGE_BITMAP "ic_head.bmp"
  !define MUI_HEADERIMAGE_UNBITMAP "ic_head.bmp"
  !define MUI_WELCOMEFINISHPAGE_BITMAP "ic_install.bmp"
  !define MUI_UNWELCOMEFINISHPAGE_BITMAP "ic_install.bmp"
   
;Definimos el valor de la variable VERSION, en caso de no definirse en el script
;podria ser definida en el compilador
!define VERSION "1.1"


;--------------------------------
;Pages
  
  ;Mostramos la p�gina de bienvenida
  !insertmacro MUI_PAGE_WELCOME
  ;P�gina donde mostramos el contrato de licencia 
  !insertmacro MUI_PAGE_LICENSE "licencia.txt"
  ;p�gina donde se muestran las distintas secciones definidas
  !insertmacro MUI_PAGE_COMPONENTS
  ;p�gina donde se selecciona el directorio donde instalar nuestra aplicacion
  !insertmacro MUI_PAGE_DIRECTORY
  ;p�gina de instalaci�n de ficheros
  !insertmacro MUI_PAGE_INSTFILES
  ;p�gina final
  !insertmacro MUI_PAGE_FINISH
  
;p�ginas referentes al desinstalador
  !insertmacro MUI_UNPAGE_WELCOME
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES
  !insertmacro MUI_UNPAGE_FINISH
  
 
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "Spanish"

; Para generar instaladores en diferentes idiomas podemos escribir lo siguiente:
;  !insertmacro MUI_LANGUAGE ${LANGUAGE}
; De esta forma pasando la variable LANGUAGE al compilador podremos generar
;paquetes en distintos idiomas sin cambiar el script

;;;;;;;;;;;;;;;;;;;;;;;;;
; Configuration General ;
;;;;;;;;;;;;;;;;;;;;;;;;;
;Nuestro instalador se llamara si la version fuera la 1.0: Ejemplo-1.0.exe
OutFile Firmafacil${VERSION}.exe

;Aqui comprobamos que en la versi�n Inglesa se muestra correctamente el mensaje:
;Welcome to the $Name Setup Wizard
;Al tener reservado un espacio fijo para este mensaje, y al ser
;la frase en espa�ol mas larga:
; Bienvenido al Asistente de Instalaci�n de Aplicaci�n $Name
; no se ve el contenido de la variable $Name si el tama�o es muy grande
Name "Firma F�cil"
Caption "Instalador de Firma f�cil con @firma"
Icon ic_launcher.ico

;Comprobacion de integridad del fichero activada
CRCCheck on
;Estilos visuales del XP activados
XPStyle on

/*
	Declaracion de variables a usar
	
*/
# tambi�n comprobamos los distintos
; tipos de comentarios que nos permite este lenguaje de script

Var PATH
Var PATH_ACCESO_DIRECTO
;Indicamos cual sera el directorio por defecto donde instalaremos nuestra
;aplicaci�n, el usuario puede cambiar este valor en tiempo de ejecuci�n.
InstallDir "$PROGRAMFILES\Firmafacil"

; check if the program has already been installed, if so, take this dir
; as install dir
InstallDirRegKey HKLM SOFTWARE\Firmafacilcon@firma "Install_Dir"
;Mensaje que mostraremos para indicarle al usuario que seleccione un directorio
DirText "Elija un directorio donde instalar la aplicaci�n:"

;Indicamos que cuando la instalaci�n se complete no se cierre el instalador autom�ticamente
AutoCloseWindow false
;Mostramos todos los detalles del la instalaci�n al usuario.
ShowInstDetails show
;En caso de encontrarse los ficheros se sobreescriben
SetOverwrite on
;Optimizamos nuestro paquete en tiempo de compilaci�n, es �ltamente recomendable habilitar siempre esta opci�n
SetDatablockOptimize on
;Habilitamos la compresi�n de nuestro instalador
SetCompress auto
;Personalizamos el mensaje de desinstalaci�n
UninstallText "Desinstalador de Firma f�cil."


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Install settings                                                    ;
; En esta secci�n a�adimos los ficheros que forman nuestra aplicaci�n ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Section "Programa"
	StrCpy $PATH "Firmafacil"
	StrCpy $PATH_ACCESO_DIRECTO "Firmafacil"

	SetOutPath $INSTDIR\$PATH

;Incluimos todos los ficheros que componen nuestra aplicaci�n
	File  Firmafacil.exe
	File  licencia.txt
	File  ic_firmar.ico

;Hacemos que la instalaci�n se realice para todos los usuarios del sistema
        SetShellVarContext all

;Creamos tambi�n el aceso directo al instalador

	;creamos un acceso directo en el escitorio
	CreateShortCut "$DESKTOP\Firma f�cil.lnk" "$INSTDIR\Firmafacil\Firmafacil.exe"

	;Menu items
	CreateDirectory "$SMPROGRAMS\Firmafacil"
	CreateShortCut "$SMPROGRAMS\Firmafacil\Firma f�cil.lnk" "$INSTDIR\Firmafacil\Firmafacil.exe"
	CreateShortCut "$SMPROGRAMS\Firmafacil\unistall.lnk" "$INSTDIR\unistall.exe"

	
	;A�ade una entrada en la lista de "Program and Features"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$PATH \" "DisplayName" "Firmafacil"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$PATH \" "UninstallString" "$INSTDIR\unistall.exe"
	
	WriteUninstaller "$INSTDIR\unistall.exe"

	WriteRegStr HKLM SOFTWARE\$PATH "InstallDir" $INSTDIR
	WriteRegStr HKLM SOFTWARE\$PATH "Version" "${VERSION}"

	;Exec "explorer $SMPROGRAMS\$PATH_ACCESO_DIRECTO\"
	
	;Registry
	;CascadeAfirma.reg
	WriteRegStr HKEY_CLASSES_ROOT "*\shell\afirma.sign" "" "Firmar con 'Firma f�cil con @firma'"
	WriteRegStr HKEY_CLASSES_ROOT "*\shell\afirma.sign" "Icon" "$INSTDIR\Firmafacil\Firmafacil.exe"
	WriteRegStr HKEY_CLASSES_ROOT "*\shell\afirma.sign\command" "" "$INSTDIR\Firmafacil\Firmafacil.exe sign -gui -i %1" 
	;WriteRegStr HKEY_CLASSES_ROOT "*\shell\afirma.sign\command" "" "$INSTDIR\Firmafacil\Firmafacil.exe %1" 
	
	;Verify
	; .csig
	WriteRegStr HKEY_CLASSES_ROOT ".csig" "" "Firma binaria CMS/CAdES"
	WriteRegStr HKEY_CLASSES_ROOT ".csig\DefaultIcon" "" "$INSTDIR\Firmafacil\ic_firmar.ico"
	WriteRegStr HKEY_CLASSES_ROOT ".csig\shell\Verify" "" "Verificar con 'Firma f�cil con @firma'"
	;WriteRegStr HKEY_CLASSES_ROOT ".csig\shell\Verify" "Icon" "$INSTDIR\Firmafacil\ic_firmar.ico"
	WriteRegStr HKEY_CLASSES_ROOT ".csig\shell\Verify\command" "" "$INSTDIR\Firmafacil\Firmafacil.exe verify -gui -i %1"
;	verify -gui -i %1"	

	;Verify
	; .xsig
	WriteRegStr HKEY_CLASSES_ROOT ".xsig" "" "Firma XMLDSig/XAdES"
	WriteRegStr HKEY_CLASSES_ROOT ".xsig\DefaultIcon" "" "$INSTDIR\Firmafacil\ic_firmar.ico"
	WriteRegStr HKEY_CLASSES_ROOT ".xsig\shell\Verify" "" "Verificar con 'Firma f�cil con @firma'"
	WriteRegStr HKEY_CLASSES_ROOT ".xsig\shell\Verify\command" "" "$INSTDIR\Firmafacil\Firmafacil.exe verify -gui -i %1"	
	
SectionEnd


;;;;;;;;;;;;;;;;;;;;;;
; Uninstall settings ;
;;;;;;;;;;;;;;;;;;;;;;

Section "Uninstall"
	StrCpy $PATH "Firmafacil"
	StrCpy $PATH_ACCESO_DIRECTO "Firmafacil"
    
	SetShellVarContext all
	
	RMDir /r $INSTDIR\$PATH
	;remove instalation directory
	RMDir /r $INSTDIR 
	
	;delete start menu shortcuts
	Delete "$DESKTOP\Firma f�cil.lnk"
	RMDir /r $SMPROGRAMS\$PATH_ACCESO_DIRECTO
	
	DeleteRegKey HKLM "SOFTWARE\$PATH"
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$PATH \" 
			
	DeleteRegKey HKEY_CLASSES_ROOT "*\shell\afirma.sign"
	DeleteRegKey HKEY_CLASSES_ROOT "*\shell\afirma.verify"
	
	;DeleteRegKey HKEY_CLASSES_ROOT ".csig\shell\Verify"
	DeleteRegKey HKEY_CLASSES_ROOT ".csig\shell\Verify"
	DeleteRegKey HKEY_CLASSES_ROOT ".xsig\shell\Verify"
	
SectionEnd
