import axios from 'axios'


export const instance = axios.create({
    baseURL: '/api',
    timeout: 1000,
    headers:  {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
    }
});

