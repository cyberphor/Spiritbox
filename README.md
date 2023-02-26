# Spiritbox
![GitHub](https://img.shields.io/github/license/cyberphor/spiritbox)  
Spiritbox is a PowerShell module and User Interface (UI) for sending Indicators of Compromise (IOCs) to subscribers. 

![Screenshot](/screenshots/screenshot.png)

Below is an example of the JSON objects created by Spiritbox. The fields may change and grow. 
```json
{
  "threat.indicator.last_seen": "2023-02-22T22:53:02.000Z",
  "geo.name": "Ziwa",
  "organization.name": "Weyland-Yutani Corp",
  "threat.tactic.name": "Reconnaissance",
  "observer.type": "Firewall",
  "source.ip": "18.55.6.215",
  "destination.ip": "192.168.1.10",
  "threat.response.description": "None"
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
