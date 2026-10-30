package com.example.jobapp.controllers;

import com.example.jobapp.models.Job;
import com.example.jobapp.services.IJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/jobs")
public class JobController {

    private final IJobService jobService;

    public JobController(IJobService jobService){
        this.jobService = jobService;
    }

    @GetMapping()
    public ResponseEntity<List<Job>> findAll(@PathVariable Long companyId){
        return new ResponseEntity<>(this.jobService.findAll(companyId), HttpStatus.OK) ;
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Job> findJobById(@PathVariable Long companyId, @PathVariable Long jobId){
        return this.jobService.findJobById(companyId, jobId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Job> addJob(@RequestBody Job job, @PathVariable Long companyId){
        Job jobCreated = this.jobService.addJob(job, companyId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(job.getId())
                .toUri();
        return ResponseEntity.created(location).body(jobCreated);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long companyId, @PathVariable Long jobId){
        if(this.jobService.deleteJob(companyId, jobId)){
            return ResponseEntity.ok("Job deleted succesfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<String> updateJob(@RequestBody Job job, @PathVariable Long companyId, @PathVariable Long jobId){
        if(this.jobService.updateJob(job, companyId, jobId)){
            return ResponseEntity.ok("Job updated succesfully");
        }
        return ResponseEntity.notFound().build();
    }

}
