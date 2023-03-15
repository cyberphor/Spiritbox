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

# Import Spiritbox functions
. $PSScriptRoot\Spiritbox\New-SpiritboxReport.ps1
. $PSScriptRoot\Spiritbox\Reset-SpiritboxReportForm.ps1
. $PSScriptRoot\Spiritbox\Show-SpiritboxBanner.ps1
. $PSScriptRoot\Spiritbox\Show-SpiritboxError.ps1
. $PSScriptRoot\Spiritbox\Show-SpiritboxEventLog.ps1
. $PSScriptRoot\Spiritbox\Show-SpiritboxReportForm.ps1
. $PSScriptRoot\Spiritbox\Start-Spiritbox.ps1
. $PSScriptRoot\Spiritbox\Submit-SpiritboxReport.ps1
. $PSScriptRoot\Spiritbox\Test-IPAddress.ps1
. $PSScriptRoot\Spiritbox\Write-SpiritboxEventLog.ps1

# Show banner
Show-SpiritboxBanner