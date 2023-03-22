function Start-Spiritbox {
  # TODO: open CLI > import > start > a child process is created and spiritbox is added to system tray
  # https://www.systanddeploy.com/2018/12/create-your-own-powershell.html
  
  Start-Process -WindowStyle Hidden powershell.exe "Import-Module .\spiritbox.psm1; Show-SpiritboxTrayIcon"
  Stop-Process $PID
}