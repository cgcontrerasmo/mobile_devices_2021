import {
  Button,
  Text,
  StyleSheet,
  TextInput,
  Picker,
  Modal,
} from "react-native";
import * as SQLite from "expo-sqlite";
import { useEffect, useState } from "react";
import Description from "./Description";

const Home = () => {
  const db = SQLite.openDatabase("reto7");
  const [listaEmpresas, setListaEmpresas] = useState([]);
  const [filtros, setFiltros] = useState({
    nombre: "",
    clasificacion: "Ninguno",
  });
  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const [telephone, setTelephone] = useState("");
  const [email, setEmail] = useState("");
  const [product, setProduct] = useState("");
  const [type, setType] = useState("Consultoría");
  const [showModal, setShowModal] = useState(false);
  const [empresaActual, setEmpresaActual] = useState({});

  const styles = StyleSheet.create({
    input: {
      height: 50,
      margin: 12,
      borderWidth: 1,
      padding: 10,
      width: 300,
    },
  });

  const handleChangeFiltros = (e, type) => {
    var aux = Object.assign({}, filtros);
    if (type === "nombre") {
      aux.nombre = e;
    } else if (type === "type") {
      aux.clasificacion = e;
    }
    setFiltros(aux);
  };

  useEffect(() => {
    console.log("Lista empresas", listaEmpresas), [listaEmpresas];
  });

  const handleChangeListEmpresas = (array) => {
    console.log(array.length);
    var aux = [];
    for (var i = 0; i < array.length; i++) {
      aux.push(array.item(i));
    }
    setListaEmpresas(aux);
  };

  const addInfoBD = async () => {
    await db.transaction((tx) => {
      tx.executeSql(
        "INSERT INTO empresas (nombre, url, telefono, email, productos, clasificacion) values (?, ?, ?, ?, ?, ?)",
        [name, url, telephone, email, product, type],
        (txObj, resultSet) => console.log(resultSet),
        (txObj, error) => console.log("Error", error)
      );
    });
    setType("");
    setEmail("");
    setProduct("");
    setTelephone("");
    setUrl("");
    setName("");
    db.transaction((tx) => {
      tx.executeSql("SELECT * FROM empresas", [], (trans, result) => {
        handleChangeListEmpresas(result.rows);
      });
    });
  };

  useEffect(() => {
    if (!filtros.nombre && filtros.clasificacion === "Ninguno") {
      db.transaction((tx) => {
        tx.executeSql("SELECT * FROM empresas", [], (trans, result) => {
          handleChangeListEmpresas(result.rows);
        });
      });
    } else if (filtros.nombre && filtros.clasificacion === "Ninguno") {
      db.transaction((tx) => {
        tx.executeSql(
          "SELECT * FROM empresas WHERE nombre LIKE ?",
          [`%${filtros.nombre}%`],
          (trans, result) => {
            handleChangeListEmpresas(result.rows);
          }
        );
      });
    } else if (!filtros.nombre && filtros.clasificacion !== "Ninguno") {
      db.transaction((tx) => {
        tx.executeSql(
          "SELECT * FROM empresas WHERE clasificacion = ?",
          [filtros.clasificacion],
          (trans, result) => {
            handleChangeListEmpresas(result.rows);
          }
        );
      });
    } else {
      db.transaction((tx) => {
        tx.executeSql(
          "SELECT * FROM empresas WHERE clasificacion = ? AND nombre LIKE ?",
          [filtros.clasificacion, `%${filtros.nombre}%`],
          (trans, result) => {
            handleChangeListEmpresas(result.rows);
          }
        );
      });
    }
  }, [filtros]);

  const handleChangeItem = (id) => {
    listaEmpresas.forEach((el) => {
      if (el.id === id) {
        setEmpresaActual(el);
      }
    });
    //setEmpresaActual(listaEmpresas[id]);
    setShowModal(true);
  };

  const handleEditBusiness = () => {
    db.transaction((tx) => {
      tx.executeSql("SELECT * FROM empresas", [], (trans, result) => {
        handleChangeListEmpresas(result.rows);
      });
    });
    setShowModal(false);
  };
  return (
    <>
      <Modal animationType="slide" visible={showModal}>
        <Description
          item={empresaActual}
          setShowModal={setShowModal}
          setItem={setEmpresaActual}
          handleEditBusiness={handleEditBusiness}
        />
      </Modal>
      <Text style={{ fontSize: "50px" }}>Base de datos SQlite</Text>
      <Text>Empresas</Text>
      <div>
        <Text>Filtrar:</Text>
        <TextInput
          style={styles.input}
          onChangeText={(e) => handleChangeFiltros(e, "nombre")}
          value={filtros.nombre}
          placeholder="Filtar por nombre"
          keyboardType="numeric"
        />
        <Text>Filtrar por tipo</Text>
        <Picker
          selectedValue={filtros.clasificacion}
          style={styles.input}
          onValueChange={(itemValue) => handleChangeFiltros(itemValue, "type")}
        >
          <Picker.Item label="Ninguno" value="Ninguno" />
          <Picker.Item label="Consultoría" value="Consultoría" />
          <Picker.Item
            label="Desarrollo a la medida"
            value="Desarrollo a la medida"
          />

          <Picker.Item
            label="Fabrica de software"
            value="Fabrica de software"
          />
        </Picker>
      </div>
      {listaEmpresas.map((el) => {
        return (
          <div
            id={el.id}
            style={{
              border: "black solid 1px",
              height: 50,
              width: 300,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
            onClick={() => handleChangeItem(el.id)}
          >
            <Text>{el.nombre}</Text>
          </div>
        );
      })}
      <Text>Ingrese la información básica de la empresa</Text>
      <TextInput
        style={styles.input}
        onChangeText={setName}
        value={name}
        placeholder="Nombre de la empresa"
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        onChangeText={setUrl}
        value={url}
        placeholder="URL de la empresa"
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        onChangeText={setTelephone}
        value={telephone}
        placeholder="Teléfono de la empresa"
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        onChangeText={setEmail}
        value={email}
        placeholder="Email de la empresa"
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        onChangeText={setProduct}
        value={product}
        placeholder="Productos  de la empresa"
        keyboardType="numeric"
      />
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
      <Button title="Crear empresa" onPress={addInfoBD} />
    </>
  );
};

export default Home;
