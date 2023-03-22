function Show-SpiritboxTrayIcon {
  # System Tray Icon
  $NotifyIcon = New-Object System.Windows.Forms.NotifyIcon
  $NotifyIcon.Text = "Spiritbox"
  $NotifyIcon.Icon = $Icon
  $NotifyIcon.ContextMenu = New-Object System.Windows.Forms.ContextMenu

  # Menu Item 1: New Report
  $NewReport = New-Object System.Windows.Forms.MenuItem
  $NewReport.Enabled = $true
  $NewReport.Text = "New Report"
  $NewReport.Add_Click({Show-SpiritboxReportForm})
  $NotifyIcon.ContextMenu.MenuItems.AddRange($NewReport)

  # Menu Item 2: Show Log
  $ShowLog = New-Object System.Windows.Forms.MenuItem
  $ShowLog.Enabled = $true
  $ShowLog.Text = "Show Log"
  $ShowLog.Add_Click({Show-SpiritboxEventLog})
  $NotifyIcon.ContextMenu.MenuItems.AddRange($ShowLog)

  # Menu Item 3: Stop Spiritbox
  $StopSpiritbox = New-Object System.Windows.Forms.MenuItem
  $StopSpiritbox.Text = "Stop Spiritbox"
  $StopSpiritbox.Add_Click({$NotifyIcon.Dispose(); Stop-Process $pid})
  $NotifyIcon.ContextMenu.MenuItems.AddRange($StopSpiritbox)

  # Show System Tray Icon and Form
  $NotifyIcon.Visible = $true
  $ApplicationContext = New-Object System.Windows.Forms.ApplicationContext
  [System.Windows.Forms.Application]::Run($ApplicationContext)
}