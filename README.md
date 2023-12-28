## Run spring using docker

### Persyaratan sistem 
 - Pastikan anda memiliki Docker diinstal pada sistem Anda. [Instruksi instalasi Docker](https://docs.docker.com/get-docker/).
 - Pastikan anda menyesuaikan openjdk

### Langkah 1: Clone Repositori
  - git clone
  - cd Spring-boot-Elasticsearch

### Langkah 2: Build image docker
  - docker build -t springapi

### Langkah 3: Jalankan container
  - docker run -p 8080:8080 springapi

### Aplikasi sekarang dapat diakses melalui http://localhost:8080/
