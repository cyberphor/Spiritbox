Param(
  [Parameter(Mandatory)]
  [ValidateSet("Object","File")]$Send,
  [switch]$ShowOnly
)

$Object = '{
  "threat.indicator.last_seen": "2023-03-10T22:53:02.000Z",
  "geo.name": "Ziwa",
  "organization.name": "Weyland-Yutani Corp",
  "threat.tactic.name": "Reconnaissance",
  "observer.type": "Firewall",
  "source.ip": "18.55.6.215",
  "destination.ip": "192.168.1.10",
  "threat.response.description": "None"
}'

$File = Get-Content .\report-example.json 

if ($Send -eq "Object") {
  $Report = $Object | ConvertFrom-Json 
} elseif ($Send -eq "File") {
  $Report = $File | ConvertFrom-Json

}

if (-not $ShowOnly) {
  Write-Host $Report
  $Uri = "http://127.0.0.1:1337"
  Invoke-RestMethod -Method POST -Uri $Uri -ContentType "application/json" -Body $Report
} else {
  Write-Host $Report
}