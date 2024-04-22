# NoobWork : logs #

## Guide ##
### Init the library ###
To begin using this library, you need to specify the folder that will contain all the .log generated files. To do this, use the Log.setDirectory(File) function.
````java
import fr.dtn.noobwork.log.Log;

public class Main {
    public static void main(String[] args) {
        Log.setDirectory(new File("your folder path"));
    }
}
````
You can also use the Log.setDirectory(String) to specify only the path to the file and avoid creating a new File object.
````java
import fr.dtn.noobwork.log.Log;

public class Main {
    public static void main(String[] args) {
        Log.setDirectory("your folder path");
    }
}
````

### Use the library ###
- To print an informative message :
````java
import fr.dtn.noobwork.log.Log;

public class Main {
    public static void main(String[] args) {
        Log.setDirectory("your folder path");
        Log.info(object);
    }
}
````

- To print a warning message, use :
````java
import fr.dtn.noobwork.log.Log;

public class Main {
    public static void main(String[] args) {
        Log.setDirectory("your folder path");
        Log.warn(object);
    }
}
````

- To print an error message :
````java
import fr.dtn.noobwork.log.Log;

public class Main {
    public static void main(String[] args) {
        Log.setDirectory("your folder path");
        Log.error(object);
    }
}
````

## Technical details ##
### Display ###
The displayed message will be based on the following syntax :
```
[logType][HH:MM:SS][thread][class#method]: message
```
- logType : '?' for an information, '#' for a warning, '!' for an error

- HH : The hour when the log method is called
- MM : The minute when the log method is called
- SS : The second when the log method is called
- thread : The thread where the log method is called from
- class  : The class where the log method is called from
- method : The method where the log method is called from
- message : The message that you want to display

### Files ###
Each time any of the logs functions is called, the print is written in a file called : "year.month.day.log", with current date of call values.

### Basic PrintStream uses ###

The Log.info(Object) and Log.warn(Object) are equivalent to use the System.out.println(Object) method, but it also writes the print content into a file, and add a prefix fore more context.

Similarly, calling Log.error(Object) uses System.err.println(Object).

### Encoding ###
Currently, the logs does not support UTF-8, because I'm too dumb to understand why it does not work, I tried many things, but none of them had work (I'm bad I know).
