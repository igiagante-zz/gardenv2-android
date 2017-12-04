package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.IrrigationTable;
import com.example.igiagante.thegarden.core.repository.restAPI.services.IrrigationRestApi;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class RestApiIrrigationRepository extends BaseRestApiRepository<Irrigation> implements Repository<Irrigation> {

    private final IrrigationRestApi api;

    public RestApiIrrigationRepository(Context context, Session session) {
        super(context, session);
        api = ServiceFactory.createRetrofitService(IrrigationRestApi.class, session.getToken());
    }

    @Override
    public Observable<Irrigation> getById(String id) {
        return api.getIrrigation(id);
    }

    @Override
    public Observable<Irrigation> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Irrigation> save(Irrigation irrigation, boolean update) {
        return addOrUpdate(irrigation, false);
    }

    @Override
    public Observable<List<Irrigation>> add(Iterable<Irrigation> items) {
        return null;
    }

    public Observable<Irrigation> update(Irrigation irrigation) {
        return addOrUpdate(irrigation, true);
    }

    @NonNull
    private Observable addOrUpdate(Irrigation irrigation, boolean update) {

        MultipartBody.Builder builder = getRequestBody(irrigation);

        Observable<Irrigation> apiResult;

        if (update) {
            apiResult = api.updateIrrigation(irrigation.getId(), builder.build());
        } else {
            apiResult = api.createIrrigation(builder.build());
        }

        return execute(apiResult, IrrigationRealmRepository.class, update);
    }

    @Override
    public Observable<Boolean> remove(String irrigationId) {
        return api.deleteIrrigation(irrigationId)
                .map(response -> response.isSuccessful());
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Irrigation>> getAll() {
        return api.getIrrigations();
    }

    @Override
    public Observable<List<Irrigation>> query(Specification specification) {
        return api.getIrrigations();
    }

    /**
     * Add irrigation to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param irrigation Irrigation
     * @return builder
     */
    private MultipartBody.Builder getRequestBody(@NonNull final Irrigation irrigation) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (irrigation.getDose() != null) {
            String dose = new Gson().toJson(irrigation.getDose());
            builder.addFormDataPart(IrrigationTable.DOSE, dose);
        }

        String date = "";
        if (irrigation.getDose() != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            date = dateFormatter.format(irrigation.getIrrigationDate());
        }

        return builder.addFormDataPart(IrrigationTable.GARDEN_ID, irrigation.getGardenId())
                .addFormDataPart(IrrigationTable.QUANTITY, String.valueOf(irrigation.getQuantity()))
                .addFormDataPart(IrrigationTable.IRRIGATION_DATE, date);
    }
}
