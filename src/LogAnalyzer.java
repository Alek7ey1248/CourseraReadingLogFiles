
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }

     // по идее метод должен загрузить построчно filename в ArrayList<LogEntry> records
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for (String line : fr.lines()) {
             // строку приводим к типу LogEntry (класс записи в журнале файла, он в этом проэкте)
             // метод parseEntry тоже в этом проэкте в классе WebLogParser
             LogEntry log = WebLogParser.parseEntry(line);
             // добавляем в ArrayList records, описанный и инициализированыйв этом классе
             records.add(log);
         }
     }

     // выводит в консоль весь ArreyList<EntryLog> records - массив записей журнала класса LogEntry
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     
}
