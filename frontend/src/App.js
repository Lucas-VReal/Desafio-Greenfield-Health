import './App.css';
import Medicos from './Medicos/Medicos';
import ListarMedicos from './Medicos/ListarMedicos';
import React, { useEffect, useState } from "react";

function App() {

  //UseState
  const[btnCadastrar, setBtnCadastrar] = useState(false);
  const[medicos, setMedicos] = useState([]);

  //UseEfect
  useEffect(()=>{
    fetch("http://localhost:8080/Doctors/allDoctors")
    .then(retorno => retorno.json())
    .then(retorno_convertido => setMedicos(retorno_convertido))
  }, [])


  return (
    <div>
      <Medicos botao={btnCadastrar} />
      <ListarMedicos />
    </div>
  );
}

export default App;
