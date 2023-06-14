package dev.gostav.weekplan.model;

import java.time.LocalDate;

public class Schedule {
    private LocalDate from;
    private LocalDate to;

    private Agenda[] agendas;

    public Schedule(LocalDate startDate, LocalDate to, Agenda[] dailyAgendas) {
        this.from = startDate;
        this.to = to;
        this.agendas = dailyAgendas;
    }
}
