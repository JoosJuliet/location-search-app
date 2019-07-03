import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import LoginPage from './pages/login/LoginPage'
import SearchMainPage from './pages/search/SearchMainPage'
import {NotFound} from './pages/notFound/NotFound'

const rounting=(
  <Router>
    <div>
      <Switch>
        <Route exact path="/" component={App}/>
        <Route exact path="/login" component={LoginPage}/>
        <Route exact path="/search" component={SearchMainPage}/>
        
        <Route component={NotFound}/>
      </Switch>
    </div>
  </Router>
)

ReactDOM.render(rounting, document.getElementById('root'));
// /event/:event_id부분 해야한다.