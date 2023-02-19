function Invoke-Spiritbox {
    <#
        .REFERENCE
        https://lazyadmin.nl/powershell/powershell-gui-howto-get-started/
    #>
    try {
        $Config = Get-Content "config.json" | ConvertFrom-Json

        Add-Type -AssemblyName System.Windows.Forms
        Add-Type -AssemblyName System.Drawing
        $Form = New-Object System.Windows.Forms.Form
        $Form.ClientSize = "500,300"
        $Form.Text = "Spiritbox"
        $Form.Icon = New-Object System.Drawing.Icon("ghost.ico") 

        # Date: Date Picker
        $Date = New-Object System.Windows.Forms.Label
        $Date.Text = "Date"

        # Time: Time Picker
        $Time = New-Object System.Windows.Forms.Label
        $Time.Text = "Time"
        
        # Location: Drop-Down
        $LocationLabel = New-Object System.Windows.Forms.Label
        $LocationLabel.Text = "Location"
        $LocationField = New-Object System.Windows.Forms.ComboBox
        $Config.Locations | ForEach-Object {[void]$LocationField.Items.Add($_)}

        # Organization: Drop-Down
        $OrganizationLabel = New-Object System.Windows.Forms.Label
        $OrganizationLabel.Text = "Organization"
        $OrganizationField = New-Object System.Windows.Forms.ComboBox
        $Config.Organizations | ForEach-Object {[void]$OrganizationField.Items.Add($_)}

        # Activity: Drop-Down
        $ActivityLabel = New-Object System.Windows.Forms.Label
        $ActivityLabel.Text = "Activity"
        $ActivityField = New-Object System.Windows.Forms.ComboBox
        $Config.Activities | ForEach-Object {[void]$ActivityField.Items.Add($_)}

        # Source: Drop-Down
        $SourceLabel = New-Object System.Windows.Forms.Label
        $SourceLabel.Text = "Source"
        $SourceField = New-Object System.Windows.Forms.ComboBox
        $Config.Sources | ForEach-Object {[void]$SourceField.Items.Add($_)}

        # Attacker IP Address: Text box
        $AttackerIPAddress = New-Object System.Windows.Forms.Label
        $AttackerIPAddress.Text = "Attacker IP Address"

        # Victim IP Address: Text box
        $VictimIPAddress = New-Object System.Windows.Forms.Label
        $VictimIPAddress.Text = "Victim IP Address"

        $Form.Controls.AddRange(@(
            $Date, 
            $Time,
            $LocationLabel, $LocationField,
            $OrganizationLabel, $OrganizationField
            $ActivityLabel, $ActivityField
            $SourceLabel, $SourceField
            $AttackerIPAddress,
            $VictimIPAddress
        ))
        [void]$Form.ShowDialog()
    } catch  {
        Write-Output "[x] $_"
    }
}

Invoke-Spiritbox