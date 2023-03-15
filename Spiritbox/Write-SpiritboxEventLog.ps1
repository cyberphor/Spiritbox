# TODO: add option to send Spiritbox log to Elastic
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