# Spiritbox
![GitHub](https://img.shields.io/github/license/cyberphor/spiritbox)  
Spiritbox is a PowerShell module and Graphical User Interface (GUI) for sending Indicators of Compromise (IOCs) to Elastic. 

![Screenshot](/screenshots/screenshot.png)

Below is an example of the JSON documents created by Spiritbox. The fields may change or grow. 
```json
{
  "@timestamp": "2023-03-10T11:09:23.000Z",
  "event": {
      "kind": "enrichment", 
      "category": "threat", 
      "type": "indicator"
  },
  "geo": {
    "name": "Ziwa"
  },
  "threat": {
    "marking": {
        "tlp": "GREEN"
      },
    "feed": {
      "name": "Spiritbox",
      "reference": "https://github.com/cyberphor/Spiritbox"
    },
    "tactic": {
      "name": "Reconnaissance"
    },
    "indicator": { 
      "provider": "Weyland-Yutani Corp",
      "last_seen": "2023-03-03T17:25:47.000Z",
      "type": [
        "ipv4-addr",
        "port",
        "domain-name",
        "email-addr"
      ],
      "ip": [
        "1.2.3.4",
        "2.2.2.2",
        "3.4.3.4"
      ],
      "port": [
        443
      ],
      "domain": [
        "malicious.evil",
        "malware.net"
      ],
      "email.address": [
        "phisherman@malicious.evil",
        "apt@nationstate.edu"
      ]
    },
    "response": "None"
  }
}
```

### Screenshots
**Input Validation**  
![Input Validation](/screenshots/screenshot-input-validation.png)

**Progress Bar**  
![Progress Bar](/screenshots/screenshot-progress-bar.png)

**Progress Bar Error**  
![Progress Bar Error](/screenshots/screenshot-progress-bar-error.png)

### Copyright
This project is licensed under the terms of the [MIT license](/LICENSE). The [ghost icon](/ghost.ico) was created by <a href="https://www.flaticon.com/free-icons/ghost" title="ghost icons">Freepik (Flaticon)</a>.
