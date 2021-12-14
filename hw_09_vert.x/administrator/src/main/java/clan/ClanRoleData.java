package clan;

public class ClanRoleData {

    public final String role;
    public int maxNumber = 0;
    public int number = 0;

    public ClanRoleData(String role) {
        this.role = role;
    }

    public boolean isMaximum() {
        return number >= maxNumber;
    }
}
