import axios from "axios";
import { BASE_API } from "./base_api";

const axiosInstance = axios.create({
    baseURL: BASE_API,
    withCredentials: true,
    headers: {
        'ngrok-skip-browser-warning': 'true'
    }
})

axiosInstance.interceptors.response.use(
    (response) => {
        return response
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);


export default axiosInstance