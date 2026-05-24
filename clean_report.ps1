$file = "c:\Users\USER\Documents\NetBeansProjects\AssTry\AssTry\src\main\java\crs\services\ReportService.java"
$content = Get-Content $file -Raw -Encoding UTF8

$content = $content -replace '(?m)^\s*//.*$', ''
$content = $content -replace '/\*\*[\s\S]*?\*/', ''
$content = $content -replace '/\*[\s\S]*?\*/', ''
$content = $content -replace '(?m)^\s*$\r?\n', ''

[System.IO.File]::WriteAllText($file, $content, [System.Text.Encoding]::UTF8)
Write-Host "Removed comments from ReportService.java"
