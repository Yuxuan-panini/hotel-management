package global;

public class Constants {
    public static class Db {
        public static final String DBNAME = "db";
        public static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME + "?autoReconnect=true&useSSL=false&useUnicode=true&charaterEncoding=UTF";
        public static String USERNAME = "system";
        public static String PASSWORD = "1234";
    }
    public static class Table {
        public static String CUSTOMER = "customer";
        public static String DEPOSIT = "deposit";
        public static String BILL = "bill";
        public static String HOTEL = "hotel";
        public static String STAFF = "staff";
        public static String HOTEL_ROOM = "hotel_room";
    }
}
