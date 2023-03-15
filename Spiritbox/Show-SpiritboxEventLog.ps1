function Show-SpiritboxEventLog {
  Get-ChildItem -Filter "spiritbox*.log" | 
  Sort-Object -Property LastWriteTime -Descending |
  Select-Object -First 1 |
  Invoke-Item
}