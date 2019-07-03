import React from 'react'

import {withRouter} from 'react-router-dom';
import { getData } from '../../lib/dbService';


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

class PopularLocationList extends React.Component {
  componentWillMount(){
    this.props.fetchPopularSearchWords();
  }
  render (){
    const mapToItems = (infos) => {
      return infos.map((info, i) => {

        return (<Item
                  key = {i}
                  id = {info.id}
                  message={"검색어 : " + info.searchword +" /  총 검색 수: "+ info.countOfSearch} 
                />)
      })
    }
    
    return (
      <div>
        <h1>인기순 10개</h1>
        <h2>{mapToItems(this.props.popularKeywords)}</h2>
      </div>
    );
  }
}
export default withRouter(PopularLocationList);
