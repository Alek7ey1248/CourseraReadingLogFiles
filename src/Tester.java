
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
        //String fileLogName = "weblog3-short_log";
        //String fileLogName = "weblog1_log";

        System.out.println(" загружаем построчно fileLogName - " + fileLogName + " в ArrayList<LogEntry> records");
        la.readFile(fileLogName);
        la.printAll();
        System.out.println();

        System.out.println(" Уникальные IP адреса");
        int countUniqIPs = la.countUniqueIPs();
        System.out.println(" Кол-во уникальных IP адресов в журнале - " + countUniqIPs);
        System.out.println();

        int num = 400 ;
        System.out.println(" LogEntrys (записи журнала), которые имеют код состояния больше, чем " + num);
        la.printAllHigherThanNum(num);
        System.out.println();

        String data = "Mar 24";
        System.out.println(" ArrayList -  строк уникальных IP-адресов, которые имели доступ " + data);
        ArrayList<String> uniqIPVisitsOnDay = la.uniqueIPVisitsOnDay(data);
        System.out.println();

        int low = 300;
        int high = 399;
        System.out.println(" уникальные IP адреса записей журнала, которые имеют код состояния statusCode в диапазоне от " + low + " до " +  high + ", включительно");
        int countUniqueIPsInRange = la.countUniqueIPsInRange(low, high);
        System.out.println(" Их кол-во - " + countUniqueIPsInRange);
        System.out.println();

        System.out.println("--------------------------------------" );
        System.out.println("--------------------------------------" );
        System.out.println("HashMap, в котором КЛЮЧИ - IP-адреса, а ЗНАЧЕНИЯ - количество посещений сайта с этого IP-адреса ");
        HashMap<String, Integer> countVisits = la.countVisitsPerIP();
        int i = 0;
        for (Map.Entry<String, Integer> map : countVisits.entrySet()) {
            i++;
            System.out.println(i + "\t" + map.getKey() + " - " + "\t" + map.getValue());
        }
        System.out.println();

        int maxValue = la.mostNumberVisitsByIP(countVisits);
        System.out.println(" максимальное количество посещений данного сайта одним IP-адресоv - " + maxValue);
        System.out.println();

        ArrayList<String> ipAdressesMaxValue = la.iPsMostVisits(countVisits);
        System.out.println(" ArrayList<String> строк IP-адресов, каждый из которых имеет максимальное количество посещений данного сайта");
        System.out.println(ipAdressesMaxValue.toString());
        System.out.println();

        HashMap<String, ArrayList<String>> mapIPsForDays = la.iPsForDays();
        System.out.println(" HashMap<String, ArrayList<String>>, с ArrayList'ом IP-адресов, которые встречались в конкретную дату (включая повторяющиеся IP-адреса)");
        int n = 0;
        for (String dataKey : mapIPsForDays.keySet()) {    // по ключам
            n++;
            System.out.println(n + "\t" + dataKey + " - " + "\t" + mapIPsForDays.get(dataKey).toString());
        }
    }
}
