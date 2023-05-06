import java.util.*;

public class PhoneBook {
    private final Map<String, TreeMap<String, String>> map = new TreeMap<>();
    private final Scanner scanner = new Scanner(System.in);

    void run() {
        while (true) {
            System.out.print("Please select command: " +
                    "\n1 - Add new contact" +
                    "\t2 - View all list" +
                    "\t3 - Search" +
                    "\n4 - Delete contact" +
                    "\t5 - Edit contact" +
                    "\t6 - Exit" +
                    "\n>>>> ");

            switch (inputNumber("", 6)) {
                case 1 -> manualAddContact();
                case 2 -> showAll();
                case 3 -> findContact();
                case 4 -> deleteContact();
                case 5 -> editContact();
                case 6 -> {
                    System.out.println("buy buy =)");
                    return;
                }
                default -> System.out.println("Error command choose.");
            }
        }
    }

    void findContact() {
        System.out.print("Search\nPlease enter line for search: ");
        String search = scanner.nextLine();
        System.out.println("We find : ");
        for (Map.Entry<String, TreeMap<String, String>> entry : map.entrySet()) {
            if (entry.getKey().toLowerCase().contains(search.toLowerCase())) {
                System.out.println(entry.getKey());
                for (Map.Entry<String, String> phones : entry.getValue().entrySet()) {
                    System.out.println("\t" + phones.getKey() + " " + phones.getValue());
                }
            } else {
                for (Map.Entry<String, String> phonesInKey : entry.getValue().entrySet()) {
                    if (phonesInKey.getValue().contains(search)) {
                        System.out.println(entry.getKey());
                        for (Map.Entry<String, String> phones : entry.getValue().entrySet()) {
                            System.out.println("\t" + phones.getKey() + " " + phones.getValue());
                        }
                    }
                }
            }
        }
    }

    void manualAddContact() {
        System.out.println("Add new contact: ");
        add(inputData("Input name"),
                inputData("Input phone type"),
                inputData("Input Phone number"));
    }

    private String inputData(String welcome) {
        System.out.print(welcome + "\n>>>> ");
        return scanner.nextLine();
    }

    void deleteContact() {
        System.out.println("Delete ");
        String toDelete = getOneName();
        if (yesNo("Delete " + toDelete + " ?")) {
            map.remove(toDelete);
        } else {
            System.out.println("Canceled");
        }
    }

    private String getOneName() {
        String toChoose = inputData("First choose name: ");
        List<String> tempList = new ArrayList<>();
        for (String currentKey : map.keySet()) {
            if (currentKey.toLowerCase().contains(toChoose.toLowerCase())) {
                tempList.add(currentKey);
            }
        }
        if (tempList.isEmpty()) {
            System.out.println("We not found " + toChoose);
            return getOneName();
        }
        if (tempList.size() == 1) {
            return tempList.get(0);
        } else {
            System.out.println("Choose number: ");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.println(i + 1 + " " + tempList.get(i));
            }
            return tempList.get(inputNumber(">>>> ", tempList.size()) - 1);
        }
    }

    boolean yesNo(String message) {
        System.out.println(message);
        while (true) {
            System.out.print("y if Yes, n if no\n>>>> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }

    void editContact() {
        System.out.println("Edit contact: ");
        String keyForEdit = getOneName();
        System.out.println(keyForEdit);

        while (true) {
            System.out.print("Please select command: " +
                    "\n1 - Rename Contact" +
                    "\t2 - Delete number" +
                    "\n3 - Edit number" +
                    "\t4 - add number" +
                    "\t5 - done" +
                    "\n>>>> ");

            switch (inputNumber("", 5)) {
                case 1 -> renameContact(keyForEdit);
                case 2 -> deleteNumber(keyForEdit);
                case 3 -> editNumber(keyForEdit);
                case 4 -> addNumber(keyForEdit);
                case 5 -> {
                    return;
                }
            }
        }
    }

    private void addNumber(String nameKey) {
        add(nameKey, inputData("Please enter phone type: "), inputData("Please enter phone number: "));
    }

    private void editNumber(String nameKey) {
        String phoneType = getOnePhoneType(nameKey);
        System.out.println(phoneType + "\t" + map.get(nameKey).remove(phoneType));
        map.get(nameKey).put(phoneType, inputData("Please input new number: "));
    }

    private void deleteNumber(String nameKey) {
        System.out.println("Deleting phone number for - " + nameKey);
        String deletingPhone = getOnePhoneType(nameKey);
        if (yesNo("delete?")) {
            map.get(nameKey).remove(deletingPhone);
        }
    }

    private String getOnePhoneType(String nameKey) {
        System.out.println(nameKey);
        int phonesCount = map.get(nameKey).size();
        if (phonesCount == 0) {
            System.out.println("not found any phone numbers");
            return null;
        }
        if (phonesCount == 1) {
            return map.get(nameKey).firstKey();
        }
        List<String> phonesList = new ArrayList<>();
        int i = 1;
        for (Map.Entry<String, String> phones : map.get(nameKey).entrySet()) {
            phonesList.add(phones.getKey());
            System.out.println(i++ + "\t" + phones.getKey() + " " + phones.getValue());
        }
        int select = inputNumber("Select number: ", phonesList.size());
        return (phonesList.get(select - 1));
    }

    void renameContact(String nameKey) {
        System.out.println("Rename Contact");
        boolean inputError = true;
        String newName = null;
        while (inputError) {
            newName = inputData("Please enter new name: ");
            if (map.containsKey(newName)) {
                System.out.println("Name " + newName + " already exists");
            } else inputError = false;
        }
        map.put(newName, map.remove(nameKey));
    }

    void add(String name, String phoneType, String phoneNumber) {
        TreeMap<String, String> temp = new TreeMap<>();
        if (map.containsKey(name)) {
            temp = map.get(name);
        }
        if (temp.containsKey(phoneType)) {
            if (temp.get(phoneType).equals(phoneNumber))
                System.out.println("We already have this number");
            else {
                System.out.printf("We already have this phone type %s: %s. New number is %s. ",
                        phoneType, temp.get(phoneType), phoneNumber);
                if (yesNo("Replace?")) {
                    temp.put(phoneType, phoneNumber);
                    return;
                }
                return;
            }
        }
        temp.put(phoneType, phoneNumber);
        map.put(name, temp);
    }

    void showAll() {
        System.out.println("All PhoneBook: ");
        for (Map.Entry<String, TreeMap<String, String>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            for (Map.Entry<String, String> phones : entry.getValue().entrySet()) {
                System.out.println("\t" + phones.getKey() + " " + phones.getValue());
            }
        }
    }

    private int inputNumber(String welcome, int biggest) {
        boolean inputError = true;
        int rezult = 0;
        while (inputError) {
            System.out.print(welcome);
            try {
                rezult = Integer.parseInt(scanner.nextLine());
                if (rezult <= biggest && rezult > 0) {
                    inputError = false;
                } else System.out.print("Choose between 1-" + biggest + "\n>>>> ");
            } catch (NumberFormatException nfe) {
                System.out.println("error - try again:");
            }
        }
        return rezult;
    }
}
