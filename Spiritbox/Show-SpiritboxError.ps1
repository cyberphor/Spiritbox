function Show-SpiritboxError {
  Param([string]$Message)
  # TODO: add Spiritbox icon and make "Error" the label inside the dialog box
  $Button = [System.Windows.Forms.MessageBoxButtons]::OK
  $Icon = [System.Windows.Forms.MessageBoxIcon]::Error
  [System.Windows.Forms.MessageBox]::Show($Message, "Error", $Button, $Icon) | Out-Null
}
