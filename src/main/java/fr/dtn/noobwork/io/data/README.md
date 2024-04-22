Here are all the formats supported in the library :
- Json (.json)
- Excel (.xls / .xlsx)

# I - Json (.json) #
## A - Create or load a Json ##
### 1 - Create an empty Json ###
To create an empty json, you need to call the Json constructor with no argument :
````java
import fr.dtn.noobwork.io.data.Json;

public class Main {
    public static void main(String[] args) {
        Json json = new Json();
    }
}
````

### 2 - Load Json from a file ###
They are two methods to load a json file :
- Create a new Json instance using a String : the path to the file containing the json data
- Create a new Json instance using a File : the file containing the json data

Both methods are similar in code :
````java
import fr.dtn.noobwork.io.data.Json;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Json jsonFromPath = new Json(System.getProperty("user.home") + "/Documents/demo.json");
        Json jsonFromFile = new Json(new File(System.getProperty("user.home"), "Documents/demo.json"));
    }
}
````

### 3 - Create a Json from a JSONObject instance ###
You can also create a Json instance using a JSONObject instance.

````java
import fr.dtn.noobwork.io.data.Json;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        object.put("key", "value");

        Json json = new Json(object);
    }
}
````

But it is not really useful, except internally in the library. Note that you can use the JSONObject class like a HashMap, because it extends of it.


## B - Set or replace data in the Json ##
We can then add data into our empty json by using the method Json#set(String, Object) :
````java
import fr.dtn.noobwork.io.data.Json;

public class Main {
    public static void main(String[] args) {
        Json json = new Json();
        json.set("myKey", 42);
    }
}
````

Note that using Json#set(String, Object) create a new value for the given key or replace an old one if there is.

Only native types are written correctly :
- String
- byte/short/int/long
- float/double
- boolean
- arrays

All others type of objects are stored as String using Object#toString() method.

## C - Save the Json in a file ##
After the instantiation of your Json object and the editing of the values in this json, you can save it in a file using one of the following methods : 
- Json#save()
- Json#save(File)
- Json#save(String)

With the first one, you need to do one of those things :
- Instantiate using Json(String) or Json(File)
- Use Json#setFile(File) method

If you don't do it, the library will throw an exception because it cannot save the Json object without knowing the file to save in.

Using the second or the third method, you can specify a new file to save the Json in.

Here is an example :

````java
import fr.dtn.noobwork.io.data.Json;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Json json = new Json();

        json.set("students.grades", Arrays.asList(12, 18, 12));
        json.set("students.average", 14.0);
        json.set("students.names", new String[] { "Bob", "Bib", "Bub" });

        json.save(new File(System.getProperty("user.home"), "Documents/demo.json"));
    }
}
````
It gives us the following json file :
````json
{"students":{"average":14.0,"names":["Bob","Bib","Bub"],"grades":[12,18,12]}}
````
Note that the json file is written in a single line, it makes it really bad to read, so try using a Json beautifier to improve the reading.


# II - Excel (.xls / .xlsx) #
## A - Read an Excel file ##

They are two methods to load an Excel file :
- Create a new Excel instance using a String : the path to the file containing the Excel data
- Create a new Excel instance using a File : the file containing the Excel data

Here is the two methods used :

````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Excel excelFromPath = new Excel(System.getProperty("user.home") +  "/Documents/demo.xls");
        Excel excelFromFile = new Excel(new File(System.getProperty("user.home"), "Documents/demo.xls"));
    }
}
````

You can then read the data in the file using the Excel#getCell(String, String) to get the cell in a given sheet :
````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Excel excel = new Excel(new File(System.getProperty("user.home"), "Documents/demo.xls"));

        String sheet = "MySheet";
        String cell = "A1";

        double value = excel.getCell(sheet, cell).getNumericCellValue();
    }
}
````
You can also use this method by not specifying the sheet as an argument :
````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Excel excel = new Excel(new File(System.getProperty("user.home"), "Documents/demo.xls"));

        String cell = "A1";

        double value = excel.getCell(cell).getNumericCellValue();
    }
}
````
In this case, the Excel object chose the first sheet in the list of sheets. But you can change the default sheet using the Excel#setWorkingSheet(String) method :
````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Excel excel = new Excel(new File(System.getProperty("user.home"), "Documents/demo.xls"));

        String sheet = "MySheet";
        excel.setWorkingSheet(sheet);
        
        String cell = "A1";
        double value = excel.getCell(cell).getNumericCellValue();
    }
}
````
It is useful in the case you have to call the method a bunch of times, using each call the same sheet.

## B - Write excel file ##
### 1 - Set values ###
After creating or loading an Excel file, you can fill values using one of the following methods :
- Excel#setCellValue(String, String, A), with "A" one of these : double / Date / LocalDate / LocalDateTime / String / Calendar
- Excel#setCellValue(String, A), with "A" the same as above

The first method has as first argument the sheet, whereas the second method does not need to specify the sheet we are working on. It is based on the current sheet, which can be modified using Excel#setWorkingSheet(String) method.

Based on the same syntax, they are these two methods to set the formula of a cell :
- Excel#setCellFormula(String, String, String)
- Excel#setCellFormula(String, String)

Here is an example :

````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Excel excel = new Excel();
        excel.createSheet("MySheet");

        excel.setCellValue("A1", "Bob");
        excel.setCellValue("A2", 3.14);
        excel.setCellValue("A3", LocalDateTime.now());

        excel.setCellFormula("A4", "A2*A2");
    }
}
````

### 2 - Save the file ###

After the Excel file is created, and filled with values, you can save it using one of these methods :
- Excel#save()
- Excel#save(String)
- Excel#save(File)

Using the first one, you must have done one of these things before :
- Instantiate the Excel instance using new Excel(File) or new Excel(String)
- Call method Excel#setFile()

If you don't do one of these, the library will throw an error because it cannot save the Excel if it doesn't know where to save the file.

Using the second method or the third one, you directly specify the path or the file to save in.

Here is several examples that give the same result :
````java
import fr.dtn.noobwork.io.data.Excel;

import java.io.File;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        String filePath = System.getProperty("user.home") +  "/Documents/demo.xls";
        
        // Method 1
        Excel excel1 = new Excel();
        excel1.save(filePath);
        
        // Method 2
        Excel excel2 = new Excel();
        excel2.save(new File(filePath));
        
        // Method 3
        Excel excel3 = new Excel(filePath);
        excel3.save();
        
        // Method 4
        Excel excel4 = new Excel(new File(filePath));
        excel4.save();
    }
}
````
