## Guide to run this template

## Requirements:
- Mysql 8.0
- Redis 
- Cloudinary
- Mail

### Configuration ###
```
spring.application.name=SpringBoot
logging.level.org.springframework.security=DEBUG
springdoc.api-docs.path=/api-docs
```
### Config database ###
```
spring.datasource.url=MYSQL_URL
spring.datasource.username=MYSQL_USERNAME
spring.datasource.password=MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
logging.level.org.springframework.jdbc.core=DEBUG
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
```
### Virtual thread config ###
```
spring.threads.virtual.enabled=true
spring.main.keep-alive=true
```
### Security config ###
```
spring.security.user.name=YOUR_USERNAME
spring.security.user.password=YOUR_PASSWORD
```
### JWT config ###
```
jwt.secret=SECRET_KEY
jwt.expiration=30*1000
```
### Cloudinary config ###
```
spring.servlet.multipart.enabled=true
cloudinary.url=CLOUD_API_KEY
```
### Redis config ###
```
spring.data.redis.url=redis://localhost:6379/0
```
### Email config ###
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=NAME_MAIL
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
### Time config ###
```
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Asia/Ho_Chi_Minh
spring.jackson.serialization.write-dates-as-timestamps=false
```

## Folder structure guide line

Domain: contain business rules and data structures
Application: 
Infrastructure: 
Presentation (Api): 


