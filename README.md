Commodus
=========

DSH105's compilation of cool, sexy utils.

Feel free to add and contribute <3

Cheers [@stuntguy3000](https://github.com/stuntguy3000) for brainstorming the name :D

Using Commodus
=============

We use Maven 3 to build Commodus. Simply run the `mvn` command in the project root to compile the code and run the unit tests. You'll get a complete JAR in the `target/` directory with the dependencies appropriately shaded for you.

You can depend on Commodus by adding the following to your `pom.xml`:

```xml
<repository>
    <id>dsh-repo</id>
    <url>http://repo.dsh105.com/</url>
</repository>
```

```xml
<dependency>
    <groupId>com.dsh105</groupId>
    <artifactId>commodus</artifactId>
    <version>VERSION</version>
</dependency>
```

You will also need to **shade and relocate Commodus to your plugin's package** for your plugin to work correctly:

```xml
<plugins>
<!-- ... -->

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>2.1</version>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <minimizeJar>true</minimizeJar>
                    <createDependencyReducedPom>false</createDependencyReducedPom>
                    <!-- Artifacts to shade -->
                    <artifactSet>
                        <includes>
                            <artifact>com.dsh105:commodus</artifact>
                        </includes>
                    </artifactSet>
                    <!-- Relocate everything into the project package -->
                    <relocations>
                        <relocation>
                            <pattern>com.dsh105.commodus</pattern>
                            <shadedPattern>com.example.myplugin.commodus</shadedPattern>
                        </relocation>
                    </relocations>
                </configuration>
            </execution>
        </executions>
    </plugin>
    
<!-- ... -->
</plugins>
```

Dependencies
============

Commodus makes use of the following dependencies:
- **Optional:** CaptainBern's Reflection framework - not required, as alternatives will always be provided if this framework is not available
- **Required for com.dsh105.commodus.message.***: Google Gson - For the message API to function correctly, Gson must be present.
    - Note: if you do not make use of this package in your project, Maven's `minimizeJar` will exclude it for you.
- **Required for com.dsh105.commodus.sponge.*:** Sponge - The SpongeAPI must be present at runtime for these utilities to function correctly.
- **Required for com.dsh105.commodus.bukkit.*:** Bukkit - The Bukkit API must be present at runtime for these utilities to function correctly.

**Note:** Other utilities may require either Bukkit OR Sponge present at runtime in order to function correctly.