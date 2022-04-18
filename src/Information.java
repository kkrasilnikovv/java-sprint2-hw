import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Information {
    private final List<YearlyReport> yearlyReports = Reader.getYearlyReports();
    private final Map<Integer, ArrayList<MonthlyReport>> monthReports = Reader.getMonthRep();
    private static final Map<Integer, String> month = getMonthNames();
    private static Information fileInfo = new Information();

    private static Map<Integer, String> getMonthNames() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Январь");
        map.put(2, "Февраль");
        map.put(3, "Март");
        map.put(4, "Апрель");
        map.put(5, "Май");
        map.put(6, "Июнь");
        map.put(7, "Июль");
        map.put(8, "Август");
        map.put(9, "Сентябрь");
        map.put(10, "Октябрь");
        map.put(11, "Ноябрь");
        map.put(12, "Декабрь");
        return map;
    }

    public void infoMonth() {
        if (checkMonthReports()) {
            System.out.println("Файлы еще не считаны");
            return;
        }

        String nameIncome = "";
        String nameBadIncome = "";
        int monthIncome;

        for (Map.Entry<Integer, ArrayList<MonthlyReport>> monthReport : monthReports.entrySet()) {
            monthIncome = monthReport.getKey();
            int sumIncome = 0;
            int sumBadIncome = 0;
            for (int i = 0; i < monthReport.getValue().size(); i++) {
                MonthlyReport m = monthReport.getValue().get(i);
                int sum = m.getQuantity() * m.getSumOfOne();
                if (!m.isIsExpense() && sum > sumIncome) {
                    sumIncome = sum;
                    nameIncome = m.getItemName();
                } else if (m.isIsExpense() && sum > sumBadIncome) {
                    sumBadIncome = sum;
                    nameBadIncome = m.getItemName();
                }
            }
            System.out.println(month.get(monthIncome));
            System.out.println("Cамый прибыльный товар " + sumIncome + " " + nameIncome);
            System.out.println("Cамый убыточный товар  " + sumBadIncome + " " + nameBadIncome);
        }
    }

    public boolean checkYearlyReports() {
        return yearlyReports.isEmpty();
    }
    public boolean checkMonthReports() {
        return monthReports.isEmpty();
    }


    public void infoYear() {
        if (checkYearlyReports()) {
            System.out.println("Файлы еще не считаны");
            return;
        }
        int income = 0;
        int incomeSum = 0;
        int expenses = 0;
        int expensesSum = 0;
        int count = 0;
        for (YearlyReport year : yearlyReports) {
            if (year.isIsExpense()) {
                expenses += year.getAmount();
                expensesSum += year.getAmount();
            } else {
                income += year.getAmount();
                incomeSum += year.getAmount();
            }
            count++;
            if (count == 2) {
                count = 0;
                System.out.println("Доход за месяц " + month.get(year.getMonth()) + " " + (income - expenses));
                income = 0;
                expenses = 0;
            }
        }
        System.out.println("Средний  расход " + expensesSum / (yearlyReports.size() / 2));
        System.out.println("Средний  доход " + incomeSum / (yearlyReports.size() / 2));
    }

    public void compareReports() {
        if (checkMonthReports() || checkYearlyReports()) {
            System.out.println("Файлы еще не считаны");
            return;
        }

        for (Map.Entry<Integer, ArrayList<MonthlyReport>> e : monthReports.entrySet()) {
            int income = 0;
            int badIncome = 0;
            for (int i = 0; i < e.getValue().size(); i++) {
                MonthlyReport monthlyReport = e.getValue().get(i);
                int count = monthlyReport.getSumOfOne() * monthlyReport.getQuantity();
                if (monthlyReport.isIsExpense()) {
                    badIncome += count;
                } else {
                    income += count;
                }
            }
            int sum = income - badIncome;
            if (fileInfo.getSumFromMonth(e.getKey()) != sum) {
                System.err.println("Ошибка в отчете за " + month.get(e.getKey()) + " месяц ");
                return;
            }
        }
        System.out.println("Успешно");
    }

    private int getSumFromMonth(int i) {
        int sumIn = 0;
        int sumBad = 0;
        for (YearlyReport report : yearlyReports) {
            if (report.getMonth() == i && report.isIsExpense()) {
                sumBad += report.getAmount();
            } else if (report.getMonth() == i && !report.isIsExpense()) {
                sumIn += report.getAmount();
            }
        }
        return sumIn - sumBad;
    }

}