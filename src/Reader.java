import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {
    private final String[] month = new String[]{"01", "02", "03"};
    private static final HashMap<Integer, ArrayList<MonthlyReport>> mapMonthReport = new HashMap<>();
    private static final List<YearlyReport> yearlyReports = new ArrayList<>();

    public static List<YearlyReport> getList() {
        return yearlyReports;
    }

    public void readYear() {
        String way = "./resources/y.2021.csv";
        File file = new File(way);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String s = br.readLine();
            while ((s = br.readLine()) != null) {
                String[] array = s.split(",");
                yearlyReports.add(new YearlyReport(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Boolean.parseBoolean(array[2])));
            }
        } catch (IOException var6) {
            System.out.println("Не удалось считать годовой отчет");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Файл считан");
        }
    }

    public void readMonth() {
        for (String actualMonth : month) {
            String url = "./resources/m.2021" + actualMonth + ".csv";
            File file = new File(url);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String s = br.readLine();
                ArrayList<MonthlyReport> monthList = new ArrayList<>();
                while ((s = br.readLine()) != null) {
                    String[] array = s.split(",");
                    monthList.add(new MonthlyReport(array[0], Boolean.parseBoolean(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3])));
                }
                mapMonthReport.put(Integer.parseInt(actualMonth), monthList);
            } catch (IOException var8) {
                System.out.println("Не удалось считать месячный отчет");
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Файл считан");
            }
        }
    }

    public static HashMap<Integer, ArrayList<MonthlyReport>> getMap() {
        return mapMonthReport;
    }
}