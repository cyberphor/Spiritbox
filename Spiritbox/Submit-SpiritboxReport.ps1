filter Submit-SpiritboxReport {
  $Body = $input -join "," # Converts ArrayListEnumeratorSimple to String
  if ($Body -ne $false) { # TODO: remove pipeline check
      # Progress Bar Form
      $Subscribers = $Config.Subscribers
      $ProgressBarForm = New-Object System.Windows.Forms.Form
      $ProgressBarForm.ClientSize = "240,120"
      $ProgressBarForm.Text = "Spiritbox"
      $ProgressBarForm.StartPosition = "CenterScreen"
      $ProgressBarForm.Icon = $Icon

      # Progress Bar
      $ProgressBar = New-Object System.Windows.Forms.ProgressBar
      $ProgressBar.Name = "Progress"
      $ProgressBar.Size = "220,20"
      $ProgressBar.Location = "10,10"
      $ProgressBar.Style = "Blocks"
      $ProgressBar.Value = 0
      $ProgressBarForm.Controls.Add($ProgressBar)

      # Progress Bar Label
      $ProgressBarLabel = New-Object System.Windows.Forms.Label
      $ProgressBarLabel.Size = "220,60"
      $ProgressBarLabel.Location = "10,35"
      $ProgressBarForm.Controls.Add($ProgressBarLabel)

      # Loop
      $ProgressBarForm.Show() | Out-Null
      $ProgressBarForm.Focus() | Out-Null
      $i = 0
      $Subscribers |
      ForEach-Object {
          $ProgressBarLabel.Text = "Notifying: " + $_.Name
          Write-SpiritboxEventLog -SeverityLevel INFORMATIONAL -Message $ProgressBarLabel.Text
          $ProgressBarForm.Refresh()
          Start-Sleep -Seconds 1
          try {
              $Uri = "http://" + $_.ip + ":" + $_.port
              $SubscriberResponse = Invoke-RestMethod -Method POST -Uri $Uri -ContentType "application/json" -Body $Body
              $Seconds = 1
              Write-SpiritboxEventLog -SeverityLevel INFORMATIONAL -Message $SubscriberResponse
          } catch {
              $SubscriberResponse = $_
              $Seconds = 3
              Write-SpiritboxEventLog -SeverityLevel ERROR -Message $SubscriberResponse
          }
          $i++
          [int]$Percent = ($i / $Subscribers.count) * 100
          $ProgressBar.Value = $Percent
          $ProgressBarLabel.Text = "Response: " + $SubscriberResponse
          $ProgressBarForm.Refresh()
          Start-Sleep -Seconds $Seconds
      }
      # TODO: add Cancel button to interrupt progress
      $ProgressBarForm.Close()
  }
}