
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



    // метод возвращает количество уникальных IP-адресов в записях,
    // которые имеют код состояния statusCode в диапазоне от low до high, включительно
    public  int countUniqueIPsInRange(int low, int high) {
         int i = 0;
         ArrayList<String> uniqueIPsInRange = new ArrayList<String>();
         for (LogEntry le : records) {
             if (le.getStatusCode() >= low && le.getStatusCode() <= high) {
                 if (!uniqueIPsInRange.contains(le.getIpAddress())) {
                     uniqueIPsInRange.add(le.getIpAddress());
                     i++;
                     System.out.println(i + "\t" + le.getIpAddress());
                 }
             }
         }
         return uniqueIPsInRange.size();
    }


    // метод возвращает HashMap<String, Integer>, который сопоставляет
    // IP-адрес с количеством появлений этого IP-адреса в записях,
    // что означает количество посещений сайта с этого IP-адреса
    public HashMap<String, Integer> countVisitsPerIP() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            if (!map.containsKey(ip)) {
                map.put(ip, 1);   // если в map еще нет IP - добавляем ключ IP c значением 1
            } else {
                map.put(ip, map.get(ip) + 1);   // если уже есть в map такой IP, то увеличиваем его значение на 1
            }
        }
        return map;
    }



    // метод возвращает максимальное количество посещений данного сайта одним IP-адресом.
    // HashMap<String, Integer> map берется из метода countVisitsPerIP()
    public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
         int max = 0;
         for (Integer value : map.values()) {
             if (value > max) max = value;
         }
         return  max;
    }



    // метод возвращает ArrayList строк IP-адресов, каждый из которых
    // имеет максимальное количество посещений данного сайта.
    // HashMap<String, Integer> map берется из метода countVisitsPerIP()
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
        ArrayList<String> ipAddresses = new ArrayList<String>();
         // методом, описанным выше найдем максимальное значение
         int maxValue = mostNumberVisitsByIP(map);
        // теперь запишем в ArrayList все ключи(IP адреса) которые соответствуют максимальному значению
        for (String key : map.keySet()) {
            if (maxValue == map.get(key)) ipAddresses.add(key);
        }
        return ipAddresses;
    }



    // метод возвращает HashMap<String, ArrayList<String>>,
    // с ArrayList'ом IP-адресов, которые встречались в конкретную дату
    // (включая повторяющиеся IP-адреса)
    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String data = le.getAccessTime().toString();
            data = data.substring(4, 10);      // вырезаем только дату
            String ip = le.getIpAddress();
            if (!map.containsKey(data)) {
                ArrayList<String> newList = new ArrayList<String>();
                newList.add(ip);
                map.put(data, newList);
            } else {
                ArrayList<String> list = map.get(data);
                list.add(ip);
                map.put(data, list);
            }
        }
        return map;
    }



    // метод имеет один параметр, представляющий собой HashMap<String, ArrayList<String>>
    //   КЛЮЧ - дата, ЗНАЧЕНИЕ - ArrayList IP адресов.
    //   Такой HashMap дает метод iPsForDays, сделанный высше.
    //   Этот метод возвращает день, в который произошло наибольшее количество
    //   посещений IP-адресов. Если существует равенство, то возвращается любой такой день.
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> mapIPForDays) {
         String dayWithMostVisits = "";
         int maxListSize = 0;
         for (String keyData : mapIPForDays.keySet()) {      // по ключам
             int currentListSize = mapIPForDays.get(keyData).size();
             if (currentListSize > maxListSize) {
                 maxListSize = currentListSize;
                 dayWithMostVisits = keyData;
             }
         }
         return dayWithMostVisits;
    }



    // метод имеет два параметра: первый - это HashMap<String, ArrayList<String>>,
    // который использует записи и сопоставляет дни из веб-журналов с ArrayList IP-адресов,
    // которые произошли в этот день,
    // а второй параметр - это String, представляющий день в формате "MMM DD".
    // Этот метод возвращает ArrayList<String> IP-адресов, к которым было наибольшее количество обращений в данный день
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> mapIPForDays, String data) {
        // берем из  HashMap<String, ArrayList<String>> mapIPForDays по ключу data,
        // ArrayList IP адресов в эту дату
        ArrayList<String> listIPs = new ArrayList<>();
        listIPs = mapIPForDays.get(data);
        if (listIPs == null) {
            System.out.println(" !!! Нету даты " + data + " в списке");
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add("некорректная дата !!!");
            return errorList;
        }
        // HashMap в котором КЛЮЧИ - IPадреса, значения - их кол-ва
        // Будем его делать из listIPs
        HashMap<String, Integer> mapCountIPs = new HashMap<String, Integer>();
        // перебираем ArrayList IPадресов и делаем mapCountIPs
        for (int i = 0; i < listIPs.size(); i++) {
            String currIP = listIPs.get(i);
            if (!mapCountIPs.containsKey(currIP)) {     // если в хашмап нету IP
                mapCountIPs.put(currIP, 1);
            } else {                                 // если есть такой IP, то увеличиваем значение на 1
                mapCountIPs.put(currIP, mapCountIPs.get(currIP) + 1);
            }
        }

        // переберем полученый ХашМап и найдем максимальное значение (кол-во IP адресов)
        int max = 0;
        for (String keyIP : mapCountIPs.keySet()) {
            if (mapCountIPs.get(keyIP) > max) max = mapCountIPs.get(keyIP);
        }

        // все ключи с max значением добавляем в аррейлист результат
        ArrayList<String> resList = new ArrayList<String>();
        for (String keyIP : mapCountIPs.keySet()) {
            if (mapCountIPs.get(keyIP) == max) {
                resList.add(keyIP);
            }
        }
        return resList;
    }
}
