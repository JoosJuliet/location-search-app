import React from 'react'
import PopularLocationList from '../../components/search/PopularLocationList'
import UserHistoryList from '../../components/search/UserHistoryList'
import SearchPagination from '../../components/search/SearchPagination'

import { Container, Button, Pagination } from 'react-bootstrap'
import SearchForm from '../../components/search/SearchForm';

import axios from 'axios';
import {withRouter} from 'react-router-dom';

class SearchMainPage extends React.Component {
  state = {
    searchWord: '',
    items: [],
    popularKeywords: [],
    searchHistories: []
  }
  handleChangeFor = (propertyName) => (e) =>{
    this.setState({ [propertyName]: e.target.value })
  }
  handleSubmit = (e) => {
    e.preventDefault();

    let url = 'http://localhost:8080/search?searchword='+this.state.searchWord+'&page=1&size=10';
      let headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("access_token")
      };

      axios(url, {
        method: 'POST',
        headers
      })
      .then(response => {
        this.setState({items: response.data});
        this.fetchHistories();
        this.fetchPopularSearchWords();
      })
      .catch(error => {
          alert(error.response.data.error_description);
          this.props.history.push("/login");
      })
  }
  fetchHistories =() => {
    let url = 'http://localhost:8080/search/history';
    let headers = {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + localStorage.getItem("access_token")
    };

    axios(url, {
      method: 'GET',
      headers
    })
    .then(response => {
      console.log(response);
      this.setState({searchHistories: response.data });
    })
    .catch(error => {
      alert(error.response.data.error_description);
      this.props.history.push("/login");
    })
  }
  fetchPopularSearchWords =(e) => {
    axios('http://localhost:8080/popular-searchword', {
      method: 'GET'
    })
    .then(response => {
      this.setState({popularKeywords: response.data });
    })
    .catch(error => {
      console.log(error.response);
    })
  }

  render(){
    return (
      <div>
        <Container>
          <SearchForm 
            handleSubmit={this.handleSubmit} 
            handleChangeFor={this.handleChangeFor} 
            searchWord={this.state.searchWord}
            />
          <SearchPagination 
            items={this.state.items} 
            onChangePage={this.onChangePage}
            searchWord={this.state.searchWord}/>
          <PopularLocationList 
            popularKeywords={this.state.popularKeywords} 
            fetchPopularSearchWords={this.fetchPopularSearchWords}
            />
          <UserHistoryList 
            searchHistories={this.state.searchHistories}
            fetchHistories={this.fetchHistories}/>
        </Container>
      </div>
    )
  }
};

export default withRouter(SearchMainPage);
