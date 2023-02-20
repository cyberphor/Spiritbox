function Submit-Report([System.Windows.Forms.Form]$Form) {
    $Report = @{ }
    $Form.Controls | 
    Where-Object { ($_ -isnot [System.Windows.Forms.Label]) -and ($_ -isnot [System.Windows.Forms.Button]) } |
    ForEach-Object {
        $Field = $_.Name + ": " + $_.Text
        Write-Host $Field
    }
}

function Clear-Form([System.Windows.Forms.Form]$Form) {
    $Form.Controls | 
    Where-Object { ($_ -isnot [System.Windows.Forms.Label]) -and ($_ -isnot [System.Windows.Forms.Button]) } |
    ForEach-Object {
        if ($_ -is [System.Windows.Forms.ComboBox]) {
            $_.Items.Clear()
        } else {
            $_.Clear()
        }
    }
}

function Show-Form {
    $Config = Get-Content "config.json" | ConvertFrom-Json

    Add-Type -AssemblyName System.Windows.Forms
    Add-Type -AssemblyName System.Drawing
    $Form = New-Object System.Windows.Forms.Form
    $Form.ClientSize = "400,500" # Width: 400, Height: 500
    $Form.MaximizeBox = $false # disable maximizing
    $Form.FormBorderStyle = "Fixed3D" # disable resizing
    $Form.Text = "Spiritbox"
    $Form.Icon = New-Object System.Drawing.Icon("$PSScriptRoot\ghost.ico")
    $Form.StartPosition = "CenterScreen"

    # Date
    $DateLabel = New-Object System.Windows.Forms.Label
    $DateLabel.Text = "Date"
    $DateLabel.Size = New-Object System.Drawing.Size(120,25)
    $DateLabel.Location = New-Object System.Drawing.Point(10,10)
    $Form.Controls.Add($DateLabel)

    $DateField = New-Object System.Windows.Forms.TextBox
    $DateField.Name = "Date"
    $DateField.Size = New-Object System.Drawing.Size(260,25)
    $DateField.Location = New-Object System.Drawing.Point(130,10)
    $Form.Controls.Add($DateField)

    # Time
    $TimeLabel = New-Object System.Windows.Forms.Label
    $TimeLabel.Text = "Time"
    $TimeLabel.Location = New-Object System.Drawing.Point(10,35)
    $Form.Controls.Add($TimeLabel)

    $TimeField = New-Object System.Windows.Forms.TextBox
    $TimeField.Name = "Time"
    $TimeField.Size = New-Object System.Drawing.Size(260,25)
    $TimeField.Location = New-Object System.Drawing.Point(130,35)
    $Form.Controls.Add($TimeField)
            
    # Location
    $LocationLabel = New-Object System.Windows.Forms.Label
    $LocationLabel.Text = "Location"
    $LocationLabel.Location = New-Object System.Drawing.Point(10,60)
    $Form.Controls.Add($LocationLabel)

    $LocationField = New-Object System.Windows.Forms.ComboBox
    $LocationField.Name = "Location"
    $LocationField.Size = New-Object System.Drawing.Size(260,25)
    $LocationField.Location = New-Object System.Drawing.Point(130,60)
    $Config.Locations | ForEach-Object {[void]$LocationField.Items.Add($_)}
    $Form.Controls.Add($LocationField)

    # Organization
    $OrganizationLabel = New-Object System.Windows.Forms.Label
    $OrganizationLabel.Text = "Organization"
    $OrganizationLabel.Location = New-Object System.Drawing.Point(10,85)
    $Form.Controls.Add($OrganizationLabel)

    $OrganizationField = New-Object System.Windows.Forms.ComboBox
    $OrganizationField.Name = "Organization"
    $OrganizationField.Size = New-Object System.Drawing.Size(260,25)
    $OrganizationField.Location = New-Object System.Drawing.Point(130,85)
    $Config.Organizations | ForEach-Object {[void]$OrganizationField.Items.Add($_)}
    $Form.Controls.Add($OrganizationField)

    # Activity
    $ActivityLabel = New-Object System.Windows.Forms.Label
    $ActivityLabel.Text = "Activity"
    $ActivityLabel.Location = New-Object System.Drawing.Point(10,110)
    $Form.Controls.Add($ActivityLabel)

    $ActivityField = New-Object System.Windows.Forms.ComboBox
    $ActivityField.Name = "Activity"
    $ActivityField.Size = New-Object System.Drawing.Size(260,25)
    $ActivityField.Location = New-Object System.Drawing.Point(130,110)
    $Config.Activities | ForEach-Object {[void]$ActivityField.Items.Add($_)}
    $Form.Controls.Add($ActivityField)

    # Source
    $SourceLabel = New-Object System.Windows.Forms.Label
    $SourceLabel.Text = "Source"
    $SourceLabel.Location = New-Object System.Drawing.Point(10,135)
    $Form.Controls.Add($SourceLabel)

    $SourceField = New-Object System.Windows.Forms.ComboBox
    $SourceField.Name = "Source"
    $SourceField.Size = New-Object System.Drawing.Size(260,25)
    $SourceField.Location = New-Object System.Drawing.Point(130,135)
    $Config.Sources | ForEach-Object {[void]$SourceField.Items.Add($_)}
    $Form.Controls.Add($SourceField)

    # Attacker IP Address
    $AttackerIPAddressLabel = New-Object System.Windows.Forms.Label
    $AttackerIPAddressLabel.Text = "Attacker IP Address"
    $AttackerIPAddressLabel.Size = New-Object System.Drawing.Size(120,20)
    $AttackerIPAddressLabel.Location = New-Object System.Drawing.Point(10,160)
    $Form.Controls.Add($AttackerIPAddressLabel)

    $AttackerIPAddressField = New-Object System.Windows.Forms.TextBox
    $AttackerIPAddressField.Name = "AttackerIpAddress"
    $AttackerIPAddressField.Size = New-Object System.Drawing.Size(260,25)
    $AttackerIPAddressField.Location = New-Object System.Drawing.Point(130,160)
    $Form.Controls.Add($AttackerIPAddressField)

    # Victim IP Address
    $VictimIPAddressLabel = New-Object System.Windows.Forms.Label
    $VictimIPAddressLabel.Text = "Victim IP Address"
    $VictimIPAddressLabel.Location = New-Object System.Drawing.Point(10,185)
    $Form.Controls.Add($VictimIPAddressLabel)

    $VictimIPAddressField = New-Object System.Windows.Forms.TextBox
    $VictimIPAddressField.Name = "VictimIpAddress"
    $VictimIPAddressField.Size = New-Object System.Drawing.Size(260,25)
    $VictimIPAddressField.Location = New-Object System.Drawing.Point(130,185)
    $Form.Controls.Add($VictimIPAddressField)

    # Actions Taken
    $ActionsTakenLabel = New-Object System.Windows.Forms.Label
    $ActionsTakenLabel.Text = "Actions Taken"
    $ActionsTakenLabel.Location = New-Object System.Drawing.Point(10,210)
    $Form.Controls.Add($ActionsTakenLabel)

    $ActionsTakenField = New-Object System.Windows.Forms.TextBox
    $ActionsTakenField.Name = "ActionsTaken"
    $ActionsTakenField.Size = New-Object System.Drawing.Size(380,200)
    $ActionsTakenField.Location = New-Object System.Drawing.Point(10,235)
    $ActionsTakenField.Multiline = $true
    $ActionsTakenField.AcceptsReturn = $true
    $Form.Controls.Add($ActionsTakenField)

    # Submit
    $SubmitButton = New-Object System.Windows.Forms.Button
    $SubmitButton.Text = "Submit"
    $SubmitButton.Size = New-Object System.Drawing.Size(185,25)
    $SubmitButton.Location = New-Object System.Drawing.Point(10,440)
    $SubmitButton.Add_Click({Send-Report($Form)})
    $Form.Controls.Add($SubmitButton)

    # Cancel
    $CancelButton = New-Object System.Windows.Forms.Button
    $CancelButton.Text = "Cancel"
    $CancelButton.Size = New-Object System.Drawing.Size(200,25)
    $CancelButton.Location = New-Object System.Drawing.Point(190,440)
    $CancelButton.Add_Click({Clear-Form($Form)})
    $Form.Controls.Add($CancelButton)

    $Form.ShowDialog()
}

Show-Form