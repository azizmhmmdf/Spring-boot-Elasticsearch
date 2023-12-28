## Run spring using docker

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

### Langkah 3: Build image docker
  - docker build -t springapi

### Langkah 4: Jalankan container
  - docker run -p 8080:8080 springapi

### Aplikasi sekarang dapat diakses melalui http://localhost:8080/
