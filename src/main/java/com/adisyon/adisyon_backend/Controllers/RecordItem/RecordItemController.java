package com.adisyon.adisyon_backend.Controllers.RecordItem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.RecordItem.CreateRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.DeleteRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.UpdateRecordItemDto;
import com.adisyon.adisyon_backend.Entities.RecordItem;
import com.adisyon.adisyon_backend.Services.RecordItem.RecordItemService;

@RestController
@RequestMapping("/api/record")
public class RecordItemController {

    @Autowired
    private RecordItemService recordItemService;

    @GetMapping("/{id}")
    public ResponseEntity<RecordItem> getRecordItemById(@PathVariable Long id) {
        RecordItem recordItem = recordItemService.findRecordItemById(id);
        return new ResponseEntity<>(recordItem, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<RecordItem>> getRecordItemsByCompanyId(@PathVariable Long id) {
        List<RecordItem> recordItem = recordItemService.findRecordItemsByCompanyId(id);
        return new ResponseEntity<>(recordItem, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordItem> createRecordItem(@RequestBody CreateRecordItemDto recordDto,
            @RequestHeader("Authorization") String jwt) {
        RecordItem recordItem = recordItemService.createRecordItem(recordDto, jwt);
        return new ResponseEntity<>(recordItem, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RecordItem> updateRecordItem(@RequestBody UpdateRecordItemDto recordDto) {
        RecordItem recordItem = recordItemService.updateRecordItem(recordDto);
        return new ResponseEntity<>(recordItem, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<HttpStatus> deleteRecordItem(@RequestBody DeleteRecordItemDto recordDto) {
        recordItemService.deleteRecordItem(recordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/{year}/{month}")
    public ResponseEntity<List<RecordItem>> getRecordItemsByMonth(@PathVariable Long id,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        List<RecordItem> recordItems = recordItemService.findRecordItemsByMonth(id, year, month);
        return new ResponseEntity<>(recordItems, HttpStatus.OK);
    }
}
