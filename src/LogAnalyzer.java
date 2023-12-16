
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
         int i = 0;
         for (LogEntry le : records) {
             i++;
             System.out.println(i + "\t" + le);
         }
     }

    // метод должен записывать в ArrayList<String> uniqueIps все уникальные
    // IP адреса из ArrayList<LogEntry> records и возвращать их кол-во,
    public int countUniqueIPs() {
        ArrayList<String> uniqueIps = new ArrayList<>();
        for (LogEntry le : records) {
            String ipAddr = le.getIpAddress();
            if (!uniqueIps.contains(ipAddr)) {
                uniqueIps.add(ipAddr);
                System.out.println("                 \t" + ipAddr);
            }
        }
        return uniqueIps.size();
    }


    // метод должен вывести те LogEntrys (записи журнала),
    // которые имеют код состояния больше, чем num.
    public void printAllHigherThanNum(int num) {
         for (LogEntry le : records) {
             int i = 0;
             if (le.getStatusCode() > num) {
                 i++;
                 System.out.println(i + "\t" + le);
             }
         }
    }


    // метод возвращает ArrayList строк уникальных IP-адресов,
    // которые имели доступ в данный день
    public ArrayList<String> uniqueIPVisitsOnDay(String data) {
         int i = 0;
         ArrayList<String> uniqueIPVisits = new ArrayList<String>();
         for (LogEntry le : records) {
             if (le.getAccessTime().toString().contains(data)) {
                 if (!uniqueIPVisits.contains(le.getIpAddress())) {
                     uniqueIPVisits.add(le.getIpAddress());
                     i++;
                     System.out.println(i + "            \t" + le.getIpAddress());
                 }
             }
         }
         return uniqueIPVisits;
    }
     
}
