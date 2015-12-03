package dataanalyser;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by Bartz, Tobias @Tobi-PC on 03.12.2015 at 14:36.
 */

@DynamoDBTable(tableName="Flightdata")
public class Flight {

    //[Hexcode,Latidude, Longitude, Track,Altitude,Speed,Squawk,RadarType,PlaneType, FlugzeugID, UnixTimestamp]
    private String id;
    private String hexcode;
    private Float latitude;
    private Float longitude;
    private Integer track;
    private Long altitude;
    private Integer speed;
    private String squawk;
    private String radarType;
    private String planeType;
    private String planeRegistration; // eindeutige ID des Flugzeugs?
    private Long timestamp;
    private String start;
    private String destination;
    private String flightNumber;
    private Long unknown1;
    private Long unknown2;
    private String callsign;
    private Long unknown3;

    public Flight() {

    }

    public Flight(String id, String hexcode, Float latitude, Float longitude,
                  Integer track, Long altitude, Integer speed, String squawk,
                  String radarType, String planeType, String planeRegistration,
                  Long timestamp, String start, String destination,
                  String flightNumber, Long unknown1, Long unknown2,
                  String callsign, Long unknown3) {
        this.id = id;
        this.hexcode = hexcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.track = track;
        this.altitude = altitude;
        this.speed = speed;
        this.squawk = squawk;
        this.radarType = radarType;
        this.planeType = planeType;
        this.planeRegistration = planeRegistration;
        this.timestamp = timestamp;
        this.start = start;
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;
        this.callsign = callsign;
        this.unknown3 = unknown3;
    }

    @DynamoDBHashKey(attributeName="Id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="Hexcode")
    public String getHexcode() {
        return hexcode;
    }
    public void setHexcode(String hexcode) {
        this.hexcode = hexcode;
    }

    @DynamoDBAttribute(attributeName="Latitude")
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName="Longitude")
    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @DynamoDBAttribute(attributeName="Track")
    public Integer getTrack() {
        return track;
    }
    public void setTrack(Integer track) {
        this.track = track;
    }

    @DynamoDBAttribute(attributeName="Altitude")
    public Long getAltitude() {
        return altitude;
    }
    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    @DynamoDBAttribute(attributeName="Speed")
    public Integer getSpeed() {
        return speed;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @DynamoDBAttribute(attributeName="Squawk")
    public String getSquawk() {
        return squawk;
    }
    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    @DynamoDBAttribute(attributeName="RadarType")
    public String getRadarType() {
        return radarType;
    }
    public void setRadarType(String radarType) {
        this.radarType = radarType;
    }

    @DynamoDBAttribute(attributeName="PlaneType")
    public String getPlaneType() {
        return planeType;
    }
    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    @DynamoDBAttribute(attributeName="PlaneRegistration")
    public String getPlaneRegistration() {
        return planeRegistration;
    }
    public void setPlaneRegistration(String planeRegistration) {
        this.planeRegistration = planeRegistration;
    }

    @DynamoDBAttribute(attributeName="Timestamp")
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName="Start")
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }

    @DynamoDBAttribute(attributeName="Destination")
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @DynamoDBAttribute(attributeName="FlightNumber")
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @DynamoDBAttribute(attributeName="Unknown1")
    public Long getUnknown1() {
        return unknown1;
    }
    public void setUnknown1(Long unknown1) {
        this.unknown1 = unknown1;
    }

    @DynamoDBAttribute(attributeName="Unknown2")
    public Long getUnknown2() {
        return unknown2;
    }
    public void setUnknown2(Long unknown2) {
        this.unknown2 = unknown2;
    }

    @DynamoDBAttribute(attributeName="Callsign")
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    @DynamoDBAttribute(attributeName="Unknown3")
    public Long getUnknown3() {
        return unknown3;
    }
    public void setUnknown3(Long unknown3) {
        this.unknown3 = unknown3;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", hexcode='" + hexcode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", track=" + track +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", squawk='" + squawk + '\'' +
                ", radarType='" + radarType + '\'' +
                ", planeType='" + planeType + '\'' +
                ", planeRegistration='" + planeRegistration + '\'' +
                ", timestamp=" + timestamp +
                ", start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", unknown1=" + unknown1 +
                ", unknown2=" + unknown2 +
                ", callsign='" + callsign + '\'' +
                ", unknown3=" + unknown3 +
                '}';
    }
}
