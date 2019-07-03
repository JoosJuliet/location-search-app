import React from 'react'
import { Button,Form } from 'react-bootstrap'
import {withRouter} from 'react-router-dom';
import axios from 'axios';

class LoginForm extends React.Component {
  render(){
    return (
      <div>
        <Form onSubmit={this.props.handleSubmit}>
          <Form.Group controlId='formEmployeeName'>
          <Form.Label>이름</Form.Label>
          <Form.Control onChange={this.props.handleChangeFor('username')} value = {this.props.username} type="text" placeholder="이름" />
          </Form.Group>

          <Form.Group controlId="formEmployeePassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control onChange={this.props.handleChangeFor('password')} value = {this.props.password} type="password" placeholder="비밀번호" />
          </Form.Group>

          <Button type="submit" variant="primary">
            로그인
          </Button>
        </Form>
      </div>
    )
  }
}
export default withRouter(LoginForm);
