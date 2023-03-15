function Test-IPAddress {
  Param([string]$IPAddress)
  try {
      return $IPAddress -match ($PSObject = [ipaddress]$IPAddress)
  } catch {
      return $false
  }
}