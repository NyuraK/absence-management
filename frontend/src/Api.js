import axios from 'axios'


export const instance = axios.create({
    baseURL: '/api/v1',
    timeout: 10000,
    headers:  {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
    }
});

