# Spring Boot Tus File Server
[Spring Boot](https://projects.spring.io/spring-boot/) and [Tus.io](http://tus.io) based file server.  
Why not build a file server that just works like FileZilla, but can be used by a web browser?  
And that supports SSL transfer and ip based ACL?

# Design Goal
This project aims to implement the following requirements:

- Strictly follow the [tus.io protocols](http://tus.io/protocols/resumable-upload.html)
- User friendly web interface
    - Read directory and file list
    - Upload files
        - Resumable
        - File queue functions
    - Download files
    - Delete files
- Spring security based authentication
- Ip based ACL
- File transferring queue
- SSL transfer if possible
- Server clustering
    - When server is clustered, file directory should be accessible by all clustered servers. Mounted drivers are good example.

# Quick Start
```
$ mvn spring-boot:run
```

# Quick Config
Configuration can be done by modifying the `application.properties` file.

- `server.port`: Application web server's port.
- `tus.binFilePath`: Place where tus binary files are stored. If Windows, use `\` for file separator.
- `tus.infoFilePath`: Place where tus info files are stored. If Windows, use `\` for file separator.

# References
- [tus.io](https://tus.io/)
- [tus.io implemented java servlet](https://github.com/terrischwartz/tus_servlet/)
- [Spring cloud lock sample](https://github.com/spring-cloud-samples/locks/)
