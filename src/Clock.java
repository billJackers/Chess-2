public class Clock {

    private int hours;
    private int mins;
    private int secs;
    private int decisecs;

    private int dsMode;

    public Clock(int h, int m, int s) {
        this.hours = h;
        this.mins = m;
        this.secs = s;
        this.decisecs = 10;
    }

    public boolean outOfTime() {
        return (hours == 0 && mins == 0 && secs == 0) || (hours < 0);
    }

    public void decrement() {
        if (this.mins == 0 && this.secs == 0) {
            this.secs = 59;
            this.mins = 59;
            this.hours--;
        } else if (this.secs == 0) {
            this.secs = 59;
            this.mins--;
        } else if (this.decisecs == 0) {
            this.decisecs = 9;
            this.secs--;
        } else {
            this.decisecs--;
        }
    }

    public void increment(int s) {
        if (this.secs+s >= 60) {
            this.secs = (this.secs + s) % 60;
            this.mins++;
        } else this.secs += s;
        if (this.mins == 60) {
            this.mins = 0;
            this.hours++;
        }
    }

    public String getTime() {
        String fHrs = String.format("%02d", this.hours);
        String fMins = String.format("%02d", this.mins);
        String fSecs = String.format("%02d", this.secs);
        String fDeciseconds = String.format("%2d", this.decisecs);
        String fTime = fHrs + ":" + fMins + ":" + fSecs;

        if (this.hours == 0 && this.mins == 0 && secs <= 20 && this.dsMode == 1) {
            fTime += "." + fDeciseconds;
        }
        if (this.dsMode == 2) fTime += "." + fDeciseconds;

        return fTime;
    }

    // 0 is no deciseconds, 1 is show once time is below 20, and 2 is always show
    public void setDsMode(int dsMode) {
        this.dsMode = dsMode;
    }
}
