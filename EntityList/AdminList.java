package EntityList;

import Entity.*;
import Interface.IUniqueIdGenerator;

import java.util.*;

public class AdminList implements IUniqueIdGenerator {
    private static final String FILE_PATH = "Data/admin_list.txt";
    private static List<Admin> admins = new ArrayList<>();
    private static FileHandler adminFileHandler = new FileHandler(FILE_PATH);

    public AdminList() {
    }

    public void addAdmin(Admin admin) {
        if (!admins.contains(admin)) {
            admins.add(admin);
        }
    }

    public void removeAdmin(Admin admin) {
        admins.remove(admin);
    }

    public static Admin findAdminById(int id) {
        for (Admin admin : admins) {
            if (admin.getID() == id) {
                return admin;
            }
        }
        return null;
    }

    public static List<Admin> getAdminList() {
        return admins;
    }

    public static void saveAdminListToFile(List<Admin> admins) {
        adminFileHandler.saveListToFile(admins);
    }

    public static List<Admin> loadAdminListFromFile() {
        List<String[]> attributesList = adminFileHandler.loadListFromFile();
        admins.clear();
        for (String[] attributes : attributesList) {
            int id = Integer.parseInt(attributes[0]);
            String name = attributes[1];
            String email = attributes[2];
            String password = attributes[3];
            Admin admin = new Admin(id, name, email, password);
            admins.add(admin);
        }
        return admins;
    }

    public int generateUniqueId() {
        int id = 1;
        while (findAdminById(id) != null) {
            id++;
        }
        return id;
    }
}
