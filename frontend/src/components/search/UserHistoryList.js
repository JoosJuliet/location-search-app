import React from 'react'

import {withRouter} from 'react-router-dom';
import axios from 'axios';


class Item extends React.Component {
  render (){
    return (
        <div className="row">
          <div className="col-md-12">
            <h1>{this.props.message}</h1>
          </div>
        </div>
    );
  }
}

class UserHistoryList extends React.Component {
    componentWillMount(){
        this.props.fetchHistories();
      }
    render (){
        const mapToItems = (infos) => {
            return infos.map((info, i) => {

                return (<Item
                        key = {i}
                        id = {info.id}
                        message={"검색어 : " + info.searchword +" /  시간: "+ info.searchAt} 
                        />)
            })
        }
        
        return (
            <div>
                <h1>검색한 기록</h1>
                <h2>{mapToItems(this.props.searchHistories)}</h2>
            </div>
        );
    }
}
export default withRouter(UserHistoryList);
