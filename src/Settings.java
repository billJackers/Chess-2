public class Settings {

    private static String variant;
    private static boolean highlightsOn;
    private static String skin;

    public Settings(String variant, boolean highlightsOn, String skin) {
        Settings.variant = variant;
        Settings.highlightsOn = highlightsOn;
        Settings.skin = skin;
    }

    public void changeSettings(String variant, boolean highlightsOn, String skin) {
        Settings.variant = variant;
        Settings.highlightsOn = highlightsOn;
        Settings.skin = skin;
    }

}
