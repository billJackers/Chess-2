public class Settings {

    enum PlayerSide {
        BOTH,
        PLAYER_BLUE,
        PLAYER_RED
    }

    private PlayerSide side;
    private String variant;
    private boolean highlightsOn;
    private String skin;
    private boolean muted;
    private int dsMode;
    private boolean moveRollback;
    private int[] timerConfig;

    public Settings(PlayerSide playerSide, String variant, boolean highlightsOn, String skin, boolean muted, boolean enableRollback, int[] timerConfig) {
        this.side = playerSide;
        this.variant = variant;
        this.highlightsOn = highlightsOn;
        this.skin = skin;
        this.muted = muted;
        this.moveRollback = enableRollback;
        this.timerConfig = timerConfig;
    }

    public int[] getTimer() {
        return timerConfig;
    }

    public PlayerSide getSide() {
        return side;
    }

    public boolean canRollback() {
        return moveRollback;
    }

    public void changeVariant(String variant) {
        this.variant = variant;
    }

    public void changeHighlightSettings(boolean highlightsOn) {
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

    public void changeTimerConfig(int[] timerConfig) {
        this.timerConfig = timerConfig;
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

    public int[] getTimerConfig() {
        return this.timerConfig;
    }
}
