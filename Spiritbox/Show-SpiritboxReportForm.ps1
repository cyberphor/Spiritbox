function Show-SpiritboxReportForm {
  # Form
  $Form = New-Object System.Windows.Forms.Form
  $Form.ClientSize = "400,670" # Width: 400, Height: 500
  $Form.MaximizeBox = $false # disable maximizing
  $Form.FormBorderStyle = "Fixed3D" # disable resizing
  $Form.Text = "Spiritbox"
  $Form.Icon = $Icon # TODO: add error handling
  $Form.StartPosition = "CenterScreen"

  # Date Label
  $DateLabel = New-Object System.Windows.Forms.Label
  $DateLabel.Text = "Date"
  $DateLabel.Size = "120,25"
  $DateLabel.Location = "10,10"
  $Form.Controls.Add($DateLabel)

  # Date Field
  $DateField = New-Object System.Windows.Forms.DateTimePicker
  $DateField.Name = "Date"
  $DateField.Size = "260,25"
  $DateField.Location = "130,10"
  $DateField.Format = [Windows.Forms.DateTimePickerFormat]::Custom 
  $DateField.CustomFormat = "yyyy-MM-dd"
  $Form.Controls.Add($DateField)

  # Time Label
  $TimeLabel = New-Object System.Windows.Forms.Label
  $TimeLabel.Text = "Time"
  $TimeLabel.Location = "10,35"
  $Form.Controls.Add($TimeLabel)

  # Time Field
  $TimeField = New-Object System.Windows.Forms.DateTimePicker
  $TimeField.Name = "Time" 
  $TimeField.Size = "260,25"
  $TimeField.Location = "130,35"
  $TimeField.Format = [Windows.Forms.DateTimePickerFormat]::Custom 
  $TimeField.CustomFormat = "HH:mm:ss"
  $TimeField.ShowUpDown = $true
  $Form.Controls.Add($TimeField)

  # Organization Label
  $OrganizationLabel = New-Object System.Windows.Forms.Label
  $OrganizationLabel.Text = "Organization"
  $OrganizationLabel.Location = "10,85"
  $Form.Controls.Add($OrganizationLabel)

  # Organization Field
  $OrganizationField = New-Object System.Windows.Forms.ComboBox
  $OrganizationField.Name = "Organization"
  $OrganizationField.Size = "260,25"
  $OrganizationField.Location = "130,85"
  $Config.Organizations | ForEach-Object {[void]$OrganizationField.Items.Add($_)} # void is used so no output is returned during each add
  $OrganizationField.SelectedIndex = 0
  $Form.Controls.Add($OrganizationField)

  # Location Label
  $LocationLabel = New-Object System.Windows.Forms.Label
  $LocationLabel.Text = "Location"
  $LocationLabel.Location = "10,60"
  $Form.Controls.Add($LocationLabel)

  # Location Field
  $LocationField = New-Object System.Windows.Forms.ComboBox
  $LocationField.Name = "Location"
  $LocationField.Size = "260,25"
  $LocationField.Location = "130,60"
  $Config.Locations | ForEach-Object {[void]$LocationField.Items.Add($_)} # void is used so no output is returned during each add
  $LocationField.SelectedIndex = 0
  $Form.Controls.Add($LocationField)

  # Observation Label
  $ObservationLabel = New-Object System.Windows.Forms.Label
  $ObservationLabel.Text = "Observation"
  $ObservationLabel.Location = "10,110"
  $Form.Controls.Add($ObservationLabel)

  # Observation Field
  $ObservationField = New-Object System.Windows.Forms.ComboBox
  $ObservationField.Name = "Observation"
  $ObservationField.Size = "260,25"
  $ObservationField.Location = "130,110"
  $Config.ObservationTypes | ForEach-Object {[void]$ObservationField.Items.Add($_)} # void is used so no output is returned during each add
  $ObservationField.SelectedIndex = 0
  $Form.Controls.Add($ObservationField)

  # Indicators DataGridView
  $IndicatorsDataGridView = New-Object System.Windows.Forms.DataGridView
  $IndicatorsDataGridView.Size = "380,245"
  $IndicatorsDataGridView.Location = "10,145"
  $IndicatorsDataGridView.RowHeadersVisible = $false # don't show first column
  $IndicatorsDataGridView.AllowUserToAddRows = $false
  $IndicatorsDataGridView.AllowUserToResizeRows = $false
  $IndicatorsDataGridView.ColumnHeadersVisible = $true
  $IndicatorsDataGridView.AutoSizeColumnsMode = "Fill"

  # Indicators DataGridView: Observer Types Column
  $ObserverTypes = $Config.ObserverTypes
  $ObserverTypesColumn = New-Object System.Windows.Forms.DataGridViewComboBoxColumn
  $ObserverTypesColumn.Name = "Observer Type"
  $ObserverTypesColumn.DataSource = $ObserverTypes
  $ObserverTypesColumn.DefaultCellStyle.NullValue = $ObserverTypes[0]
  $IndicatorsDataGridView.Columns.Add($ObserverTypesColumn) | Out-Null # Out-Null is used so no output is returned during the add

  # Indicators DataGridView: Indicator Types Column
  $IndicatorTypes = $Config.Indicators.IndicatorType
  $IndicatorTypesColumn = New-Object System.Windows.Forms.DataGridViewComboBoxColumn
  $IndicatorTypesColumn.Name = "Indicator Type"
  $IndicatorTypesColumn.DataSource = $IndicatorTypes
  $IndicatorTypesColumn.DefaultCellStyle.NullValue = $IndicatorTypes[0]
  $IndicatorsDataGridView.Columns.Add($IndicatorTypesColumn) | Out-Null # Out-Null is used so no output is returned during the add


  # Indicators DataGridView: Indicator Values Column
  $IndicatorValuesColumn = New-Object System.Windows.Forms.DataGridViewTextBoxColumn
  $IndicatorValuesColumn.Name = "Indicator Value"
  $IndicatorsDataGridView.Columns.Add($IndicatorValuesColumn) | Out-Null # Out-Null is used so no output is returned during the add
  # TODO: add ability to copy/paste from a spreadsheet

  # Add Indicators DataGridView to Form
  $IndicatorsDataGridView.RowCount = 10
  $Form.Controls.Add($IndicatorsDataGridView)

  # Actions Taken Label
  $ActionsTakenLabel = New-Object System.Windows.Forms.Label
  $ActionsTakenLabel.Text = "Actions Taken"
  $ActionsTakenLabel.Location = "10,400" 
  $Form.Controls.Add($ActionsTakenLabel)

  # Actions Taken Field
  $ActionsTakenField = New-Object System.Windows.Forms.TextBox
  $ActionsTakenField.Name = "ActionsTaken"
  $ActionsTakenField.Size = "380,200"
  $ActionsTakenField.Location = "10,425"
  $ActionsTakenField.Multiline = $true
  $ActionsTakenField.AcceptsReturn = $true
  $Form.Controls.Add($ActionsTakenField)

  # Submit Button
  $SubmitButton = New-Object System.Windows.Forms.Button
  $SubmitButton.Text = "Submit"
  $SubmitButton.Size = "185,25"
  $SubmitButton.Location = "10,630"
  $SubmitButton.Add_Click({
      New-SpiritboxReport -Form $Form | Submit-SpiritboxReport 
      Reset-SpiritboxReportForm -Form $Form
  })
  $Form.Controls.Add($SubmitButton)

  # Reset Button
  $ResetButton = New-Object System.Windows.Forms.Button
  $ResetButton.Text = "Reset"
  $ResetButton.Size = "200,25"
  $ResetButton.Location = "190,630"
  $ResetButton.Add_Click({
      Reset-SpiritboxReportForm -Form $Form
  }) 
  $Form.Controls.Add($ResetButton)

  # Show Form
  $Form.ShowDialog() | Out-Null # Send dialog output (e.g., which button was pressed) to null
}