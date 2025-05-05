public Date nextTimeAfter(Date current){
    if (current.after(end) || current.equals(end))return null;
    if (isRepeated() && isActive()){
        Date timeBefore  = start;
        Date timeAfter = start;
        if (current.before(start)){
            return start;
        }
        if ((current.after(start) || current.equals(start)) && (current.before(end) || current.equals(end))){
            for (long i = start.getTime(); i <= end.getTime(); i += (long) interval*1000){
                if (current.equals(timeAfter)) return new Date(timeAfter.getTime()+interval*1000);
                if (current.after(timeBefore) && current.before(timeAfter)) return timeBefore;//return timeAfter
                timeBefore = timeAfter;
                timeAfter = new Date(timeAfter.getTime()+ interval*1000);
            }
        }
    }
    if (!isRepeated() && current.before(time) && isActive()){
        return time;
    }
    return null;
}