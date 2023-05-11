package com.maracuya.app.atmservice;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class AtmOrderingService {

    private static final Comparator<ServiceTask> SERVICE_TASK_COMPARATOR =
        comparing(ServiceTask::region).thenComparing(task -> task.requestType().priorityOrder);

    public AtmOrder calculateOrder(List<ServiceTask> serviceTasks) {
        return new AtmOrder(serviceTasks.stream()
            .sorted(SERVICE_TASK_COMPARATOR)
            .map(task -> new AtmDetails(task.region(), task.atmId()))
            .distinct()
            .toList());
    }
}
