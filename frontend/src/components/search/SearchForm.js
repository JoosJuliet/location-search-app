import React from 'react'
import { Button,Form } from 'react-bootstrap'


class SearchForm extends React.Component {
  render(){
    return (
      <div>
        <Form onSubmit={this.props.handleSubmit}>
          <Form.Group controlId='formEmployeeName'>
          <Form.Label>검색어</Form.Label>
          <Form.Control 
            onChange={this.props.handleChangeFor('searchWord')} 
            value = {this.props.searchWord} 
            type="text" 
            placeholder="검색어" />
          </Form.Group>
          <Button type="submit" variant="primary">
            검색
          </Button>
        </Form>
      </div>
    )
  }
}
export default SearchForm
