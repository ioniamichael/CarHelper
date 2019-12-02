package com.enjoyapp.carhelper.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class Garage {

    @SerializedName("garage")
    public ArrayList<GarageObject> garage;

    static public class GarageObject {
        @SerializedName("mispar_mosah")
        public int garageNumber;

        @SerializedName("shem_mosah")
        public String garageName;

        @SerializedName("cod_sug_mosah")
        public int garageTypeCode;

        @SerializedName("sug_mosah")
        public String garageType;

        @SerializedName("ktovet")
        public String garageAddress;

        @SerializedName("yishuv")
        public String garageCity;

        @SerializedName("telephone")
        public String garagePhone;

        @SerializedName("mikud")
        public int zipCode;

        @SerializedName("cod_miktzoa")
        public int professionCode;

        @SerializedName("miktzoa")
        public String profession;

        @SerializedName("menahel_miktzoa")
        public String professionManager;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GarageObject that = (GarageObject) o;
            return garageNumber == that.garageNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(garageNumber);
        }
    }

    public Garage(ArrayList<GarageObject> garage) {
        this.garage = garage;
    }

    public ArrayList<GarageObject> getGarage() {
        return garage;
    }



}