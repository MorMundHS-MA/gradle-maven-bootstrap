$jarsDirectory = Resolve-Path -Path "subprojects/distributions-full/build/distributions/gradle-*/lib/plugins"

$gradleJars = Get-ChildItem -File -Filter "gradle*.jar" -Path $jarsDirectory

$gradleJars | ForEach-Object {
  if($_.BaseName -match "gradle-(.*)-\d+\.\d+") {
    $moduleName = $matches[1]
  } else {
    throw "unexpected jar name"
  }

  $moduleSource = Get-ChildItem -Recurse -Directory -Filter $moduleName
  if($moduleSource.Length -ne 1) {
    Write-Warning "$($moduleSource.Length) candidates found for $moduleName"

    $matchingPomDir = "";
    for ($i = 0; $i -lt $moduleSource.Length; $i++) {
      $candidatePath = $moduleSource[$i]
      $pomPath = Join-Path -Path $candidatePath -ChildPath "pom.xml"
      if(Test-Path -Path $pomPath) {
        Write-Host "Candidate pom.xml found for $moduleName"
      } else {
        continue
      }
      if(((Get-Content -Path $pomPath) -match "^  <artifactId>gradle-$moduleName</artifactId>") ) {
        if($matchingPomDir -ne "") {
          Write-Error "Multiple pom.xml candidates found for $moduleName"
          $matchingPomDir = ""
          break
        }

        Write-Host "Match found in pom.xml for $moduleName"
        $matchingPomDir = $candidatePath
      }
    }

    if($matchingPomDir -ne "") {
      Write-Warning "Found exactly one matching pom.xml for $moduleName in $matchingPomDir"
      $moduleSource = $matchingPomDir
    } else {
      Write-Error "No suitable matches found for $moduleName"
      return
    }
  }

  $propContent = unzip -q -c $_.FullName "gradle-$moduleName-classpath.properties"
  $moduleResourcePath = Join-Path -Path $moduleSource -ChildPath "src/main/resources"
  $resourceDir = New-Item -Path $moduleResourcePath -ItemType Directory -Force
  $propResourcePath = Join-Path -Path $resourceDir.FullName -ChildPath "gradle-$moduleName-classpath.properties"
  $propContent > $propResourcePath
  Write-Host "Written $propResourcePath"
}
