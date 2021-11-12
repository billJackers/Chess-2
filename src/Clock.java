public class Clock {

    private int hours;
    private int mins;
    private int secs;

    public Clock(int h, int m, int s) {
        this.hours = h;
        this.mins = m;
        this.secs = s;
    }

    public boolean outOfTime() {
        return (hours == 0 && mins == 0 && secs == 0);
    }

    public void decrement() {
        if (this.mins == 0 && this.secs == 0) {
            this.secs = 59;
            this.mins = 59;
            this.hours--;
        } else if (this.secs == 0) {
            this.secs = 59;
            this.mins--;
        } else {
            this.secs--;
        }
    }

    public String getTime() {
        String fHrs = String.format("%02d", this.hours);
        String fMins = String.format("%02d", this.mins);
        String fSecs = String.format("%02d", this.secs);
        String fTime = fHrs + ":" + fMins + ":" + fSecs;
        return fTime;
    }

}
