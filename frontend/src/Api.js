import axios from 'axios'

// const SERVER_URL = 'http://localhost:8080';

export const instance = axios.create({
    // baseURL: '/api',
    timeout: 1000,
    headers:  {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
    }
});

