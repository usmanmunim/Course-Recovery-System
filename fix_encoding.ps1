$file = "c:\Users\USER\Documents\NetBeansProjects\AssTry\AssTry\src\main\java\crs\services\ReportService.java"
$content = Get-Content $file -Raw
$utf8NoBom = New-Object System.Text.UTF8Encoding $false
[System.IO.File]::WriteAllText($file, $content, $utf8NoBom)
Write-Host "Fixed encoding for ReportService.java"
