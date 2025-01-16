package net.kpuig.mcstat.controller;

import org.springframework.web.bind.annotation.RestController;

import net.kpuig.mcstat.datamodel.McServerStatus;
import net.kpuig.mcstat.service.McStatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class McStatController {
    @Autowired McStatService mcStatService;

    @GetMapping("/statuses")
    public ResponseEntity<List<McServerStatus>> getStatuses() {
        return ResponseEntity.ok(mcStatService.getServerStatuses());
    }
    
}
