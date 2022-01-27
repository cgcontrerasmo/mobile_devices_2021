import { StatusBar } from "expo-status-bar";
import { useEffect, useState } from "react";
import { StyleSheet, Text, View, TextInput } from "react-native";
import MapView, { Marker, PROVIDER_GOOGLE } from "react-native-maps";
import axios from "axios";

export default function App() {
  const [location, setLocation] = useState({
    latitude: 4.671329,
    longitude: -74.145499,
    latitudeDelta: 0.001663 * 5,
    longitudeDelta: 0.002001 * 5,
  });

  const updateLocation = (coordenade) => {
    var aux = {
      ...coordenade,
      ...{ latitudeDelta: 0.001663 * 5, longitudeDelta: 0.002001 * 5 },
    };
    setLocation(aux);
  };
  const [radius, setRadius] = useState(0);

  const handleChangeRadius = (e) => {
    if (e.length <= 0) {
      setRadius(0);
    } else {
      setRadius(parseInt(e));
    }
  };
  const [pois, setPois] = useState([]);

  const getMarkers = async (longitude, latitude, radius) => {
    try {
      const response = await axios.get(
        `https://api.mapbox.com/v4/mapbox.mapbox-streets-v8/tilequery/${longitude},${latitude}.json`,
        {
          params: {
            radius: radius.toString(),
            layers: "poi_label",
            geometry: "point",
            limit: 50,
            access_token:
              "pk.eyJ1IjoiYWNyZWVkMjEiLCJhIjoiY2t5a29kcmc4MDNpZTJubzB2dnRxYjEyMyJ9.c_Wwpscsq2yE633aMuRXgA",
            dedupe: true,
          },
        }
      );
      setPois(response.data.features);
    } catch (error) {
      setPois([]);
      console.error(error);
    }
  };

  useEffect(() => {
    if (radius) getMarkers(location.longitude, location.latitude, radius);
  }, [location, radius]);

  return (
    <View style={styles.container}>
      <MapView
        style={StyleSheet.absoluteFillObject}
        provider={PROVIDER_GOOGLE}
        mapType="standard"
        region={location}
        onPress={(e) => {
          updateLocation(e.nativeEvent.coordinate);
        }}
      >
        <MapView.Circle
          key={(location.latitude + location.longitude).toString()}
          center={{
            latitude: location.latitude,
            longitude: location.longitude,
          }}
          radius={parseInt(radius)}
          strokeWidth={1}
          strokeColor={"#1a66ff"}
          fillColor={"rgba(230,238,255,0.5)"}
          //onRegionChangeComplete = { this.onRegionChangeComplete.bind(this) }
        />
        <Marker
          key={"actual"}
          coordinate={{
            latitude: location.latitude,
            longitude: location.longitude,
          }}
          title={"Localización actual"}
          description={"Localización actual"}
        />
        {pois.map((poi, index) =>
          poi.properties.type !== "Residential" && poi.properties.name ? (
            <Marker
              key={index}
              coordinate={{
                latitude: poi.geometry.coordinates[1],
                longitude: poi.geometry.coordinates[0],
              }}
              title={poi.properties.name}
              description={poi.properties.type}
              image={require("./alfiler.png")}
            ></Marker>
          ) : null
        )}
      </MapView>
      <Text
        style={{
          position: "absolute",
          top: 50,
          backgroundColor: "white",
          right: 50,
          fontWeight: "bold",
        }}
      >
        Ingrese el radio de su interés
      </Text>
      <TextInput
        keyboardType="numeric"
        style={{
          height: 40,
          width: 100,
          borderColor: "gray",
          borderWidth: 1,
          position: "absolute",
          top: 80,
          backgroundColor: "white",
          borderRadius: 10,
          right: 50,
          padding: 10,
        }}
        onChangeText={(text) => handleChangeRadius(text)}
        value={radius}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
