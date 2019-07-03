import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import LoginForm from '../../components/login/LoginForm'
import axios from 'axios';
import {withRouter} from 'react-router-dom';

class LoginPage extends React.Component{
    state = {
        username: '',
        password: '',
      }
    
      handleChangeFor = (propertyName) => (e) =>{
        this.setState({ [propertyName]: e.target.value })
      }

    handleSubmit = (e) => {
        e.preventDefault();
        let url = "http://localhost:8080/oauth/token";
        let clientId = "locationApp";
        let pass = "password";
        let authorizationBasic = window.btoa(clientId + ':' + pass);
        let headers = {
          "Content-Type": "application/json",
          "Authorization": "Basic " + authorizationBasic
        };
        
        let params = {
            ...this.state,
            grant_type: "password"
        }
        axios(url, {
          method: 'POST',
          headers, 
          params,
        })
        .then(response => {
          localStorage.setItem("access_token", response.data.access_token);
          localStorage.setItem("refresh_token", response.data.refresh_token);
          this.props.history.push("/search");
        })
        .catch(error => {
            alert(error.response.status + " " + error.response.data.error_description);
        })
      }
    render() {
        return (
            <Container>
                <LoginForm 
                    handleSubmit={this.handleSubmit}
                    username={this.state.username}
                    password={this.state.password}
                    handleChangeFor={this.handleChangeFor}/>
            </Container>
        )
    }
}

export default withRouter(LoginPage);
