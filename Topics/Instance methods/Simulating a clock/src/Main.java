class Clock {

    int hours = 12;
    int minutes = 0;

    void next() {
        this.minutes++;
        if (this.minutes == 60) {
            this.minutes = 0;
            this.hours++;
        }
        if (this.hours == 13) {
            this.hours = 1;
        }
    }
}