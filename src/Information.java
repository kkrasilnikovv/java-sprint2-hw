import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Information {
    private final List<YearlyReport> yearlyReports = Reader.getList();
    private final Map<Integer, ArrayList<MonthlyReport>> mapMonthReport = Reader.getMap();
    private final Map<Integer, String> mapMonth = fillMap();

    private Map<Integer, String> fillMap() {
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
        if (checkListAndMap()) {
            System.out.println("Файлы еще не считаны");
            return;
        }

        String nameIncome = "";
        String nameBadIncome = "";
        int monthIncome ;

        for (Map.Entry<Integer, ArrayList<MonthlyReport>> e : mapMonthReport.entrySet()) {
            monthIncome = e.getKey();
            int sumIncome = 0;
            int sumBadIncome = 0;
            for (int i = 0; i < e.getValue().size(); i++) {
                MonthlyReport m = e.getValue().get(i);
                int sum = m.getQuantity() * m.getSumOfOne();
                if (!m.isIsExpense() && sum > sumIncome) {
                    sumIncome = sum;
                    nameIncome = m.getItemName();
                } else if (m.isIsExpense() && sum > sumBadIncome) {
                    sumBadIncome = sum;
                    nameBadIncome = m.getItemName();
                }
            }
            System.out.println(mapMonth.get(monthIncome));
            System.out.println("Cамый прибыльный товар " + sumIncome + " " + nameIncome);
            System.out.println("Cамый убыточный товар  " + sumBadIncome + " " + nameBadIncome);
        }
    }

    public boolean checkListAndMap() {
        return yearlyReports.isEmpty() || mapMonthReport.isEmpty();
    }

    public void infoYear() {
        if (checkListAndMap()) {
            System.out.println("Файлы еще не считаны");
            return;
        }
        int income = 0;
        int incomeSum = 0;
        int Expenses = 0;
        int ExpensesSum = 0;
        int count = 0;
        for (YearlyReport y : yearlyReports) {
            if (y.isIsExpense()) {
                Expenses += y.getAmount();
                ExpensesSum += y.getAmount();
            } else {
                income += y.getAmount();
                incomeSum += y.getAmount();
            }
            count++;
            if (count == 2) {
                count = 0;
                System.out.println("Доход за месяц " + mapMonth.get(y.getMonth()) + " " + (income - Expenses));
                income = 0;
                Expenses = 0;
            }
        }
        System.out.println("Средний  расход " + ExpensesSum / (yearlyReports.size() / 2));
        System.out.println("Средний  доход " + incomeSum / (yearlyReports.size() / 2));
    }

    public void compareReports() {
        if (checkListAndMap()) {
            System.out.println("Файлы еще не считаны");
            return;
        }
        Information fileInfo = new Information();
        for (Map.Entry<Integer, ArrayList<MonthlyReport>> e : mapMonthReport.entrySet()) {
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
                System.err.println("Ошибка в отчете за " + mapMonth.get(e.getKey()) + " месяц ");
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