/*eslint-disoble*/
import React, { useState } from 'react';
import './App.css';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import axios from 'axios';


function App() {
  const [id,setId] = useState("");
  const [pw,setPw] = useState("");

  const register= () => {
    //axios로 서버에 보낸다
    axios
        .post('http://Udangtangtangapp-env.eba-xaipu9ej.ap-northeast-2.elasticbeanstalk.com/join',{
          username : id,
          password : pw,
        })
        .then((response) =>{
          console.log("done!");
          console.log("user profile",response.data.user);
          console.log("Use token",response.data.jwt);
        })
        .catch((error) => {
          console.log('An error occurred:',error.response);
        });
  }

  return (
      <div>
        <TextField
            label="아이디"
            value = {id}
            onChange = {(event)=>{setId(event.target.value);
              console.log(event.target.value);
            }}
        />
        <TextField
            label="비밀번호"
            value = {pw}
            onChange = {(event)=>{setPw(event.target.value);
              console.log(event.target.value);
            }}
        />
        <Button type = "submit" variant="contained" onClick={register}>Sign in</Button>
      </div>
  );
}

export default App;