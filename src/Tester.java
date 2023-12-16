
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        String fileLogName = "weblog-short_log";

        System.out.println(" загружаем построчно fileLogName - " + fileLogName + " в ArrayList<LogEntry> records");
        la.readFile(fileLogName);
        la.printAll();
        System.out.println();

        System.out.println(" Уникальные IP адреса");
        int countUniqIPs = la.countUniqueIPs();
        System.out.println(" Кол-во уникальных IP адресов в журнале - " + countUniqIPs);
        System.out.println();

        int num = 200 ;
        System.out.println(" LogEntrys (записи журнала), которые имеют код состояния больше, чем num");
        la.printAllHigherThanNum(200);
        System.out.println();

        String data = "Sep 14";
        System.out.println(" ArrayList -  строк уникальных IP-адресов, которые имели доступ в данный день");
        ArrayList<String> uniqIPVisitsOnDay = la.uniqueIPVisitsOnDay(data);
    }
}
