package com.valerii;

import com.valerii.entity.User;
import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ranAplication();
    }

    private static void ranAplication() {

        ArrayList<User> users = new ArrayList<User>();
        users = readUsersFile("users.txt");

        if (users.isEmpty()) {
            createDefaultUsers();
            users = readUsersFile("users.txt");
        }
        System.out.println("You are welcomed by the coolest user management program!!! )))");
        System.out.println("To create a new user, enter the command: create");
        System.out.println("To edit a user, enter the command: edit");
        System.out.println("To delete a user, enter the command: delete");
        System.out.println("Show all users, enter the command: getAll");
        System.out.println("Search user, enter the command: search");
        System.out.println("To exit the program, enter the command: exit");

        Scanner scaner = new Scanner(System.in);

        for (; ; ) {
            String command = scaner.nextLine();
            if (command.equals("create")) {
                System.out.println("You are in the create new user section");
                User user = createUser(scaner, "create");
                users.add(user);
                writeUserFile(users);
                System.out.println("Command completed!!!");
            } else if (command.equals("edit")) {
                System.out.println("You are in the edit user section");
                System.out.println("list of editable users:");
                ShowAllUsers(users);
                int namberUser = 0;
                for (; ; ) {
                    System.out.println("Enter user number to edit:");
                    namberUser = scaner.nextInt();
                    if ((namberUser - 1) <= users.size() - 1) break;
                    else System.out.println("Invalid number of the user!");
                }
                User user = createUser(scaner, "edit");
                users.set(namberUser - 1, user);
                writeUserFile(users);
                System.out.println("Command completed!!!");
            } else if (command.equals("getAll")) {
                users.forEach(System.out::println);
                System.out.println("Command completed!!!");
            } else if (command.equals("delete")) {
                System.out.println("You are in the delete user section");
                System.out.println("list of deleted users:");
                ShowAllUsers(users);
                int namberUser = 0;
                for (; ; ) {
                    System.out.println("Enter user number to delete:");
                    namberUser = scaner.nextInt();
                    if ((namberUser - 1) <= users.size() - 1) break;
                    else System.out.println("Invalid number of the user!");
                }
                users.remove(namberUser - 1);
                writeUserFile(users);
                System.out.println("Command completed!!!");
            } else if (command.equals("search")) {
                System.out.println("You are in the search user section");
                System.out.println("Enter name of the user for search :");
                String searchName = scaner.nextLine();
                boolean serchResult = false;
                ArrayList<Integer> searhIndex = new ArrayList<>();
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    if (user.getName().equals(searchName)) {
                        serchResult = true;
                        searhIndex.add(i);
                    }
                }
                if (serchResult) {
                    System.out.println("Result of search:");
                    for (int i = 0; i < searhIndex.size(); i++) {
                        System.out.println(users.get(searhIndex.get(i)));
                    }
                } else System.out.println("User is not found");
                System.out.println("Command completed!!!");
            } else if (command.equals("exit")) {System.out.println("Command completed!!!"); break;}
        }
    }

    private static void ShowAllUsers(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i) + " number " + (i + 1));
        }
    }

    private static ArrayList<User> readUsersFile(String name) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            @Cleanup
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
            users = (ArrayList<User>) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    private static void writeUserFile(ArrayList<User> users) {
        try {
            @Cleanup
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.txt"));
            oos.writeObject(users);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void createDefaultUsers() {
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> phones = new ArrayList<String>();
        ArrayList<String> roles1 = new ArrayList<String>();
        ArrayList<String> phones1 = new ArrayList<String>();
        roles.add("admin");
        roles.add("user");
        phones.add("37529456789");
        roles1.add("admin");
        phones1.add("3752323232323");

        User user = User.builder().
                name("Igor").
                surname("Ivanov").
                email("igor@mail.ru").
                roles(roles).
                mobile(phones).
                build();
        User user1 = User.builder().
                name("Petr").
                surname("Ivanov").
                email("Petr@mail.ru").
                roles(roles1).
                mobile(phones1).
                build();

        users.add(user);
        users.add(user1);

        writeUserFile(users);
    }

    private static User createUser(Scanner scaner, String comand) {
        // Name entry block
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        //End name entry Block

        // Surename entry
        System.out.println("Enter the surename of the user:");
        String surename = scanner.nextLine();
        //End surename entry

        // Email entry
        String email = "";
        for (; ; ) {
            System.out.println("Enter the email of the user:");
            email = scanner.nextLine();
            if (isValidate(email, "[@].*\\.")) {
                break;
            } else System.out.println("Invalid email, try entering again!");
        }
        //End email entry

        // Roles entry
        int namber = 0;
        for (; ; ) {
            System.out.println("Enter user role count:");
            String namberRoless = scanner.nextLine();
            if (isValidate(namberRoless, "[1-3]")) {
                namber = Integer.parseInt(namberRoless);
                break;
            } else System.out.println("Incorrect role count, try entering again!");
        }

        ArrayList<String> listRoles = new ArrayList<String>();
        if (comand.equals("edit")) {
            String role1 = scaner.nextLine();//needed to fix a bug with the scanner
        }
        for (int i = 0; i < namber; i++) {
            System.out.println("Enter " + (i + 1) + " role of the user:");
            String role = scaner.nextLine();
            listRoles.add(role);
        }
        //End entry roles

        // Phones entry
        int namber1 = 0;
        for (; ; ) {
            System.out.println("Enter user phone count:");
            String namberRoless = scanner.nextLine();
            if (isValidate(namberRoless, "[1-3]")) {
                namber1 = Integer.parseInt(namberRoless);
                break;
            } else System.out.println("Incorrect phone count, try entering again!");
        }

        ArrayList<String> listPhones = new ArrayList<String>();
        for (int i = 0; i < namber1; i++) {
            for (; ; ) {
                System.out.println("Enter " + (i + 1) + " phone:");
                String phone = scaner.nextLine();
                if (isValidate(phone, "^375")) {
                    listPhones.add(phone);
                    break;
                } else System.out.println("Incorrect phone, try entering again!");
            }
        }
        //End entry phones

        User user = User.builder().
                name(name).
                surname(surename).
                email(email).
                roles(listRoles).
                mobile(listPhones).
                build();

        return user;
    }

    private static boolean isValidate(String validateString, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(validateString);
        return matcher.find();
    }
}
