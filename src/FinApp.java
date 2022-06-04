import java.util.InputMismatchException;
import java.util.Scanner;

public class FinApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double moneyBeforeSalary;
        int daysBeforeSalary;

        System.out.println("Сколько денег у вас осталось до зарплаты?");
        while (true) {
            try {
                moneyBeforeSalary = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите число.");
                scanner = new Scanner(System.in);
            }
        }

        System.out.println("Сколько дней до зарплаты?");
        while (true) {
            try {
                daysBeforeSalary = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите целое число.");
                scanner = new Scanner(System.in);
            }
        }

        Converter converter = new Converter();
        DinnerAdvisor dinnerAdvisor = new DinnerAdvisor();
        ExpensesManager expensesManager = new ExpensesManager();

        while (true) {
            printMenu();
            int command;
            try {
                command = scanner.nextInt();
            } catch (InputMismatchException e) {
                command = 99;
                scanner = new Scanner(System.in);
            }

            if (command == 1) {
                System.out.println("Ваши сбережения: " + moneyBeforeSalary + " RUB");
                System.out.println("В какую валюту хотите конвертировать? Введите трёхзначный код валюты");
                String currency = scanner.next().toUpperCase();
                converter.convert(moneyBeforeSalary, currency);
            } else if (command == 2) {
                dinnerAdvisor.getAdvice(moneyBeforeSalary, daysBeforeSalary);
            } else if (command == 3) {
                System.out.println("Введите размер траты:");
                double expense = scanner.nextDouble();
                System.out.println("К какой категории относится трата?");
                String category = scanner.next();
                moneyBeforeSalary = expensesManager.saveExpense(moneyBeforeSalary, category, expense);
            } else if (command == 4) {
                expensesManager.printAllExpensesByCategories();
            } else if (command == 5) {
                System.out.println("В какой категории искать?");
                String category = scanner.next();
                System.out.println("Самая большая трата в категории " + category + " составила "
                        + expensesManager.findMaxExpenseInCategory(category) + " руб.");
            } else if (command == 6) {
                expensesManager.removeAllExpenses();
                // Допишите остальные пункты меню
            } else if (command == 7) {
                System.out.println("Всего потрачено: " + expensesManager.getExpensesSum());
            } else if (command == 8) {
                System.out.println("Какую категорию вы хотите удалить?");
                String category = scanner.next();
                expensesManager.removeCategory(category);
            } else if (command == 9) { // Напечатайте фразу "В категории ИМЯ_КАТЕГОРИИ вы потратили больше всего.");
                System.out.println("В категории " + expensesManager.getMaxCategoryName() + " вы потратили больше всего.");
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Конвертировать валюту");
        System.out.println("2 - Получить совет");
        System.out.println("3 - Ввести трату");
        System.out.println("4 - Показать траты по категориям");
        System.out.println("5 - Показать самую большую трату в выбранной категории");
        System.out.println("6 - Очистить таблицу трат");
        System.out.println("7 - Вернуть сумму всех трат");
        System.out.println("8 - Удалить категорию");
        System.out.println("9 - Получить имя самой дорогой категории");
        System.out.println("0 - Выход");
    }
}