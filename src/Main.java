import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Information information = new Information();
        Reader reader = new Reader();
        while (true) {
            printMenu();
            int command = scanner.nextByte();
            switch (command) {
                case (1): {
                    reader.readMonth();
                    break;
                }
                case (2): {
                    reader.readYear();
                    break;
                }
                case (3): {
                    information.compareReports();
                    break;
                }
                case (4): {
                    information.infoMonth();
                    break;
                }
                case (5): {
                    information.infoYear();
                    break;
                }
                case (0): {
                    return;
                }
                default:
                    System.out.println("Такой команды нет.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1-Считать все месячные отчёты" + '\n' + "2-Считать годовой отчёт" + '\n' +
                "3-Сверить отчёты" + '\n' + "4-Вывести информацию о всех месячных отчётах" + '\n' +
                "5-Вывести информацию о годовом отчёте" + '\n' + "0-Выход");

    }
}
