Param([switch]$ShowReport)

$Report = Get-Content .\report-example.json 

if ($ShowReport) {
  Write-Host $Report
} 

$Uri = "http://127.0.0.1:1337"
Invoke-RestMethod -Method POST -Uri $Uri -ContentType "application/json" -Body $Report 