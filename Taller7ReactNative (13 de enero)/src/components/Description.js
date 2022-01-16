import { TextInput, StyleSheet, Text } from "react-native";
import { useEffect } from "react/cjs/react.development";
import * as SQLite from "expo-sqlite";
import { useState } from "react";
import { Button, Picker } from "react-native-web";

const Description = ({ item, setShowModal, setItem, handleEditBusiness }) => {
  const db = SQLite.openDatabase("reto7");
  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const [telephone, setTelephone] = useState("");
  const [email, setEmail] = useState("");
  const [product, setProduct] = useState("");
  const [type, setType] = useState("Consultoría");
  const styles = StyleSheet.create({
    input: {
      height: 50,
      margin: 12,
      borderWidth: 1,
      padding: 10,
      width: 300,
    },
    text: {
      height: 50,
      padding: 10,
      width: 300,
    },
  });

  useEffect(() => {
    setName(item.nombre);
    setUrl(item.url);
    setTelephone(item.telefono);
    setEmail(item.email);
    setProduct(item.productos);
    setType(item.clasificacion);
    console.log("Item en modal", item);
  }, []);

  const editInfoBusiness = async () => {
    await db.transaction((tx) => {
      tx.executeSql(
        "UPDATE empresas SET nombre = ?, url = ?, telefono = ?, email = ?, productos = ?, clasificacion = ? WHERE id = ?",
        [name, url, telephone, email, product, type, item.id],
        (txObj, resultSet) => {
          if (resultSet.rowsAffected > 0) {
            console.log(resultSet);
          }
        }
      );
    });
    handleEditBusiness();
  };

  const eliminarEmpresa = async () => {
    await db.transaction((tx) => {
      tx.executeSql(
        "DELETE FROM empresas WHERE id = ? ",
        [item.id],
        (txObj, resultSet) => {
          console.log(resultSet);
        }
      );
    });
    handleEditBusiness();
  };

  return (
    <>
      <div>
        <Text style={styles.text}>Nombre de la empresa:</Text>
        <TextInput
          style={styles.input}
          onChangeText={setName}
          value={name}
          placeholder="Nombre de la empresa"
          keyboardType="numeric"
        />
      </div>
      <div>
        <Text style={styles.text}>URL de la empresa:</Text>
        <TextInput
          style={styles.input}
          onChangeText={setUrl}
          value={url}
          placeholder="URL de la empresa"
          keyboardType="numeric"
        />
      </div>
      <div>
        <Text style={styles.text}>Telefono de la empresa:</Text>
        <TextInput
          style={styles.input}
          onChangeText={setTelephone}
          value={telephone}
          placeholder="Teléfono de la empresa"
          keyboardType="numeric"
        />
      </div>
      <div>
        <Text style={styles.text}>Email de la empresa:</Text>
        <TextInput
          style={styles.input}
          onChangeText={setEmail}
          value={email}
          placeholder="Email de la empresa"
          keyboardType="numeric"
        />
      </div>
      <div>
        <Text style={styles.text}>Productos de la empresa:</Text>
        <TextInput
          style={styles.input}
          onChangeText={setProduct}
          value={product}
          placeholder="Productos  de la empresa"
          keyboardType="numeric"
        />
      </div>
      <Picker
        selectedValue={type}
        style={styles.input}
        onValueChange={(itemValue, itemIndex) => setType(itemValue)}
      >
        <Picker.Item label="Consultoría" value="Consultoría" />
        <Picker.Item
          label="Desarrollo a la medida"
          value="Desarrollo a la medida"
        />
        <Picker.Item label="Fabrica de software" value="Fabrica de software" />
      </Picker>
      <Button title="Editar empresa" onPress={editInfoBusiness} />
      <Button title="Eliminar empresa" onPress={eliminarEmpresa} />
      <Button title="Cerrar" onPress={() => setShowModal(false)} />
    </>
  );
};

export default Description;
