package com.example.igiagante.thegarden.home.plants.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.Collection;

/**
 * @author Ignacio Giagante, on 2/5/16.
 */
public interface PlantListView extends IView {

    /**
     * Render a user list in the UI.
     *
     * @param plants The collection of {@link Plant} that will be shown.
     */
    void renderPlantList(Collection<Plant> plants);

    /**
     * Notify that a plant was deleted from the garden
     */
    void notifyPlantWasDeleted();
}
