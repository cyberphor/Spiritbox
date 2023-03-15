function Reset-SpiritboxReportForm {
  Param([System.Windows.Forms.Form]$Form)
  $Form.Controls | 
  Where-Object { 
      ($_ -isnot [System.Windows.Forms.Label]) -and 
      ($_ -isnot [System.Windows.Forms.Button])
  } |
  ForEach-Object {
      if ($_ -is [System.Windows.Forms.DateTimePicker]) { 
          $_.Value = Get-Date 
      } elseif ($_ -is [System.Windows.Forms.ComboBox]) {
          $_.SelectedIndex = 0
      } elseif ($_ -is [System.Windows.Forms.TextBox]) {
          $_.Clear()
      # TODO: add logic to clear DataGridView cells
      }
  }
  $Form.Refresh()
}