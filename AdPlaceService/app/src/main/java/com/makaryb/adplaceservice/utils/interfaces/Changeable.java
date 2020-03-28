package com.makaryb.adplaceservice.utils.interfaces;

public interface Changeable {
    boolean isChanged();
    void markChanged();
    void markChangesHandled();
}
