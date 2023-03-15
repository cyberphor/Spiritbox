function New-SpiritboxReport {
  Param([System.Windows.Forms.Form]$Form)

  # Declare a Report Details object
  $ReportDetails = [ordered]@{}
  $ReportHasNoErrors = $true

  # Add Event field to Report Details
  $Event = [ordered]@{}
  $Event.Add("kind", "enrichment")
  $Event.Add("category", "threat")
  $Event.Add("type", "indicator")
  $ReportDetails.Add("event", $Event)

  # Add Location field to Report Details
  $Location = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.ComboBox]) -and ($_.Name -eq "Location") })
  $ReportDetails.Add("geo.name", $Location.Text)
  # TODO: add "grid coordinates" option to config

  # Declare Threat field
  $Threat = [ordered]@{}

  # Add Threat Marking field to Threat field
  $Marking = @{"tlp" = "GREEN"}
  $Threat.Add("marking", $Marking)
  
  # Add Threat Feed field to Threat field
  $Feed = [ordered]@{}
  $Feed.Add("name", "Spiritbox")
  $Feed.Add("reference", "https://github.com/cyberphor/Spiritbox")
  $Threat.Add("feed", $Feed)

  # Add Observation field to Threat field
  $Observation = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.ComboBox]) -and ($_.Name -eq "Observation") })
  $Tactic = [ordered]@{"name" = $Observation.Text}
  $Threat.Add("tactic", $Tactic)

  # Declare Indicator field
  $Indicator = [ordered]@{}

  # Add Organization field to Indicator field
  $Organization = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.ComboBox]) -and ($_.Name -eq "Organization") })
  $Indicator.Add("provider", $Organization.Text)

  # Add Last Seen field to Indicator field
  $Date = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.DateTimePicker]) -and ($_.Name -eq "Date") }).Text
  $Time = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.DateTimePicker]) -and ($_.Name -eq "Time") }).Text
  $LastSeen = $Date + "T" + $Time + ".000Z"
  $Indicator.Add("last_seen", $LastSeen)

  # Parse indicators
  $ObserverTypes = @()
  $IndicatorTypes = @()
  $IpAddresses = @()
  ($Form.Controls | Where-Object { $_ -is [System.Windows.Forms.DataGridView] }).Rows | 
  ForEach-Object {
      $ObserverType = $_.Cells[0].FormattedValue
      $IndicatorType = $_.Cells[1].FormattedValue
      $IndicatorValue = $_.Cells[2].FormattedValue

      # Ensure at least one IndicatorValue is given
      if (-not [string]::IsNullorEmpty($IndicatorValue)) {
          $ObserverTypes += $ObserverType
          
          # Check if IndicatorValue is of IndicatorType
          if ($IndicatorType -eq "ipv4-addr") {
              if (-not (Test-IPAddress($IndicatorValue))) {
                  $ReportHasNoErrors = $false
                  $Message = "An invalid IP address was specified: " + $IndicatorValue
                  Write-SpiritboxEventLog -SeverityLevel ERROR -Message $Message
                  Show-SpiritboxError -Message $Message
              }
              $IpAddresses += $IndicatorValue
          }
      }
  }
  if ($IpAddresses.Count -ge 0) {
      $IndicatorTypes += "ipv4-addr"
      $Indicator.Add("ip", $IpAddresses)
  }
  $Observer = [ordered]@{"type" = $ObserverTypes}
  $ReportDetails.Add("observer", $Observer)
  $Indicator.Add("type", $IndicatorTypes)

  # Add Actions Taken field to Threat field
  $ActionsTaken = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.TextBox]) -and ($_.Name -eq "ActionsTaken") })
  $Threat.Add("response", $ActionsTaken.Text)

  # Add Threat field to Report Details
  $Threat.Add("indicator", $Indicator)
  $ReportDetails.Add("threat", $Threat)

  # Convert Report Details to JSON and return the Report
  $Report = $ReportDetails | ConvertTo-Json
  $Report | Out-Host
  if ($ReportHasNoErrors) {
      Write-SpiritboxEventLog -SeverityLevel INFORMATIONAL -Message $Report
      return $Report
  } else {
      Write-SpiritboxEventLog -SeverityLevel ERROR -Message $Report
      return $false
  }
}