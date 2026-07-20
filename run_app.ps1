[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip" -OutFile "maven.zip"
Expand-Archive -Path maven.zip -DestinationPath .\maven_local -Force
.\maven_local\apache-maven-3.9.5\bin\mvn.cmd spring-boot:run
