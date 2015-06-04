package androidgaidamak.blogspot.com.simpleweather;

import java.util.Arrays;

/**
 * Created by Emerpus_drolrevO on 04.06.2015.
 */
public class WeatherProtocol {
    public String message;
    public int cod;
    public int count;
    public CityItem[] list;

    @Override
    public String toString() {
        return "WeatherProtocol{" +
                "message='" + message + '\'' +
                ", cod=" + cod +
                ", count=" + count +
                ", list=" + Arrays.toString(list) +
                '}';
    }

    public class CityItem {
        public int id;
        public String name;
        public Coordinates coord;
        public MainData main;
        public long dt;
        public WindData wind;
        public SysData sys;
        public CloudsData clouds;
        public WeatherData[] weather;

        @Override
        public String toString() {
            return "CityItem{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", coord=" + coord +
                    ", main=" + main +
                    ", dt=" + dt +
                    ", wind=" + wind +
                    ", sys=" + sys +
                    ", clouds=" + clouds +
                    ", weather=" + weather +
                    '}';
        }

        public class Coordinates {
            public double lat;
            public double lon;

            @Override
            public String toString() {
                return "Coordinates{" +
                        "lat=" + lat +
                        ", lon=" + lon +
                        '}';
            }
        }

        public class MainData {
            public float temp;
            public float temp_min;
            public float temp_max;
            public float pressure;
            public float sea_level;
            public float grnd_level;
            public int humidity;

            @Override
            public String toString() {
                return "MainData{" +
                        "temp=" + temp +
                        ", temp_min=" + temp_min +
                        ", temp_max=" + temp_max +
                        ", pressure=" + pressure +
                        ", sea_level=" + sea_level +
                        ", grnd_level=" + grnd_level +
                        ", humidity=" + humidity +
                        '}';
            }
        }

        public class WindData {
            public float speed;
            public float deg;

            @Override
            public String toString() {
                return "WindData{" +
                        "speed=" + speed +
                        ", deg=" + deg +
                        '}';
            }
        }

        public class SysData {
            public String country;

            @Override
            public String toString() {
                return "SysData{" +
                        "country='" + country + '\'' +
                        '}';
            }
        }

        public class CloudsData {
            public int all;

            @Override
            public String toString() {
                return "CloudsData{" +
                        "all=" + all +
                        '}';
            }
        }

        public class WeatherData {
            private long id;
            public String main;
            public String description;
            public String icon;

            @Override
            public String toString() {
                return "WeatherData{" +
                        "id=" + id +
                        ", main='" + main + '\'' +
                        ", description='" + description + '\'' +
                        ", icon='" + icon + '\'' +
                        '}';
            }
        }
    }
}
