package com.example.igiagante.thegarden;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.PlantRealmRepository;
import com.example.igiagante.thegarden.plants.repository.specification.PlantByNameSpecification;
import com.example.igiagante.thegarden.plants.repository.specification.PlantSpecification;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by igiagante on 28/4/16.
 */
public class PlantRealmRepositoryTest extends AndroidTestCase{

    private static final String TAG = PlantRealmRepositoryTest.class.getSimpleName();

    private PlantRealmRepository repository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new PlantRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    /**
     * Test add(final Iterable<Plant> plants)
     */
    public void testPersistPlants() {

        ArrayList<Plant> plants = createPlants();
        repository.add(plants);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    //Assert.assertEquals(item.size(), 3);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("PLANTS", item.toString())
        );
    }

    public void testPersistOnePlant() {

        Plant plant = createPlant("1", "mango");

        repository.add(plant);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 1);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("PLANTS", item.toString())
        );
    }

    public void testPersistOnePlantWithImages() {

        Plant plant = createPlantWithImages("1", "mango");

        repository.add(plant);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 1);
                    Assert.assertEquals(item.get(0).getImages().size(), 2);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                    Log.i(TAG, "NUMBER OF IMAGES " + item.get(0).getImages().size());
                }
        );

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("item", item.toString())
        );
    }

    public void testPersistPlantsWithImages() {

        ArrayList<Plant> plants = createPlantsWithImages();
        repository.add(plants);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    //Assert.assertEquals(item.size(), 3);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("PLANTS", item.toString())
        );
    }

    public void testRemoveOnePlant() {

        repository.removeAll();

        Plant plant = createPlantWithImages("1", "mango");

        repository.add(plant);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 1);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );

        repository.remove(plant);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 0);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );
    }

    public void testRemoveOnePlantBySpecification() {

        Plant plant = createPlantWithImages("1", "mango");

        repository.add(plant);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 1);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );

        RealmSpecification realmSpecification = new PlantByNameSpecification("mango");

        repository.remove(realmSpecification);

        repository.query(new PlantSpecification()).subscribe(
                item -> {
                    Assert.assertEquals(item.size(), 0);
                    Log.i(TAG, "NUMBER OF PLANTS " + item.size());
                }
        );
    }


    /**
     * Create a list of plants without images
     * @return plants
     */
    private ArrayList<Plant> createPlants() {

        ArrayList<Plant> plants = new ArrayList<>();

        Plant plantOne = createPlant("1", "mango");
        Plant plantTwo = createPlant("2", "pera");
        Plant plantThree = createPlant("3", "naranja");

        plants.add(plantOne);
        plants.add(plantTwo);
        plants.add(plantThree);

        return plants;
    }

    /**
     * Create a list of plants with images
     * @return plants
     */
    private ArrayList<Plant> createPlantsWithImages() {

        ArrayList<Plant> plants = new ArrayList<>();

        Plant plantOne = createPlantWithImages("1", "mango");

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "mango2", false);

        ArrayList<Image> images = new ArrayList<>();
        images.add(imageOne);
        images.add(imageTwo);

        plantOne.setImages(images);

        Plant plantTwo = createPlantWithImages("2", "pera");

        Image imageThree = createImage("1", "pera", true);
        Image imageFour = createImage("2", "pera2", false);

        ArrayList<Image> imagesPlantTwo = new ArrayList<>();
        images.add(imageThree);
        images.add(imageFour);

        plantOne.setImages(imagesPlantTwo);

        plants.add(plantOne);
        plants.add(plantTwo);

        return plants;
    }

    /**
     * Create one plant
     * @param id Id
     * @param name Plant's name
     * @return plant
     */
    private Plant createPlant(String id, String name) {

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setSize(30);
        plant.setGardenId("1");
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);

        return plant;
    }

    /**
     * Create one plant with images
     * @param id Id
     * @param name Plant's name
     * @return plant
     */
    private Plant createPlantWithImages(String id, String name) {

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setSize(30);
        plant.setGardenId("1");
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        plant.setImages(images);

        return plant;
    }

    /**
     * Create one image (domain)
     * @param id Id
     * @param name Image's name
     * @return image
     */
    private Image createImage(String id, String name, boolean main) {

        Image image = new Image();
        image.setId(id);
        image.setName(name);
        image.setUrl("url");
        image.setThumbnailUrl("thumbUrl");
        image.setType("jpeg");
        image.setSize(4233);
        image.setMain(main);

        return image;
    }
}