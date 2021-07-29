package com.ayponyo.android.movieapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaDate extends Date {
    private SimpleDateFormat sdf;

    /**
     * @param pFormat (String) Format to be given to the date
     * <ul>
     * <li>G -> Era</li>
     * <li>y -> Year</li>
     * <li>M -> Month</li>
     * <li>w -> Week in the year</li>
     * <li>W -> Week in the month</li>
     * <li>D -> Day in the year</li>
     * <li>d -> Day in the month</li>
     * <li>F -> Day of the week in the month</li>
     * <li>E -> Day of the week in letters</li>
     * <li>a -> Marker AM/PM</li>
     * <li>H -> Hour (0-23)</li>
     * <li>k -> Hour (1-24)</li>
     * <li>K -> Hour in AM/PM (0-11)</li>
     * <li>h -> Hour in AM/PM (1-12)</li>
     * <li>m -> Minutes</li>
     * <li>s -> Seconds</li>
     * <li>S -> Milliseconds</li>
     * </ul>
     * Exemples : "dd/MM/yyyy", "dd MMMMM yyyy GGG, hh:mm aaa","hh:mm a, zzzz"
     *
     * @param pDateString (String)
     * Date written as a string of characters in the same way as the specified format
     * Example : pformat = "dd/MM/yyyy", pDateString = "21/01/2023"
     */
    public MaDate(String pFormat, String pDateString) throws ParseException {
        super((new SimpleDateFormat(pFormat)).parse(pDateString).getTime());
        sdf = new SimpleDateFormat(pFormat);
    }

    /**
     * Current Date
     */
    public MaDate(){
        super();

        sdf = new SimpleDateFormat("dd/MM/yyyy");

    }

    /**
     * @param pDateString (String) Date written as a string of characters in the format "dd/MM/yyyy"
     * Example : "21/01/2023"
     * @throws ParseException if pDateString isn't in the format "dd/MM/yyyy"
     */
    public MaDate(String pDateString) throws ParseException{
        super((new SimpleDateFormat("dd/MM/yyyy")).parse(pDateString).getTime());


        sdf = new SimpleDateFormat("dd/MM/yyyy");

    }
    /**
     * @return (String) corresponding to the data received in the constructor
     */
    @Override
    public String toString(){
        return sdf.format(this);
    }

    public String toStringPattern(String pattern){
        sdf.applyPattern(pattern);
        return sdf.format(this);
    }
    /**
     * @return (String) corresponding to the format received in the constructor - "dd/MM/yyyy" by default.
     */
    public String getFormat(){ return sdf.toPattern();}

    @Override
    public Object clone() {
        MaDate s = null;
        s = (MaDate) super.clone();
        return s;
    }
}