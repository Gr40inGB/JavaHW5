import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        String s = "Иван Иванов, Светлана Петрова, Кристина Белова, Анна Мусина, Анна Крутова, " +
                "Иван Юрин, Петр Лыков, Павел Чернов, Петр Чернышов, Мария Федорова, Марина Светлова, " +
                "Мария Савина, Мария Рыкова, Марина Лугова, Анна Владимирова, Иван Мечников, Петр Петин, Иван Ежов.";
        String[] names = s.replace(".", ", ").split(" .+?, ");
        Arrays.sort(names);
        Map<String, Integer> countNames = new HashMap<>();
        for (String currentName : names) {
            if (countNames.containsKey(currentName)) {
                countNames.put(currentName, countNames.get(currentName) + 1);
            } else {
                countNames.put(currentName, 1);
            }
        }

        Map<Integer, List<String>> mapNames = new TreeMap<>(Collections.reverseOrder());
        for (String currentName : names) {
            List<String> namesList = new ArrayList<>();
            int count = countNames.get(currentName.split(" ")[0]);
            if (mapNames.containsKey(count)) {
                namesList = mapNames.get(count);
                if (!namesList.contains(currentName)) {
                    namesList.add(currentName);
                }
            } else {
                namesList.add(currentName);
                mapNames.put(count, namesList);
            }
        }
        for (Map.Entry<Integer, List<String>> entry : mapNames.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}
