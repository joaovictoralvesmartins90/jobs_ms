package com.example.jobapp.services;

import com.example.jobapp.models.Job;

import java.util.List;
import java.util.Optional;

public interface IJobService {
    List<Job> findAll(Long companyId);
    Optional<Job> findJobById(Long jobId, Long companyId);
    Job addJob(Job job, Long companyId);
    boolean deleteJob(Long id, Long companyId);
    boolean updateJob(Job job, Long jobId, Long companyId);
}
