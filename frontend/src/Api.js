import axios from 'axios'

const SERVER_URL = 'http://localhost:8088';

export const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000,
    headers:  {
            'Content-Type': 'application/json',
            // 'Authorization': 'Bearer ' + localStorage.getItem('token')
    }
});

