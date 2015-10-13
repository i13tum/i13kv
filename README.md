#I13 KV

The goal of _I13 Key Value Store_ is to teach various socket handling techniques in Java. The only commands supported so far are `PUT table key value` and `GET table key`

Following socket handling techniques are implemented:
- Java 7 NIO
- Single threaded and blocking
- One thread per connection
- Threadpool

## Tests
`mvn test`

## Running
```
mvn assembly:assembly
java -jar target/niokv-jar-with-dependencies.jar
```

If you don't have maven installed you can run it from your IDE ([Eclipse](http://www.eclipse.org/), [IntelliJ](https://www.jetbrains.com/idea/))

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
- Additional operators (List, Search, [more ideas](http://redis.io/commands), ...)
- Reactive programming
- Notifications
- Persistence to disk
- ...







