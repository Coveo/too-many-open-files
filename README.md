Step to reproduce :

<ol>
<li>Checkout this repository
        <pre>git checkout https://github.com/Coveo/too-many-open-files.git</pre>
</li>
<li>No need to modify anything, simply build the executable jar : 
        <pre>mvn clean install</pre>
</li>
<li>Launch an EC2 instance, SSH on it and deploy the jar</li>
<li>Set file descriptors limit low for testing purposes :
        <pre>ulimit -n 100</pre>
</li>
<li>Run the jar :
        <pre>java -jar file-descriptors-leak-1.0-SNAPSHOT.jar</pre>
</li>
<li>Wait a couple of minutes until : 
        <pre>Error setting/closing connection: Too many open files.</pre>
</li>
</ol>

Environment details

    $ java -version
    java version "1.7.0_79"
    OpenJDK Runtime Environment (amzn-2.5.5.1.59.amzn1-x86_64 u79-b14)
    OpenJDK 64-Bit Server VM (build 24.79-b02, mixed mode)

    $ uname -a
    Linux ip-xxx-xxx-xxx-xxxx 3.14.35-28.38.amzn1.x86_64 #1 SMP Wed Mar 11 22:50:37 UTC 2015 x86_64 x86_64 x86_64 GNU/Linux
