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
        [Parameter(Mandatory)][ValidateSet("DEBUG","INFORMATIONAL","NOTICE","WARNING","ERROR","CRITICAL","ALERT","EMERGENCY")]$SeverityLevel,
        [Parameter(Mandatory)][string]$Message
    )
    $Date = Get-Date
    $Time = $Date.ToString("yyyy-MM-ddTHH:mm:ss.fffZ") 
    $SpiritboxEventLog = "spiritbox-" + $Date.ToString("yyyy-MM-dd") + ".log"
    $SpiritboxEvent = $Time, $SeverityLevel, $Message | ForEach-Object { "[" + $_ + "]" }
    $SpiritboxEvent -join " " | Out-File -Append -FilePath $SpiritboxEventLog
}

function Show-SpiritboxError {
    Param([string]$Message)
    # TODO: add Spiritbox icon and make "Error" the label inside the dialog box
    $Button = [System.Windows.Forms.MessageBoxButtons]::OK
    $Icon = [System.Windows.Forms.MessageBoxIcon]::Error
    [System.Windows.Forms.MessageBox]::Show($Message, "Error", $Button, $Icon) | Out-Null
}

function Test-IPAddress {
    Param([string]$IPAddress)
    try {
        return $IPAddress -match ($PSObject = [ipaddress]$IPAddress)
    } catch {
        return $false
    }
}

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
                #$SubscriberResponse = Invoke-RestMethod -Method POST -Uri $Uri -ContentType "application/json" -Body $Body
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
            #Start-Sleep -Seconds $Seconds
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
        # TODO: add logic to clear DataGridView cells
        }
    }
    $Form.Refresh()
}

function New-SpiritboxReport {
    Param([System.Windows.Forms.Form]$Form)

    # Report
    $ReportDetails = [ordered]@{}
    $ReportHasNoErrors = $true

    # TODO: mirror log examples provided in link below 
    # - https://www.elastic.co/guide/en/ecs/current/ecs-threat-usage.html

    # Combine Date and Time into an Elastic-friendly @timestamp value
    $Date = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.DateTimePicker]) -and ($_.Name -eq "date") }).Text
    $Time = ($Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.DateTimePicker]) -and ($_.Name -eq "time") }).Text
    $Timestamp = $Date + "T" + $Time + ".000Z"
    $ReportDetails.Add("threat.indicator.last_seen", $Timestamp)

    # Location, Organization, Activity
    $Form.Controls | Where-Object { $_ -is [System.Windows.Forms.ComboBox] } |  
    ForEach-Object { $ReportDetails.Add($_.Name, $_.Text) }

    # Actions Taken
    $Form.Controls | Where-Object { ($_ -is [System.Windows.Forms.TextBox]) -and ($_.Name -eq "threat.response.description") } | 
    ForEach-Object { $ReportDetails.Add($_.Name, $_.Text) }

    # Observer Type, Indicator Type, Indicator Values
    $Indicators = New-Object System.Collections.Generic.List[System.Collections.Hashtable]
    ($Form.Controls | Where-Object { $_ -is [System.Windows.Forms.DataGridView] }).Rows | 
    ForEach-Object {
        # TODO: add logic to check if IndicatorValue is of IndicatorType
        # TODO: add logic to map IndicatorType to threat.indicator.ip, threat.indicator.url, etc. 
        # TODO: ensure at least one indicator value is given
        $Indicator = [ordered]@{}
        $ObserverType = $_.Cells[0].FormattedValue
        $IndicatorType = $_.Cells[1].FormattedValue
        $IndicatorValue = $_.Cells[2].FormattedValue

        if (-not [string]::IsNullorEmpty($IndicatorValue)) {
            $Indicator.Add("observer.type", $ObserverType)
            $Indicator.Add("indicator.type", $IndicatorType)
            if ($IndicatorType -eq "ipv4-addr") {
                if (Test-IPAddress($IndicatorValue)) {
                    $Indicator.Add("indicator.value", $IndicatorValue)
                } else {
                    $ReportHasNoErrors = $false
                    $Message = "An invalid IP address was specified: " + $IndicatorValue
                    Write-SpiritboxEventLog -SeverityLevel ERROR -Message $Message
                    Show-SpiritboxError -Message $Message
                }
            }
            $Indicators.Add($Indicator)
        }
    }
    $ReportDetails.Add("indicators", $Indicators)

    # Return Report
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
    $IndicatorsDataGridView.Columns.Add($ObserverTypesColumn) | Out-Null # Out-Null is used so no output is returned during the add

    # Indicators DataGridView: Indicator Types Column
    $IndicatorTypes = $Config.Indicators.IndicatorType
    $IndicatorTypesColumn = New-Object System.Windows.Forms.DataGridViewComboBoxColumn
    $IndicatorTypesColumn.Name = "Indicator Type"
    $IndicatorTypesColumn.DataSource = $IndicatorTypes
    $IndicatorTypesColumn.DefaultCellStyle.NullValue = $IndicatorTypes[0]
    $IndicatorsDataGridView.Columns.Add($IndicatorTypesColumn) | Out-Null # Out-Null is used so no output is returned during the add
    # TODO: add ability to copy/paste from a spreadsheet

    # Indicators DataGridView: Indicator Values Column
    $IndicatorValues = $Config.Indicators.ValueType
    $IndicatorValuesColumn = New-Object System.Windows.Forms.DataGridViewTextBoxColumn
    $IndicatorValuesColumn.Name = "Indicator Value"
    $IndicatorsDataGridView.Columns.Add($IndicatorValuesColumn) | Out-Null # Out-Null is used so no output is returned during the add
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
        New-SpiritboxReport -Form $Form | Submit-SpiritboxReport 
        Reset-SpiritboxForm -Form $Form
    })
    $Form.Controls.Add($SubmitButton)

    # Reset Button
    $ResetButton = New-Object System.Windows.Forms.Button
    $ResetButton.Text = "Reset"
    $ResetButton.Size = "200,25"
    $ResetButton.Location = "190,630"
    $ResetButton.Add_Click({
        Reset-SpiritboxForm -Form $Form
    }) 
    $Form.Controls.Add($ResetButton)

    # Show Form
    $Form.ShowDialog() | Out-Null # Send dialog output (e.g., which button was pressed) to null
}

function Start-Spiritbox {
    # TODO: open CLI > import > start > a child process is created and spiritbox is added to system tray
    # Start-Process -WindowStyle Hidden powershell.exe "Import-Module spiritbox; Start-Spiritbox"
    # https://www.systanddeploy.com/2018/12/create-your-own-powershell.html

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
