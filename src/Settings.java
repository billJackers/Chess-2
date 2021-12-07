public class Settings {

    private String variant;
    private boolean highlightsOn;
    private String skin;

    public Settings(String variant, boolean highlightsOn, String skin) {
        this.variant = variant;
        this.highlightsOn = highlightsOn;
        this.skin = skin;
    }

    public void changeSettings(String variant, boolean highlightsOn, String skin) {
        this.variant = variant;
        this.highlightsOn = highlightsOn;
        this.skin = skin;
    }

    public void changeSettings(String variant) {
        this.variant = variant;
    }

    public void changeSettings(boolean highlightsOn) {
        this.highlightsOn = highlightsOn;
    }

    public void changeSkin(String skin) {
        this.skin = skin;
    }

    public String getVariant() {
        return this.variant;
    }

    public boolean getHighlightsOn() {
        return this.highlightsOn;
    }

    public String getSkin() {
        return this.skin;
    }

}
