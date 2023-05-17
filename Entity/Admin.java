package Entity;

public class Admin extends Person {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public String toString() {
        return "ID: " + super.getID() + "\n" +
                "Name: " + super.getName() + "\n" +
                "Email: " + super.getEmail() + "\n" +
                "Password: " + super.getPassword();
    }
}