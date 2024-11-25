package com.adisyon.adisyon_backend.Services.RecordItem;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.RecordItem.CreateRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.DeleteRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.UpdateRecordItemDto;
import com.adisyon.adisyon_backend.Entities.RecordItem;

public interface RecordItemService {
    public RecordItem findRecordItemById(Long id);

    public List<RecordItem> findRecordItemsByCompanyId(Long id);

    public RecordItem createRecordItem(CreateRecordItemDto recordDto, String jwt);

    public RecordItem updateRecordItem(UpdateRecordItemDto recordDto);

    public void deleteRecordItem(DeleteRecordItemDto recordDto);

    public List<RecordItem> findRecordItemsByDay(Long id, Integer year, Integer month, Integer day);

    public List<RecordItem> findRecordItemsByMonth(Long id, Integer year, Integer month);
}
