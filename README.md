#I13 KV

[![Build status](https://travis-ci.org/i13tum/i13kv.svg?branch=master)](https://travis-ci.org/i13tum/i13kv)

The goal of _I13 Key Value Store_ is to teach various socket handling techniques in Java. The only commands supported so far are `PUT, GET, DELETE, EXISTS`

Following socket handling techniques are implemented:
- Java 7 NIO
- Single threaded and blocking
- One thread per connection
- Threadpool

## Install Java and Maven

Maven is optional. You only need it if you want to run the build and tests from commandline.
You can use an IDE ([Eclipse](http://www.eclipse.org/), [IntelliJ IDEA](https://www.jetbrains.com/student/))

Install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Tests
`mvn test`

## Running
```
mvn assembly:assembly
java -jar target/niokv-jar-with-dependencies.jar
```


## Connect via telnet (Linux)
Install telnet `apt-get install telnet`

```
user@machine: telnet
telnet> open localhost 5559
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
PUT turingawards 2013 LeslieLamport
GET turingawards 2013
```

## Connect via telnet (Windows)
[Install telnet client](http://windows.microsoft.com/en-us/windows/telnet-faq#1TC=windows-7)
Windows + R, telnet, Enter

```
telnet> open localhost 5559
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
PUT turingawards 2013 LeslieLamport
GET turingawards 2013
```

## Contributing
Pull requests welcome!

Ideas:
- Additional operators (INC, FLUSHALL, ... [more ideas](http://redis.io/commands), ...)
- Reactive programming
- Notifications
- Persistence to disk
- Client
- HTTP interface
- ...







