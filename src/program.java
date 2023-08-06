import cls.Toy;

import java.io.*;
import java.util.*;

public class program {
    private List<Toy> toys;

    // конструктор
    public program() {
        toys = new ArrayList<>();
    }

    // метод для добавления новых игрушек
    public void addToy(Toy toy) {
        toys.add(toy);
    }

    // метод для изменения веса (частоты выпадения) игрушки по её id
    public static void setToyWeight(program program, int id, double weight) {
        for (Toy toy : program.toys) {
            if (toy.getId() == id) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    // метод для розыгрыша игрушек
    public void drawToys() {
        // проверяем, есть ли игрушки в магазине
        if (toys.isEmpty()) {
            System.out.println("В магазине нет игрушек.");
            return;
        }
        // считаем общую сумму весов игрушек
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }
        // генерируем случайное число от 0 до общей суммы весов
        Random random = new Random();
        double randomNumber = random.nextDouble() * totalWeight;
        // находим игрушку, соответствующую сгенерированному числу
        double currentWeight = 0;
        Toy chosenToy = null;
        for (Toy toy : toys) {
            currentWeight += toy.getWeight();
            if (randomNumber <= currentWeight) {
                chosenToy = toy;
                break;
            }
        }
        // выводим результат розыгрыша

        if (chosenToy != null) {
            winToy.r = chosenToy.getName();
            System.out.println("Поздравляем! Вы выиграли игрушку: " + winToy.r);
            if (chosenToy.getQuantity() > 0) {
                chosenToy.setQuantity(chosenToy.getQuantity() - 1);
                winToy.winList += winToy.r + ", ";
            } else {
                System.out.println("К сожалению, данная игрушка закончилась.");
                //winToy.r = "";
            }
        } else {
            System.out.println("Ничего не выиграли.");
        }


    }

    //сохранение в файл
    public void saveToysToFile(String filename) {
        if (winToy.t != 1) {
            try (FileWriter writer = new FileWriter(filename + ".txt", true)) {
                writer.write("\nРозыгрышь " + winToy.t + ":\n");
                if (winToy.winList == "") {
                    winToy.winList = "Небыло выиграно ни одной игрушки\n";
                    writer.write(winToy.winList);
                    writer.flush();
                    winToy.winList = "";
                } else {
                    writer.write(winToy.winList);
                    writer.flush();
                    winToy.winList = "";
                }
                winToy.t += 1;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try (FileWriter writer = new FileWriter(filename + ".txt", false)) {
                writer.write("\nРозыгрышь " + winToy.t + ":\n");
                if (winToy.winList == "") {
                    winToy.winList = "Небыло выиграно ни одной игрушки\n";
                    writer.write(winToy.winList);
                    writer.flush();
                    winToy.winList = "";
                } else {
                    writer.write(winToy.winList);
                    writer.flush();
                    winToy.winList = "";
                }
                winToy.t += 1;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void loadToysFromFile(String filename) {
        try (FileReader reader = new FileReader(filename + ".txt")) {
            // читаем посимвольно
            int c;
            while ((c = reader.read()) != -1) {

                System.out.print((char) c);
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {

        program raffleToy = new program();
        // добавляем игрушки в магазин
        raffleToy.addToy(new Toy(1, "Мяч", 1, 20));
        raffleToy.addToy(new Toy(2, "Кукла", 1, 30));
        raffleToy.addToy(new Toy(3, "Машинка", 1, 50));
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Розыгрыш игрушки");
            System.out.println("2. Добавление новой игрушки");
            System.out.println("3. Изменение веса (частоты выпадения) игрушки");
            System.out.println("4. Сохранить список игрушек в файл");
            System.out.println("5. Загрузить список игрушек из файла");
            System.out.println("6. Выйти");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    raffleToy.drawToys();
                    break;
                case 2:
                    System.out.println("Введите id новой игрушки:");
                    int id = scanner.nextInt();
                    System.out.println("Введите название новой игрушки:");
                    String name = scanner.next();
                    System.out.println("Введите количество новой игрушки:");
                    int quantity = scanner.nextInt();
                    System.out.println("Введите вес (частоту выпадения) новой игрушки:");
                    double weight = scanner.nextDouble();
                    raffleToy.addToy(new Toy(id, name, quantity, weight));
                    break;
                case 3:
                    System.out.println("Введите id игрушки, у которой необходимо изменить вес:");
                    int toyId = scanner.nextInt();
                    System.out.println("Введите новый вес (частоту выпадения) игрушки:");
                    double newWeight = scanner.nextDouble();
                    program.setToyWeight(raffleToy, toyId, newWeight);
                    break;
                case 4:
                    String saveFilename = "toysWin";
                    raffleToy.saveToysToFile(saveFilename);
                    System.out.println("Список выиграных игрушек записан в файл " + saveFilename);
                    break;
                case 5:
                    String loadFilename = "toysWin";
                    raffleToy.loadToysFromFile(loadFilename);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }
        }
        scanner.close();
    }
}