package br.com.todeolho.todeolho.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by luciano on 14/04/16.
 *
 * This is a POJO representation of the marker JSON
 * {
 *   id: "115-0",
 *   nome_da_obra: "CONTROLE DE CHEIAS DA BACIA DO RIO RESSACA E PARQUE LINEAR - FASE 1",
 *   inicio: "14/06/2010",
 *   data_estimada_termino: "14/06/2011",
 *   situacao: "Concluída",
 *   lat: -25.514722222222222,
 *   lon: -49.20055555555556
 *   }
 *
 */
public class MapMarker implements Serializable {

    String id;
    String title;
    String startDate;
    String endDate;
    String status;
    double lat;
    double lon;


    public MapMarker(JSONObject source) {
        this.id = source.optString("id");
        this.title = source.optString("nome_da_obra");
        this.startDate = source.optString("inicio");
        this.endDate = source.optString("data_estimada_termino");
        this.status = source.optString("situacao");

        this.lat = source.optDouble("lat", 0);
        this.lon = source.optDouble("lon", 0);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
