package com.example.hms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class UndergoesDTO {

    private int patient;
    private int procedure;
    private int stay;
    private int physician;

    private Integer assistingNurse;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateUndergoes;

    public UndergoesDTO() {}

    public int getPatient() { return patient; }
    public void setPatient(int patient) { this.patient = patient; }

    public int getProcedure() { return procedure; }
    public void setProcedure(int procedure) { this.procedure = procedure; }

    public int getStay() { return stay; }
    public void setStay(int stay) { this.stay = stay; }

    public int getPhysician() { return physician; }
    public void setPhysician(int physician) { this.physician = physician; }

    public Integer getAssistingNurse() { return assistingNurse; }
    public void setAssistingNurse(Integer assistingNurse) { this.assistingNurse = assistingNurse; }

    public LocalDateTime getDateUndergoes() { return dateUndergoes; }
    public void setDateUndergoes(LocalDateTime dateUndergoes) { this.dateUndergoes = dateUndergoes; }
}