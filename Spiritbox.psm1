# TODO: add GitHub Test Action
try {
    Add-Type -AssemblyName PresentationFramework
    Add-Type -AssemblyName System.Drawing
    Add-Type -AssemblyName System.Windows.Forms
    Add-Type -AssemblyName WindowsFormsIntegration
    $Config = Get-Content config.json | ConvertFrom-Json
    $Icon = New-Object System.Drawing.Icon("$PSScriptRoot\ghost.ico")
} catch {
    Write-Host $_
    exit
}

function Show-SpiritboxLog {
    Get-ChildItem -Filter "spiritbox*.log" | 
    Sort-Object -Property LastWriteTime -Descending |
    Select-Object -First 1 |
    Invoke-Item
}

function Write-SpiritboxEventLog {
    Param(
        [Parameter(Mandatory)][ValidateSet("DEBUG","INFORMATIONAL","NOTICE","WARNING","ERROR","CRITICAL","ALERT","EMERGENCY")]$Prefix,
        [Parameter(Mandatory)][string]$Message
    )
    $Date = Get-Date
    $Time = $Date.ToString("yyyy-MM-ddTHH:mm:ss.fffZ") 
    $SpiritboxEventLog = "spiritbox-" + $Date.ToString("yyyy-MM-dd") + ".log"
    $SpiritboxEvent = $Time, $Prefix, $Message | ForEach-Object { "[" + $_ + "]" }
    $SpiritboxEvent -join " " | Out-File -Append -FilePath $SpiritboxEventLog
}

function Show-SpiritboxError {
    Param([string]$Message)
    # TODO: add Spiritbox icon and make "Error" the label inside the dialog box
    $Button = [System.Windows.Forms.MessageBoxButtons]::OK
    $Icon = [System.Windows.Forms.MessageBoxIcon]::None
    [System.Windows.Forms.MessageBox]::Show($Message, "Error", $Button, $Icon) | Out-Null
}

filter Test-IPAddress {
    $Address = [string]$input
    try {
        return $Address -match ($PSObject = [ipaddress]$Address)
    } catch {
        return $false
    }
}

filter Submit-SpiritboxReport {
    $Body = $input -join "," # convert ArrayListEnumeratorSimple to a String
    if ($Body -ne $false) {
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
            Write-SpiritboxEventLog -Prefix INFORMATIONAL -Message $ProgressBarLabel.Text
            $ProgressBarForm.Refresh()
            Start-Sleep -Seconds 1
            try {
                $Uri = "http://" + $_.ip + ":" + $_.port
                $SubscriberResponse = Invoke-RestMethod -Method POST -Uri $Uri -ContentType "application/json" -Body $Body
                $Seconds = 1
                Write-SpiritboxEventLog -Prefix INFORMATIONAL -Message $SubscriberResponse
            } catch {
                $SubscriberResponse = $_
                $Seconds = 3
                Write-SpiritboxEventLog -Prefix ERROR -Message $SubscriberResponse
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

function Reset-SpiritboxForm {
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
        }
    }
    $Form.Refresh()
}

filter New-SpiritboxReport {
    # Report
    $Report = [ordered]@{}
    $ReportHasNoErrors = $true
    # TODO: add logic to handle DataGridView cells (observer type, indicator type, indicator values)
    $Form = $input.Controls | Where-Object { ($_ -isnot [System.Windows.Forms.Label]) -and ($_ -isnot [System.Windows.Forms.Button]) }

    # combine date and time into an Elastic-friendly @timestamp value
    $Date = $Form | Where-Object { $_.Name -eq "date" } | Select-Object -ExpandProperty Text
    $Time = $Form | Where-Object { $_.Name -eq "time" } | Select-Object -ExpandProperty Text  
    $Timestamp = $Date + "T" + $Time + ".000Z"
    $Report.Add("threat.indicator.last_seen", $Timestamp)

    # add the Location, Organization, and Activity observed to the report
    $Form | Where-Object { $_.Name -in ("geo.name", "organization.name", "threat.tactic.name", "observer.type") } | ForEach-Object { $Report.Add($_.Name, $_.Text) }

    # add the Attacker IP Address observed to the report
    $AttackerIPAddress = $Form | Where-Object { $_.Name -eq "source.ip" }
    if ($AttackerIPAddress.Text | Test-IPAddress) {
        $Report.Add($AttackerIPAddress.Name, $AttackerIPAddress.Text)
    } else {
        $ReportHasNoErrors = $false
        if ($AttackerIPAddress.Text -eq "") {
            $Message = 'No IP address was specified for the "Attacker IP Address" field.'
        } else {
            $Message = "An invalid IP address was specified: " + $AttackerIPAddress.Text
        }
        Write-SpiritboxEventLog -Prefix ERROR -Message $Message
        Show-SpiritboxError -Message $Message
    }
    
    # add the Victim IP Address observed to the report
    $VictimIPAddress = $Form | Where-Object { $_.Name -eq "destination.ip" }
    if ($VictimIPAddress.Text | Test-IPAddress) {
        $Report.Add($VictimIPAddress.Name, $VictimIPAddress.Text)
    } else {
        $ReportHasNoErrors = $false
        if ($VictimIPAddress.Text -eq "") {
            $Message = 'No IP address was specified for the "Victim IP Address" field.'    
        } else {
            $Message = "An invalid IP address was specified: " + $VictimIPAddress.Text
        }
        Write-SpiritboxEventLog -Prefix ERROR -Message $Message
        Show-SpiritboxError -Message $Message
    } 

    # add the Actions Taken to the report
    $Form | Where-Object { $_.Name -eq "threat.response.description" } | ForEach-Object { $Report.Add($_.Name, $_.Text) }

    if ($ReportHasNoErrors) {
        $Report = $Report | ConvertTo-Json -Compress
        Write-SpiritboxEventLog -Prefix INFORMATIONAL -Message $Report
        return $Report
    } else {
        return $false
    }
}

function Show-SpiritboxForm {
    # Form
    $Form = New-Object System.Windows.Forms.Form
    $Form.ClientSize = "400,670" # Width: 400, Height: 500
    $Form.MaximizeBox = $false # disable maximizing
    $Form.FormBorderStyle = "Fixed3D" # disable resizing
    $Form.Text = "Spiritbox"
    $Form.Icon = New-Object System.Drawing.Icon("$PSScriptRoot\ghost.ico")
    $Form.StartPosition = "CenterScreen"

    # Date Label
    $DateLabel = New-Object System.Windows.Forms.Label
    $DateLabel.Text = "Date"
    $DateLabel.Size = "120,25"
    $DateLabel.Location = "10,10"
    $Form.Controls.Add($DateLabel)

    # Date Field
    $DateField = New-Object System.Windows.Forms.DateTimePicker
    $DateField.Name = "date"
    $DateField.Size = "260,25"
    $DateField.Location = "130,10"
    $DateField.Format = [Windows.Forms.DateTimePickerFormat]::Custom 
    $DateField.CustomFormat = "yyyy-MM-dd"
    $Form.Controls.Add($DateField)

    # Time Label
    $TimeLabel = New-Object System.Windows.Forms.Label
    $TimeLabel.Text = "Time"
    $TimeLabel.Location = "10,35"
    $Form.Controls.Add($TimeLabel)

    # Time Field
    $TimeField = New-Object System.Windows.Forms.DateTimePicker
    $TimeField.Name = "time" 
    $TimeField.Size = "260,25"
    $TimeField.Location = "130,35"
    $TimeField.Format = [Windows.Forms.DateTimePickerFormat]::Custom 
    $TimeField.CustomFormat = "HH:mm:ss"
    $TimeField.ShowUpDown = $true
    $Form.Controls.Add($TimeField)
            
    # Location Label
    $LocationLabel = New-Object System.Windows.Forms.Label
    $LocationLabel.Text = "Location"
    $LocationLabel.Location = "10,60"
    $Form.Controls.Add($LocationLabel)

    # Location Field
    $LocationField = New-Object System.Windows.Forms.ComboBox
    $LocationField.Name = "geo.name"
    $LocationField.Size = "260,25"
    $LocationField.Location = "130,60"
    $Config.Locations | ForEach-Object {[void]$LocationField.Items.Add($_)} # void is used so no output is returned during each add
    $LocationField.SelectedIndex = 0
    $Form.Controls.Add($LocationField)

    # Organization Label
    $OrganizationLabel = New-Object System.Windows.Forms.Label
    $OrganizationLabel.Text = "Organization"
    $OrganizationLabel.Location = "10,85"
    $Form.Controls.Add($OrganizationLabel)

    # Organization Field
    $OrganizationField = New-Object System.Windows.Forms.ComboBox
    $OrganizationField.Name = "organization.name"
    $OrganizationField.Size = "260,25"
    $OrganizationField.Location = "130,85"
    $Config.Organizations | ForEach-Object {[void]$OrganizationField.Items.Add($_)} # void is used so no output is returned during each add
    $OrganizationField.SelectedIndex = 0
    $Form.Controls.Add($OrganizationField)

    # Activity Label
    $ActivityLabel = New-Object System.Windows.Forms.Label
    $ActivityLabel.Text = "Activity"
    $ActivityLabel.Location = "10,110"
    $Form.Controls.Add($ActivityLabel)

    # Activity Field
    $ActivityField = New-Object System.Windows.Forms.ComboBox
    $ActivityField.Name = "threat.tactic.name"
    $ActivityField.Size = "260,25"
    $ActivityField.Location = "130,110"
    $Config.Activities | ForEach-Object {[void]$ActivityField.Items.Add($_)} # void is used so no output is returned during each add
    $ActivityField.SelectedIndex = 0
    $Form.Controls.Add($ActivityField)

    # Indicators DataGridView
    $IndicatorsDataGridView = New-Object System.Windows.Forms.DataGridView
    $IndicatorsDataGridView.Size = "380,245"
    $IndicatorsDataGridView.Location = "10,145"
    $IndicatorsDataGridView.RowHeadersVisible = $false # don't show first column
    $IndicatorsDataGridView.AllowUserToAddRows = $false
    $IndicatorsDataGridView.AllowUserToResizeRows = $false
    $IndicatorsDataGridView.ColumnHeadersVisible = $true
    $IndicatorsDataGridView.AutoSizeColumnsMode = "Fill"

    # Indicators DataGridView: Observer Types Column
    $ObserverTypes = $Config.Sources
    $ObserverTypesColumn = New-Object System.Windows.Forms.DataGridViewComboBoxColumn
    $ObserverTypesColumn.Name = "Observer Type"
    $ObserverTypesColumn.DataSource = $IndicatorTypes
    $ObserverTypesColumn.DefaultCellStyle.NullValue = $ObserverTypes[0]
    $IndicatorsDataGridView.Columns.Add($ObserverTypesColumn)

    # Indicators DataGridView: Indicator Types Column
    $IndicatorTypes = $Config.Indicators.IndicatorType
    $IndicatorTypesColumn = New-Object System.Windows.Forms.DataGridViewComboBoxColumn
    $IndicatorTypesColumn.Name = "Indicator Type"
    $IndicatorTypesColumn.DataSource = $IndicatorTypes
    $IndicatorTypesColumn.DefaultCellStyle.NullValue = $IndicatorTypes[0]
    $IndicatorsDataGridView.Columns.Add($IndicatorTypesColumn)
    # TODO: add ability to copy/paste from a spreadsheet

    # Indicators DataGridView: Indicator Values Column
    $IndicatorValues = $Config.Indicators.ValueType
    $IndicatorValuesColumn = New-Object System.Windows.Forms.DataGridViewTextBoxColumn
    $IndicatorValuesColumn.Name = "Indicator Value"
    $IndicatorsDataGridView.Columns.Add($IndicatorValuesColumn)
    $IndicatorsDataGridView.RowCount = 10
    $Form.Controls.Add($IndicatorsDataGridView)

    # Actions Taken Label
    $ActionsTakenLabel = New-Object System.Windows.Forms.Label
    $ActionsTakenLabel.Text = "Actions Taken"
    $ActionsTakenLabel.Location = "10,400" 
    $Form.Controls.Add($ActionsTakenLabel)

    # Actions Taken Field
    $ActionsTakenField = New-Object System.Windows.Forms.TextBox
    $ActionsTakenField.Name = "threat.response.description"
    $ActionsTakenField.Size = "380,200"
    $ActionsTakenField.Location = "10,425"
    $ActionsTakenField.Multiline = $true
    $ActionsTakenField.AcceptsReturn = $true
    $Form.Controls.Add($ActionsTakenField)

    # Submit Button
    $SubmitButton = New-Object System.Windows.Forms.Button
    $SubmitButton.Text = "Submit"
    $SubmitButton.Size = "185,25"
    $SubmitButton.Location = "10,630"
    $SubmitButton.Add_Click({
        $Form | New-SpiritboxReport | Submit-SpiritboxReport
        Reset-SpiritboxForm -Form $Form
    })
    $Form.Controls.Add($SubmitButton)

    # Reset Button
    $ResetButton = New-Object System.Windows.Forms.Button
    $ResetButton.Text = "Reset"
    $ResetButton.Size = "200,25"
    $ResetButton.Location = "190,630"
    $ResetButton.Add_Click({Reset-SpiritboxForm -Form $Form}) 
    $Form.Controls.Add($ResetButton)

    # Show Form
    $Form.ShowDialog() | Out-Null # Send dialog output (e.g., which button was pressed) to null
}

function Start-Spiritbox {
    # System Tray Icon
    $NotifyIcon = New-Object System.Windows.Forms.NotifyIcon
    $NotifyIcon.Text = "Spiritbox"
    $NotifyIcon.Icon = $Icon
    $NotifyIcon.ContextMenu = New-Object System.Windows.Forms.ContextMenu

    # Menu Item 1: New Report
    $NewReport = New-Object System.Windows.Forms.MenuItem
    $NewReport.Enabled = $true
    $NewReport.Text = "New Report"
    $NewReport.Add_Click({Show-SpiritboxForm})
    $NotifyIcon.ContextMenu.MenuItems.AddRange($NewReport)

    # Menu Item 2: Show Log
    $ShowLog = New-Object System.Windows.Forms.MenuItem
    $ShowLog.Enabled = $true
    $ShowLog.Text = "Show Log"
    $ShowLog.Add_Click({Show-SpiritboxLog})
    $NotifyIcon.ContextMenu.MenuItems.AddRange($ShowLog)

    # Menu Item 3: Stop Spiritbox
    $StopSpiritbox = New-Object System.Windows.Forms.MenuItem
    $StopSpiritbox.Text = "Stop Spiritbox"
    $StopSpiritbox.Add_Click({$NotifyIcon.Dispose(); Stop-Process $pid})
    $NotifyIcon.ContextMenu.MenuItems.AddRange($StopSpiritbox)

    # Show System Tray Icon and Form
    $NotifyIcon.Visible = $true
    Show-SpiritboxForm
    $ApplicationContext = New-Object System.Windows.Forms.ApplicationContext
    [System.Windows.Forms.Application]::Run($ApplicationContext)
}
