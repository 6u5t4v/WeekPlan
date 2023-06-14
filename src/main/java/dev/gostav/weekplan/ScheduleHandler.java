package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Agenda;
import dev.gostav.weekplan.model.Schedule;
import dev.gostav.weekplan.model.layouts.DefaultDay;

import java.time.LocalDate;

public class ScheduleHandler {
    //            startDate = LocalDate.now().with(previousOrSame(DayOfWeek.MONDAY));

    private DefaultDay workDay, offDay;

    public ScheduleHandler(DefaultDay workDay, DefaultDay offDay) {
        this.workDay = workDay;
        this.offDay = offDay;

        createSchedule(LocalDate.now(), 7);
    }

    public Schedule createSchedule(LocalDate startDate, int days) {
        Agenda[] dailyAgendas = new Agenda[days];

        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            if (isWorkday(date)) {
                dailyAgendas[i] = new Agenda(workDay, null);
            } else {
                dailyAgendas[i] = new Agenda(offDay, null);
            }

            Agenda agenda = dailyAgendas[i];

            // figure out how much time is available between routines
            float freeTime = agenda.getDefaultDay().freeTimeInSeconds();
            System.out.println(freeTime + " seconds free time");

            // all the free time has to be divided into filler tasks

            // the filler tasks have to have their minimum time requirement met

            // if the filler task minimum time exceeds a threshold it has to be
            // split across multiple days
        }

        return new Schedule(startDate, startDate.plusDays(days), dailyAgendas);
    }

    private boolean isWorkday(LocalDate date) {
        return date.getDayOfWeek().getValue() < 6;
    }
}
