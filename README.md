## Run spring with docker

### Persyaratan sistem 
 - Pastikan anda memiliki Docker diinstal pada sistem Anda. [Instruksi instalasi Docker](https://docs.docker.com/get-docker/).
 - Pastikan anda menyesuaikan openjdk

### Langkah 1: Clone Repositori
  - git clone https://github.com/azizmuhammadfikhri123/Spring-boot-Elasticsearch.git
  - cd Spring-boot-Elasticsearch

### Langkah 2: Build file .jar
  - Pastikan Maven sudah terinstal di sistem Anda.
  - Buka terminal dan pindah ke direktori proyek Spring Boot.
  - Jalankan perintah berikut untuk membersihkan proyek dan membangun file JAR:
     - mvn clean install

### Langkah 3: Build Image Docker
  - docker build -t springapi

### Langkah 4: Create Container
  - docker container create [NAME OPTIONAL] -P 8080:8080 [NAME IMAGE]
  - docker container create --name=crud_spring -p 8080:8080 springapi

### Langkah 5: Run Container 
  - docker container start [NAME CONTAINER]
  - docker container start crud_spring

### Aplikasi sekarang dapat diakses melalui http://localhost:8080/

### Langkah 6: Stop Container 
  - docker container stop [NAME CONTAINER]
  - docker container stop crud_spring


