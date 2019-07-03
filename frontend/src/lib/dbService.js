import axios from 'axios';

export const saveData = (url, item) => {
  return axios(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: item,
  });
};

export const getData = (url) => {
  return axios(url, {
    method: 'GET',
  });
};
