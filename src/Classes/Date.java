package Classes;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Ficheiro retirado da aula
 */
public class Date implements Comparable<Date> {

    public int day;
    public int month;
    public int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date (Date d){
        this.day = d.day;
        this.month = d.month;
        this.year = d.year;
    }

    public Date(){
        GregorianCalendar g = new GregorianCalendar();
        this.day = g.get(Calendar.DAY_OF_MONTH);
        this.month = g.get(Calendar.MONTH) + 1;
        this.year = g.get(Calendar.YEAR);
    }


    public boolean beforeDate(Date d) {
        return this.compareTo(d) < 0;
    }

    public static boolean isLeapYear(int year) {
        return((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    public boolean afterDate(Date d) {
        return this.compareTo(d) > 0;
    }

    public void incrementDate(){
        if(daysMonth((short) this.month, this.year) == this.day){
            if(this.month == 12){
                this.year++;
                this.month = 1;
                this.day = 1;
                return;
            }
            this.month++;
            this.day = 1;
            return;
        }

        this.day++;
    }

    public int differenceYears(Date d) {
        return 0;
    }

    public int compareTo(Date d) {
        if(this.day == d.day && this.month == d.month && this.year == d.year)
            return 0;
        if(this.year < d.year)
            return -1;
        if(this.year == d.year)
            if(this.month < d.month)
                return -1;
            if(this.month == d.month)
                if(this.day == d.day)
                    return -1;
        return 1;
    }

    public int differenceMonths(Date d) {
        return 0;
    }

    public static int daysMonth(short month, int year) {
        switch (month){
            case 11:
            case 9:
            case 6:
            case 4:
                return 30;
            case 2:
                return(Date.isLeapYear(year)? 29 : 28);
            default:
                return 31;
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    public static int daysBetweenDates(Date begin, Date end){
        if(begin.afterDate(end)){
            return -1;
        }
        if(begin.year == end.year){
            return Date.daysBetweenMonths(begin.day, begin.month, end.day, end.month, begin.year);
        }

        Date aux = new Date(begin);

        int days = Date.daysBetweenMonths(aux.day, aux.month, 31, 12, aux.year);
        while(++aux.year < end.year){
            days += (isLeapYear(aux.year) ? 366 : 365);
        }

        return days + Date.daysBetweenMonths(1 ,1 ,end.day, end.month, end.year) + 1;
    }

    private static int daysBetweenMonths(int beginDay, int beginMonth, int endDay, int endMonth, int year) {
        if(beginMonth == endMonth){
            return endDay - beginDay;
        }
        int days = Date.daysMonth((short) beginMonth, year) - beginDay;
        while(++beginMonth < endMonth){
            days += Date.daysMonth((short) beginMonth, year);
        }
        return days + endDay;
    }
    private static int daysBetweenDates_Recursive_Aux(Date begin, Date end){
        if(begin.beforeDate(end)){
            begin.incrementDate();
            return 1 + daysBetweenDates_Recursive_Aux(begin, end);
        }
        return 0;
    }

    private static int daysBetweenDates_Recursive (Date begin, Date end) {
        Date auxBegin = new Date(begin);
        Date auxEnd = new Date(auxBegin.day, auxBegin.month, auxBegin.year + 1);
        int days = 0;

        while (auxEnd.year < end.year) {
            days += Date.daysBetweenDates_Recursive_Aux(auxBegin, auxEnd);
            auxEnd = new Date(auxBegin.day, auxBegin.month, auxBegin.year + 1);
        }

        days += Date.daysBetweenDates_Recursive_Aux(auxBegin, end);
        return days;
    }
}
