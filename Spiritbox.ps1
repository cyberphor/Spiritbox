function Invoke-Spiritbox {
    try {
        $Config = Get-Content "config.json" | ConvertFrom-Json

        Add-Type -AssemblyName System.Windows.Forms
        Add-Type -AssemblyName System.Drawing
        $Form = New-Object System.Windows.Forms.Form
        $Form.ClientSize = "400,500"
        $Form.Text = "Spiritbox"
        $Form.Icon = New-Object System.Drawing.Icon("$PSScriptRoot\ghost.ico")
        $Form.StartPosition = "CenterScreen"

        # Date
        $DateLabel = New-Object System.Windows.Forms.Label
        $DateLabel.Text = "Date"
        $DateLabel.Location = New-Object System.Drawing.Point(10,10)
        $DateLabel.Size = New-Object System.Drawing.Size(120,25)
        $Form.Controls.Add($DateLabel)

        $DateField = New-Object System.Windows.Forms.TextBox
        $DateField.Location = New-Object System.Drawing.Point(130,10)
        $Form.Controls.Add($DateField)
        $DateField.Size = New-Object System.Drawing.Size(260,25)

        # Time
        $TimeLabel = New-Object System.Windows.Forms.Label
        $TimeLabel.Text = "Time"
        $TimeLabel.Location = New-Object System.Drawing.Point(10,35)
        $Form.Controls.Add($TimeLabel)

        $TimeField = New-Object System.Windows.Forms.TextBox
        $TimeField.Location = New-Object System.Drawing.Point(130,35)
        $Form.Controls.Add($TimeField)
        $TimeField.Size = New-Object System.Drawing.Size(260,25)
        
        # Location
        $LocationLabel = New-Object System.Windows.Forms.Label
        $LocationLabel.Text = "Location"
        $LocationLabel.Location = New-Object System.Drawing.Point(10,60)
        $Form.Controls.Add($LocationLabel)

        $LocationField = New-Object System.Windows.Forms.ComboBox
        $Config.Locations | ForEach-Object {[void]$LocationField.Items.Add($_)}
        $LocationField.Location = New-Object System.Drawing.Point(130,60)
        $Form.Controls.Add($LocationField)
        $LocationField.Size = New-Object System.Drawing.Size(260,25)

        # Organization
        $OrganizationLabel = New-Object System.Windows.Forms.Label
        $OrganizationLabel.Text = "Organization"
        $OrganizationLabel.Location = New-Object System.Drawing.Point(10,85)
        $Form.Controls.Add($OrganizationLabel)

        $OrganizationField = New-Object System.Windows.Forms.ComboBox
        $Config.Organizations | ForEach-Object {[void]$OrganizationField.Items.Add($_)}
        $OrganizationField.Location = New-Object System.Drawing.Point(130,85)
        $Form.Controls.Add($OrganizationField)
        $OrganizationField.Size = New-Object System.Drawing.Size(260,25)

        # Activity
        $ActivityLabel = New-Object System.Windows.Forms.Label
        $ActivityLabel.Text = "Activity"
        $ActivityLabel.Location = New-Object System.Drawing.Point(10,110)
        $Form.Controls.Add($ActivityLabel)

        $ActivityField = New-Object System.Windows.Forms.ComboBox
        $Config.Activities | ForEach-Object {[void]$ActivityField.Items.Add($_)}
        $ActivityField.Location = New-Object System.Drawing.Point(130,110)
        $Form.Controls.Add($ActivityField)
        $ActivityField.Size = New-Object System.Drawing.Size(260,25)

        # Source
        $SourceLabel = New-Object System.Windows.Forms.Label
        $SourceLabel.Text = "Source"
        $SourceLabel.Location = New-Object System.Drawing.Point(10,135)
        $Form.Controls.Add($SourceLabel)

        $SourceField = New-Object System.Windows.Forms.ComboBox
        $Config.Sources | ForEach-Object {[void]$SourceField.Items.Add($_)}
        $SourceField.Location = New-Object System.Drawing.Point(130,135)
        $Form.Controls.Add($SourceField)
        $SourceField.Size = New-Object System.Drawing.Size(260,25)

        # Attacker IP Address
        $AttackerIPAddressLabel = New-Object System.Windows.Forms.Label
        $AttackerIPAddressLabel.Text = "Attacker IP Address"
        $AttackerIPAddressLabel.Location = New-Object System.Drawing.Point(10,160)
        $AttackerIPAddressLabel.Size = New-Object System.Drawing.Size(120,20)
        $Form.Controls.Add($AttackerIPAddressLabel)

        $AttackerIPAddressField = New-Object System.Windows.Forms.TextBox
        $AttackerIPAddressField.Location = New-Object System.Drawing.Point(130,160)
        $Form.Controls.Add($AttackerIPAddressField)
        $AttackerIPAddressField.Size = New-Object System.Drawing.Size(260,25)

        # Victim IP Address
        $VictimIPAddressLabel = New-Object System.Windows.Forms.Label
        $VictimIPAddressLabel.Text = "Victim IP Address"
        $VictimIPAddressLabel.Location = New-Object System.Drawing.Point(10,185)
        $Form.Controls.Add($VictimIPAddressLabel)

        $VictimIPAddressField = New-Object System.Windows.Forms.TextBox
        $VictimIPAddressField.Location = New-Object System.Drawing.Point(130,185)
        $Form.Controls.Add($VictimIPAddressField)
        $VictimIPAddressField.Size = New-Object System.Drawing.Size(260,25)

        # Actions Taken
        $ActionsTakenLabel = New-Object System.Windows.Forms.Label
        $ActionsTakenLabel.Text = "Actions Taken"
        $ActionsTakenLabel.Location = New-Object System.Drawing.Point(10,210)
        $Form.Controls.Add($ActionsTakenLabel)

        $ActionsTakenField = New-Object System.Windows.Forms.TextBox
        $ActionsTakenField.Location = New-Object System.Drawing.Point(10,235)
        $ActionsTakenField.Size = New-Object System.Drawing.Size(380,200)
        $ActionsTakenField.AcceptsReturn = $true
        $ActionsTakenField.Multiline = $true
        $Form.Controls.Add($ActionsTakenField)

        # Submit
        $SubmitButton = New-Object System.Windows.Forms.Button
        $SubmitButton.Text = "Submit"
        $SubmitButton.Location = New-Object System.Drawing.Point(10,440)
        $SubmitButton.Size = New-Object System.Drawing.Size(185,25)
        $Form.AcceptButton = $SubmitButton
        $Form.Controls.Add($SubmitButton)

        # Cancel
        $CancelButton = New-Object System.Windows.Forms.Button
        $CancelButton.Text = "Cancel"
        $CancelButton.Location = New-Object System.Drawing.Point(190,440)
        $CancelButton.Size = New-Object System.Drawing.Size(200,25)
        $Form.CancelButton = $CancelButton
        $Form.Controls.Add($CancelButton)

        [void]$Form.ShowDialog()
    } catch  {
        Write-Output "[x] $_"
    }
}

Invoke-Spiritbox