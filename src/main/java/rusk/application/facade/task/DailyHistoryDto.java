package rusk.application.facade.task;

import rusk.common.util.Dto;

@Dto
public class DailyHistoryDto {
    
    public long id;
    public String title;
    public double workTimeSummary;
    @Override
    public String toString() {
        return "DailyHistoryDto [id=" + id + ", title=" + title + ", workTimeSummary=" + workTimeSummary + "]";
    }
    
}
