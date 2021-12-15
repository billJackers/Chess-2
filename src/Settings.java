public class Settings {

    private String variant;
    private boolean highlightsOn;
    private String skin;
    private boolean muted;
    private int dsMode;

    public Settings(String variant, boolean highlightsOn, String skin, boolean muted) {
        this.variant = variant;
        this.highlightsOn = highlightsOn;
        this.skin = skin;
        this.muted = muted;
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

    public void changeMuted(boolean muted) {
        this.muted = muted;
    }

    public void changeDsMode(int mode) {
        this.dsMode = mode;
    }

    public boolean getMuted() {
        return this.muted;
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

    public int getDsMode() {
        return this.dsMode;
    }

}
