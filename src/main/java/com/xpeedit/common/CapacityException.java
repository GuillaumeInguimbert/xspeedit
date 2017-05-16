package com.xpeedit.common;

import com.xpeedit.domain.Capacity;

import java.util.Objects;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public class CapacityException extends RuntimeException {

    private Capacity capacityInfo;

    public CapacityException(String message, Capacity capacity) {
        super(message);
        this.capacityInfo = capacity;
    }

    public Capacity getCapacityInfo() {
        return capacityInfo;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder(super.getMessage());
        if(Objects.nonNull(capacityInfo)){
            builder.append(" The remaining capacity is ").append(capacityInfo.getRemainingCapacity())
                    .append(" (The max size is ").append(capacityInfo.getMaxSize())
                    .append(" and the actual size is ").append(capacityInfo.getSize()).append(").");
        }
        return builder.toString();
    }
}
